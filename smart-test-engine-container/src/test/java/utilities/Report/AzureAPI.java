package utilities.Report;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilities.Utils.MainUtils;

public class AzureAPI {

    static String personalAccessToken = "2slpnfyg5tyzpddkswcpl5edtruw3bnu6wcvq5rn7e3nzde27cya";
    static String authHeaderValue = "Basic "
            + Base64.getEncoder().encodeToString((":" + personalAccessToken).getBytes());
    static String organizacion = "quironsalud";
    static String proyecto = "SmartTestEngine";
    static String testPlan = "238561";
    static String testSuiteFolder = "256028"; // Directorio donde cuelgan los TS que vamos creando en las ejecuciones
    static String testSuiteStatic = "257598"; // TS que se va modificando con los TC que cumplen la qury y tomamos como
                                              // plantilla
    static String stateRun = "Completed";

    /**
     * Funcion para Crear un Test Suite en base a una query
     * 
     */
    public static String CreaTestSuite() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        String fechaHoraFormateada = ahora.format(formatter);
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/test/Plans/" + testPlan
                + "/suites/" + testSuiteFolder + "?api-version=5.0";
        String requestBody = "{"
                + "\"suiteType\": \"StaticTestSuite\","
                + "\"name\": \"" + fechaHoraFormateada + "\","
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            Pattern pattern = Pattern.compile("\"id\":(\\d+)");
            Matcher matcher = pattern.matcher(responseBody);
            if (matcher.find()) {
                String id = matcher.group(1);
                return id;
            } else {
                MainUtils.printReportMessage(
                        "[FRAMEWORK] El valor de 'id' no se encontró en la respuesta :" + responseBody);
                return null;
            }
        } catch (IOException | InterruptedException e) {
            MainUtils.printReportMessage("[FRAMEWORK] Error en la creación del Test Suite en Azure Devops");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion para añadir TC a un TS
     * 
     */
    @SuppressWarnings("unused")
    public static void AddTC(String suiteId, String caseId) {
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/test/Plans/" + testPlan
                + "/suites/" + suiteId + "/testcases/" + caseId + "?api-version=7.1-preview.3";
        String requestBody = "{}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        int retries = 1;
        boolean repeat = true;
        do {
            if (retries < 3) {
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String responseBody = response.body();
                    repeat = false;
                    // System.out.println("responseBody:" + responseBody);
                } catch (javax.net.ssl.SSLHandshakeException e) {
                    retries++;
                    MainUtils.printReportMessage(
                            "[FRAMEWORK] Error al añadir el TC :" + caseId + " se reintenta de nuevo");
                } catch (IOException | InterruptedException e) {
                    MainUtils.printReportMessage("[FRAMEWORK] Error al añadir el TC :" + caseId);
                    e.printStackTrace();
                }
            } else {
                MainUtils.printReportMessage("[FRAMEWORK] Error al añadir el TC :" + caseId);
                repeat = false;
            }
        } while (repeat);
    }

    /**
     * Funcion para Obtener los Test Case de un Test Suite (estatico) y añadirlos al
     * test suite recien creado
     * 
     * @throws ParseException
     * 
     */
    public static void getAndAddTestCases(String suiteId) throws ParseException {
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/test/Plans/" + testPlan
                + "/suites/" + testSuiteStatic + "/testcases?api-version=7.1-preview.3";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Accept", "application/json")
                .GET() // Cambio a GET
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(responseBody);
            JSONArray valueArray = (JSONArray) obj.get("value");
            for (Object valueElement : valueArray) {
                JSONObject testCase = (JSONObject) ((JSONObject) valueElement).get("testCase");
                String caseId = (String) testCase.get("id");
                AddTC(suiteId, caseId);
            }

        } catch (IOException | InterruptedException e) {
            MainUtils.printReportMessage("[FRAMEWORK] Error al recuperar los Test Points de Azure Devops");
            e.printStackTrace();
        }

    }

    /**
     * Funcion para Obtener los Test Points de un Test Suite
     * 
     */

    public static String getTestSuitePoints(String id) {
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/test/Plans/" + testPlan
                + "/suites/" + id + "/points?api-version=6.0";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Accept", "application/json")
                .GET() // Cambio a GET
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return responseBody;
        } catch (IOException | InterruptedException e) {
            MainUtils.printReportMessage("[FRAMEWORK] Error al recuperar los Test Points de Azure Devops");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion para Crear un Run de ejecución vacio asociado a un test suite
     * 
     */

    public static String CreaExecution() {
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://vstmr.dev.azure.com/" + organizacion + "/" + proyecto
                + "/_apis/testresults/runs?api-version=7.1-preview.1";
        String requestBody = "{ \"Name\":\"Execution Test Automation Framework\", \"plan\": { \"id\": \"" + testPlan
                + "\" }, \"pointIds\": [], \"state\":\"InProgress\" }";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            Pattern pattern = Pattern.compile("\"id\":(\\d+)");
            Matcher matcher = pattern.matcher(responseBody);
            if (matcher.find()) {
                String id = matcher.group(1);
                MainUtils.printReportMessage("[FRAMEWORK] Se ha generado un Run con id :" + id);
                return id;
            } else {
                MainUtils.printReportMessage(
                        "[FRAMEWORK] El valor de 'id' no se encontró en la respuesta :" + responseBody);
                return null;
            }
        } catch (IOException | InterruptedException e) {
            MainUtils.printReportMessage("[FRAMEWORK] Error al crear la ejecución en Azure Devops");
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static void AddResult(String idRun, Long idPoint, String state) {
        String stateTest;
        if (state.equals("PASSED")) {
            stateTest = "Passed";
        } else if (state.equals("PASSED WITH FAILURES") || state.equals("FAILED")) {
            stateTest = "Failed";
        } else {
            stateTest = "NotExecuted";
        }
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/test/runs/" + idRun
                + "/results?api-version=7.1-preview.2";
        String requestBody = "[{\"id\": 0,\"testPoint\": {\"id\": \"" + idPoint + "\"},\"outcome\": \"" + stateTest
                + "\",\"state\":\"Completed\"}]";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        int retries = 1;
        boolean repeat = true;
        do {
            if (retries < 3) {

                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String responseBody = response.body();
                    repeat = false;
                } catch (javax.net.ssl.SSLHandshakeException e) {
                    retries++;
                    MainUtils.printReportMessage("[FRAMEWORK] Error al añadir resultados (idPoint: " + idPoint
                            + ")  al run " + idRun + ", se reintenta de nuevo");
                } catch (IOException | InterruptedException e) {
                    MainUtils.printReportMessage(
                            "[FRAMEWORK] Error al añadir resultados (idPoint: " + idPoint + ")  al run " + idRun);
                    e.printStackTrace();
                    repeat = false;
                }
            } else {
                MainUtils.printReportMessage(
                        "[FRAMEWORK] Error al añadir resultados (idPoint: " + idPoint + ")  al run " + idRun);
                repeat = false;
            }
        } while (repeat);

    }

    @SuppressWarnings("unused")
    public static void CompleteExecution(String idRun) {
        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "https://vstmr.dev.azure.com/" + organizacion + "/" + proyecto + "/_apis/testresults/runs/"
                + idRun + "?api-version=7.1-preview.1";
        String requestBody = "{\"Name\":\"Execution Test Automation Framework\",\"state\":\"" + stateRun + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
        } catch (IOException | InterruptedException e) {
            MainUtils.printReportMessage("[FRAMEWORK] Error al completar la ejecución en Azure Devops");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void AzureExecutions(ResumeReport resumeReport) throws ParseException {
        String currentDir = System.getProperty("user.dir");
        MainUtils.printReportMessage("[FRAMEWORK] vemos el directorio :" + currentDir);
        if (currentDir.equals("/Users/inetum/Repositorios/Regresion/STEMobility")) {

            // En este trozo, vamos a extraer del reporte de ejecución los nombres de los
            // test y el resultado de la ejecución
            String jsonStr = resumeReport.toString();
            JSONParser parser = new JSONParser();
            JSONArray resultsArray = new JSONArray();
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
                JSONArray testSuites = (JSONArray) jsonObject.get("TestSuites");
                for (Object suite : testSuites) {
                    JSONObject testSuite = (JSONObject) suite;
                    JSONArray tests = (JSONArray) testSuite.get("Tests");
                    for (Object tst : tests) {
                        JSONObject test = (JSONObject) tst;
                        String testName = (String) test.get("TestName");
                        String status = (String) test.get("Status");
                        JSONObject resultObject = new JSONObject();
                        resultObject.put("TestName", testName);
                        resultObject.put("Status", status);
                        // TODO add duration
                        resultsArray.add(resultObject);
                    }
                }
            } catch (ParseException e) {
                MainUtils.printReportMessage("[FRAMEWORK] Error al extraer los test de ResumeReport");
                e.printStackTrace();
            }

            String automaticTestSuite = CreaTestSuite();
            getAndAddTestCases(automaticTestSuite);
            String jsonResponse = getTestSuitePoints(automaticTestSuite);
            String idExecution = CreaExecution();

            // En este trozo, vamos a recorrer respuesta de getPoints el id , y extraer del
            // nombre el código del test para cruzarlo con los test que hemos extraido del
            // reporte
            JSONParser parserPoints = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) parserPoints.parse(jsonResponse);
                JSONArray valuesArray = (JSONArray) jsonObject.get("value");
                for (Object valueObj : valuesArray) {
                    JSONObject value = (JSONObject) valueObj;
                    long idPoint = (long) value.get("id");
                    JSONObject testCase = (JSONObject) value.get("testCase");
                    String testCaseName = (String) testCase.get("name");
                    String[] parts = testCaseName.split(" - ");
                    String testCaseCode = parts[0].split("MOB_")[1];
                    for (int i = 0; i < resultsArray.size(); i++) {
                        JSONObject result = (JSONObject) resultsArray.get(i);
                        String testName = (String) result.get("TestName");
                        String status = (String) result.get("Status");
                        // System.out.println("vemos la posicion de los dos arrays, i: " + i + "de " +
                        // resultsArray.size() );
                        if (testCaseCode.equals(testName)) {
                            MainUtils.printReportMessage(
                                    "[FRAMEWORK] Se ha encontrado ejecución para el TC: " + testCaseName);
                            AddResult(idExecution, idPoint, status);
                            break;
                        }
                        if (i == resultsArray.size() - 1) {
                            MainUtils.printReportMessage(
                                    "[FRAMEWORK] No se ha encontrado ejecución para el TC: " + testCaseName);
                            AddResult(idExecution, idPoint, "NotExecuted");
                        }
                    }
                }
            } catch (ParseException e) {
                MainUtils.printReportMessage(
                        "[FRAMEWORK] Error al recorrer los test del test suite creado dinamicamente");
                e.printStackTrace();
            }
            CompleteExecution(idExecution);

        } else {
            MainUtils.printReportMessage(
                    "[FRAMEWORK] No se genera Reporte de Ejecucion en Azure, no está en Regresión o no está activado");
        }
    }

}
