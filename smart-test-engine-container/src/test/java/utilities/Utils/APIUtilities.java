package utilities.Utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.testng.SkipException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Report.APIEvidence;
import utilities.Report.APIEvidence.RequestDetails;
import utilities.Report.APIEvidence.ResponseDetails;
import utilities.Report.StringToJsonFile;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtilities {
    public static String testId;
    private static Response response;

    public APIUtilities() {
        MainUtils.setInstance();
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion get y devuelve el resultado
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     * @throws Exception
     *
     */
    public static void getRequest(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }
                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }
                String url = jsonPath.getString(path + "url");
                response = request.get(url);

                String StringRequest = request.toString();
                String StringResponse = response.toString();

                TestAPIEnd(StringRequest, StringResponse);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento de peticion número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorGetRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorGetRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion get y devuelve el body de la respuesta
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     * @throws Exception
     *
     */
    public static String getRequestBody(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                response = request.get(url);

                String StringRequest = request.toString();
                String StringResponse = response.asString();

                System.out.println("vemos StringRequest:" + StringRequest);
                System.out.println("vemos StringResponse:" + StringResponse);
                System.out.println("vemos StringResponse asString:" + response.asString());

                TestAPIEnd(StringRequest, StringResponse);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                    return response.asPrettyString();
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento de peticion número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorGetRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");

                }
            } while (repeat);
            return null;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorGetRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion post
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static void postRequest(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.post(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion post y devuelve el body de respuesta
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static String postRequestBody(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.post(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                    return response.asPrettyString();
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
            return null;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion PUT
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static void putRequest(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.put(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion PUT y devuelve el body de respuesta
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static String putRequestBody(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.put(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                    return response.asPrettyString();
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
            return null;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion PATCH
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static void patchRequest(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.patch(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion PATCH y devuelve el body de respuesta
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static String patchRequestBody(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.patch(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                    return response.asPrettyString();
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
            return null;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion DELETE
     * 
     * @param indexJson indice del contenedor de datos para la llamda
     * @param status    estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static void deleteRequest(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            // Response response = null;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "headers") != null) {
                    Map<String, String> headers = jsonPath.getMap(path + "headers");
                    request.headers(headers);
                }

                if (jsonPath.getMap(path + "formParams") != null) {
                    Map<String, String> formParams = jsonPath.getMap(path + "formParams");
                    request.formParams(formParams);
                }

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                if (jsonPath.get(path + "body") != null) {
                    request.body(jsonPath.getString(path + "body"));
                }

                if (jsonPath.get(path + "rawBody") != null) {
                    String rawBody = jsonPath.getString(path + "rawBody");
                    request.body(rawBody);
                }
                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.delete(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servico web");
                    // MainUtils.contadorPostRequest++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            // MainUtils.contadorPostRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Ejecuta una peticion post y devuelve el las cookies de la respuesta
     * 
     * @param url    URL de la API que se quieren recoger las cookies
     * @param status estado de la respuesta
     * @return Response: objeto tipo response que contiene codigo de status, headers
     *         y cuerpo de respuesta
     *
     */
    public static String[] postRequestAndGetCookies(String indexJson, String status) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            String environment = "PRE";
            String path = environment + "." + indexJson + ".";
            String[] cookiesArray = null;
            ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
            PrintStream requestPrintStream = new PrintStream(requestOutputStream);
            ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            PrintStream responsePrintStream = new PrintStream(responseOutputStream);

            do {
                JsonPath jsonPath = readJsonFileToJsonPath();
                RestAssured.baseURI = jsonPath.get(path + "url");
                RequestSpecification request = RestAssured.given();

                request.filter(new RequestLoggingFilter(requestPrintStream))
                        .filter(new ResponseLoggingFilter(responsePrintStream));

                if (jsonPath.getMap(path + "cookies") != null) {
                    Map<String, String> cookies = jsonPath.getMap(path + "cookies");
                    request.cookies(cookies);
                }

                String url = jsonPath.getString(path + "url");
                RestAssured.useRelaxedHTTPSValidation();
                response = request.post(url);

                String requestDetails = requestOutputStream.toString();
                String responseDetails = responseOutputStream.toString();
                TestAPIEnd(requestDetails, responseDetails);

                if (Integer.toString(response.statusCode()).equals(status)) {
                    MainUtils.passedAction("[ACTION OK] Se obtuvo respuesta del servicio web");
                    Map<String, String> cookiesMap = response.getCookies();
                    cookiesArray = new String[cookiesMap.size()];
                    int i = 0;
                    for (Map.Entry<String, String> entry : cookiesMap.entrySet()) {
                        cookiesArray[i++] = entry.getKey() + "=" + entry.getValue();
                    }

                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento petición número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION KO] Se ha superado el límite de reintentos para obtener respuesta del servicio web");
                    repeat = false;
                    throw new SkipException("[ACTION STOP] Sin respuesta del servicio web");
                }
            } while (repeat);

            return cookiesArray;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la llamada al servicio web");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Comprueba el estado de una respuesta obtenida en una llamada anterior
     * 
     * @param status: Estado que se quiere comprobar en la respuesta
     * 
     */
    public static void checkStatusResponse(String status) throws Exception {
        try {
            System.out.println("status de la respuesta: " + response.statusCode());
            if (Integer.toString(response.statusCode()).equals(status)) {
                MainUtils.passedCondition(
                        "[CONDITION OK] El estado de la respuesta es correcto: "
                                + Integer.toString(response.statusCode()));

            } else {
                MainUtils.failedCondition(
                        "[CONDITION KO] El estado de la respuesta es incorrecto: "
                                + Integer.toString(response.statusCode()));
                // MainUtils.contadorCheckStatusRequest++;
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[CONDITION EXCEPTION] No se pudo realizar la comprobación del estado de la respuesta");
            // MainUtils.contadorCheckStatusRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Comprueba un texto existente en una respuesta obtenida en una llamada
     * anterior
     * 
     * @param text: Text que se quiere comprobar en la respuesta
     * 
     */
    public static void checkTextResponse(String text) throws Exception {
        try {
            if (response.getBody().asString().contains(text)) {
                MainUtils.passedCondition("[CONDITION OK] Existe el siguiente texto en la respuesta: " + text);
            } else {
                MainUtils.failedCondition(
                        "[CONDITION KO] No se ha encontrado el siguiente texto en la respuesta: " + text);
                // MainUtils.contadorCheckTextRequest++;
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar la comprobación del texto en el cuerpo de la respuesta");
            // MainUtils.contadorCheckTextRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Devuelve el valor de la variable contenida en la respuesta pasada como
     * parámetro
     * La clave debe ser única
     * 
     * @param key:  Clave de la respuesta
     * @param type: Tipo del cuerpo de la respuesta con las siguientes opciones:
     *              - xml
     *              - json
     * 
     */
    public static String getValueResponse(String key, String type) throws Exception {
        try {
            String value;
            switch (type.toLowerCase()) {
                case "json":
                    JsonPath jsonPath = response.jsonPath();
                    value = jsonPath.getString(key);
                    break;
                case "xml":
                    XmlPath xmlPath = response.xmlPath();
                    value = xmlPath.getString("**.find { it.name() == '" + key + "' }.text()");
                    break;
                default:
                    value = null;
                    break;
            }
            if (value != null) {
                MainUtils.passedAction("[ACTION OK] Se ha obtenido el valor de la clave: " + key);
            } else {
                MainUtils.failedCondition(
                        "[CONDITION KO] No se ha encontrado la siguente clave en la respuesta: " + key);
                // MainUtils.contadorGetValueRequest++;
            }
            return value;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar obtener el valor de la clave: " + key);
            // MainUtils.contadorGetValueRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Extrae los datos de un fichero .json y los guarda en un objeto tipo JsonPath
     * 
     * @return JsonPath: objeto tipo JsonPath
     * @throws IOException
     * 
     */
    private static JsonPath readJsonFileToJsonPath() throws IOException {
        String basePath = "./src/test/resources/apiFiles/dataFiles/";
        String jsonFileName;

        // Verifica si hay precondiciones activas y obtiene el nombre del archivo JSON
        // correspondiente
        if (!MainUtils.getPreconditionList().isEmpty()) {
            // Si hay precondiciones activas, usa el ID de la última precondición activa
            // para construir el nombre del archivo
            MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                    .get(MainUtils.getPreconditionList().size() - 1);
            String preconditionId = lastPrecondition.id.split("_")[0]; // Obtiene la parte antes del primer guion bajo
            jsonFileName = preconditionId + "/" + lastPrecondition.id + ".json";
        } else {
            // Si no hay precondiciones activas, usa el ID del test actual
            String testId = MainUtils.testId; // Asegúrate de que testId está siendo establecido correctamente
            jsonFileName = testId.split("_")[0] + "/" + testId.split("_step")[0] + ".json";
        }

        // Lee el contenido del archivo JSON
        String jsonContent = new String(Files.readAllBytes(Paths.get(basePath + jsonFileName)));
        return new JsonPath(jsonContent);
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Función para leer datos desde un archivo JSON y devolverlos en un objeto tipo
     * JsonNode
     * 
     * @return JsonNode: objeto tipo JsonNode
     * 
     */
    public static JsonNode readJson() throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File archivo = new File("./src/test/resources/apiFiles/dataFiles/"
                    + testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json");

            MainUtils.passedAction("[ACTION OK] Se han obtenido los datos del fichero correctamente");
            return objectMapper.readTree(archivo);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se han podido leer los datos del fichero");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Función para escribir datos desde en un objeto tipo Map con formato json
     * 
     * @param data  objeto tipo Map <String,Object>
     * @param key   clave del valor que se quiere modificar
     * @param value valor a modificar
     * 
     */
    public static JsonNode modifyJson(JsonNode originalJson, String[] keys, String newValue) throws Exception {
        try {
            ObjectNode modifiedJson = ((ObjectNode) originalJson).deepCopy();
            ObjectNode currentNode = modifiedJson;
            for (int i = 0; i < keys.length - 1; i++) {
                String key = keys[i];
                if (!currentNode.has(key) || !currentNode.get(key).isObject()) {
                    ObjectNode newNode = currentNode.objectNode();
                    currentNode.set(key, newNode);
                    currentNode = newNode;
                } else {
                    currentNode = (ObjectNode) currentNode.get(key);
                }
            }
            currentNode.put(keys[keys.length - 1], newValue);
            MainUtils.passedAction("[ACTION OK] Se ha modificado el valor de la clave correctamente");
            return modifiedJson;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo modificar el varlor de la clave");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Función para leer el valor de una clave de un xml
     * 
     * @param xmlString objeto tipo Map <String,Object>
     * @param key       clave del valor que se quiere modificar
     * @return Objeto de tipo String con el valor del nuevo xml
     * 
     */
    public static String readXml(String xmlString, String key) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            NodeList nodeList = document.getElementsByTagName(key);

            Element elemento = (Element) nodeList.item(0);
            MainUtils.passedAction("[ACTION OK] Se ha obtenido el valor de la clave correctamente");
            return elemento.getTextContent();

        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo obtener el valor de la clave");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Función para modificar una clave de un xml
     * 
     * @param data  objeto tipo Map <String,Object>
     * @param key   clave del valor que se quiere modificar
     * @param value valor a modificar
     * 
     */
    public static String modifyXml(String xmlString, String key, String value) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            NodeList nodeList = document.getElementsByTagName(key);

            if (nodeList.getLength() > 0) {
                Element elemento = (Element) nodeList.item(0);
                elemento.setTextContent(value);
            } else {
                // Si la etiqueta no existe, puedes crearla si lo deseas
                Element nuevaEtiqueta = document.createElement(key);
                nuevaEtiqueta.appendChild(document.createTextNode(value));
                document.getDocumentElement().appendChild(nuevaEtiqueta);
            }

            // Convertir el documento de nuevo a un string XML modificado
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

            MainUtils.passedAction("[ACTION OK] Se ha modificado el valor de la clave correctamente");
            return stringWriter.toString();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo obtener el valor de la clave");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Modifica el valor de la variable contenida en la respuesta pasada como
     * parámetro
     * La clave debe ser única
     * 
     * @param pathKey:  Clave de la respuesta que se quiere modificar
     * @param newValue: Nuevo valor
     * 
     */
    public static void editJSONData(String pathKey, String newValue) throws Exception {
        try {
            String environment = "PRE";
            String path;
            ObjectMapper objectMapper;
            JsonNode jsonNode;
            JsonNode targetNode;
            String originalValue;
            String jsonModificado;

            path = "/" + environment + "/" + pathKey.replace(".", "/");
            String filePath = "./src/test/resources/apiFiles/dataFiles/"
                    + testId.split("_")[0] + "/" + testId.split("_step")[0] + ".json";

            objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            jsonNode = objectMapper.readTree(new File(filePath));
            targetNode = jsonNode.at(path);
            originalValue = targetNode.isMissingNode() ? null : targetNode.asText();

            if (originalValue != null) {
                ((ObjectNode) jsonNode.at(path.substring(0, path.lastIndexOf("/"))))
                        .put(pathKey.substring(pathKey.lastIndexOf(".") + 1), newValue);
                jsonModificado = objectMapper.writeValueAsString(jsonNode);
                Files.write(Paths.get(filePath), jsonModificado.getBytes(StandardCharsets.UTF_8));
                MainUtils.passedAction(
                        "[ACTION OK] Se ha modificado el valor de la siguiente clave en el fichero: " + pathKey);
            } else {
                MainUtils.failedActionOrImportantCondition(
                        "[ACTION EXCEPTION] La clave especificada no existe en el JSON: " + pathKey);
                // MainUtils.contadorGetValueRequest++;
            }

        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar realizar el cambio de valor de la clave: " + pathKey);
            // MainUtils.contadorGetValueRequest++;
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Modifica el valor de la variable contenida en la respuesta pasada como
     * parámetro
     * La clave debe ser única
     * 
     * @param pathKey:  Clave de la respuesta que se quiere modificar
     * @param newValue: Nuevo valor
     * 
     */
    public static void editJSONDataWithBar(String pathKey, String newValue) throws Exception {
        try {
            String environment = "PRE";
            String basePath = "./src/test/resources/apiFiles/dataFiles/";
            String jsonFileName;

            // Verifica si hay precondiciones activas y obtiene el nombre del archivo JSON
            // correspondiente
            if (!MainUtils.getPreconditionList().isEmpty()) {
                // Si hay precondiciones activas, usa el ID de la última precondición activa
                // para construir el nombre del archivo
                MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                        .get(MainUtils.getPreconditionList().size() - 1);
                String preconditionId = lastPrecondition.id.split("_")[0]; // Obtiene la parte antes del primer guion
                                                                           // bajo
                jsonFileName = preconditionId + "/" + lastPrecondition.id + ".json";
            } else {
                // Si no hay precondiciones activas, usa el ID del test actual
                String testId = MainUtils.testId; // Asegúrate de que testId está siendo establecido correctamente
                jsonFileName = testId.split("_")[0] + "/" + testId.split("_step")[0] + ".json";
            }

            String filePath = basePath + jsonFileName;
            String path = "/" + environment + "/" + pathKey;

            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            JsonNode targetNode = jsonNode.at(path);
            String originalValue = targetNode.isMissingNode() ? null : targetNode.asText();

            if (originalValue != null) {
                ((ObjectNode) jsonNode.at(path.substring(0, path.lastIndexOf("/"))))
                        .put(pathKey.substring(pathKey.lastIndexOf("/") + 1), newValue);
                String jsonModificado = objectMapper.writeValueAsString(jsonNode);
                Files.write(Paths.get(filePath), jsonModificado.getBytes(StandardCharsets.UTF_8));
                MainUtils.passedAction(
                        "[ACTION OK] Se ha modificado el valor de la siguiente clave en el fichero: " + pathKey);
            } else {
                MainUtils.failedActionOrImportantCondition(
                        "[ACTION EXCEPTION] La clave especificada no existe en el JSON: " + pathKey);
            }

        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar realizar el cambio de valor de la clave: " + pathKey);
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Extrae los datos de un fichero .json de la ruta restFile
     * 
     * @return String: objeto tipo String
     * 
     */

    public static String obtainJSONData(String requestedDataID) throws IOException {

        try {
            String testId = MainUtils.testId;

            String environment = "PRE";
            String basePath = "./src/test/resources/apiFiles/dataFiles/";
            String jsonFileName;
            if (!MainUtils.getPreconditionList().isEmpty()) {
                // Si hay precondiciones activas, usa el ID de la última precondición activa
                // para construir el nombre del archivo
                MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                        .get(MainUtils.getPreconditionList().size() - 1);
                String preconditionId = lastPrecondition.id.split("_")[0]; // Obtiene la parte antes del primer guion
                                                                           // bajo
                jsonFileName = preconditionId + "/" + lastPrecondition.id + ".json";
            } else {
                // Si no hay precondiciones activas, usa el ID del test actual
                testId = MainUtils.testId;
                jsonFileName = testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json";
            }
            String jsonContent = new String(Files.readAllBytes(Paths.get(basePath + jsonFileName)));
            JsonPath jsonPath = new JsonPath(jsonContent);
            return jsonPath.get(environment + "." + requestedDataID);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[EXCEPCIÓN DE ACCIÓN] No se pudo realizar la lectura del fichero.");
            throw e;
        }
    }

    public static boolean updateNode(JsonNode node, String key, String newValue) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            if (objectNode.has(key)) {
                objectNode.put(key, newValue);
                return true;
            } else {
                for (JsonNode childNode : objectNode) {
                    if (updateNode(childNode, key, newValue)) {
                        return true;
                    }
                }
            }
        } else if (node.isArray()) {
            for (JsonNode childNode : node) {
                if (updateNode(childNode, key, newValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Edita un valor del json body
     * 
     * @param indexJSON indice del json de repositorio de datos
     * @param key       key del documento el cual queremos cambiar el valor
     * @param value     nuevo valor que queremos añadir al documento
     * 
     */

    public static void editBodyJSON(String indexJSON, String key, String newValue) throws Exception {
        try {
            String basePath = "./src/test/resources/apiFiles/dataFiles/";
            String jsonFileName;

            // Verifica si hay precondiciones activas y obtiene el nombre del archivo JSON
            // correspondiente
            if (!MainUtils.getPreconditionList().isEmpty()) {
                // Si hay precondiciones activas, usa el ID de la última precondición activa
                // para construir el nombre del archivo
                MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                        .get(MainUtils.getPreconditionList().size() - 1);
                String preconditionId = lastPrecondition.id.split("_")[0]; // Obtiene la parte antes del primer guion
                                                                           // bajo
                jsonFileName = preconditionId + "/" + lastPrecondition.id + ".json";
            } else {
                // Si no hay precondiciones activas, usa el ID del test actual
                String testId = MainUtils.testId; // Asegúrate de que testId está siendo establecido correctamente
                jsonFileName = testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json";
            }

            String filePath = basePath + jsonFileName;
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            JsonNode bodyNode = jsonNode.path("PRE").path(indexJSON).path("body");

            if (!bodyNode.isMissingNode() && bodyNode.isTextual()) {
                String bodyContent = bodyNode.asText();
                JsonNode bodyContentNode = objectMapper.readTree(bodyContent);

                // Variable para rastrear si se ha actualizado el nodo
                boolean updated = false;

                // Cola para nodos pendientes de revisión
                LinkedList<JsonNode> nodes = new LinkedList<>();
                nodes.add(bodyContentNode);

                while (!nodes.isEmpty()) {
                    JsonNode currentNode = nodes.poll();
                    if (currentNode.isObject()) {
                        ObjectNode objectNode = (ObjectNode) currentNode;
                        if (objectNode.has(key)) {
                            objectNode.put(key, newValue);
                            updated = true;
                            break;
                        } else {
                            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
                            while (fields.hasNext()) {
                                Map.Entry<String, JsonNode> entry = fields.next();
                                nodes.add(entry.getValue());
                            }
                        }
                    } else if (currentNode.isArray()) {
                        for (JsonNode childNode : currentNode) {
                            nodes.add(childNode);
                        }
                    }
                }

                if (updated) {
                    String updatedBodyContent = objectMapper.writeValueAsString(bodyContentNode);
                    ((ObjectNode) jsonNode.path("PRE").path(indexJSON)).put("body", updatedBodyContent);
                    objectMapper.writeValue(new File(filePath), jsonNode);
                    MainUtils.passedAction(
                            "[ACTION OK] Se ha modificado el valor de la siguiente clave en el JSON body: " + key);
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "[ACTION EXCEPTION] No se encontró la clave \"" + key + "\" para editar en el JSON body.");
                }
            } else {
                MainUtils.failedActionOrImportantCondition(
                        "[ACTION EXCEPTION] No se pudo encontrar el body para editar o no es textual.");
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("[ACTION EXCEPTION] No se pudo editar el valor de " + key + ".");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Renueva las cookies, previamente almacenadas en una variable, del JSON del
     * test en ejecucion.
     * 
     * @return String: objeto tipo String
     * @throws Exception
     * 
     */

    @SuppressWarnings("unused")
    public static void renewCookies(String[] cookies) throws Exception {

        try {
            String testId = MainUtils.testId;
            String jsonFileName;
            if (!MainUtils.getPreconditionList().isEmpty()) {
                // Si hay precondiciones activas, usa el ID de la última precondición activa
                // para construir el nombre del archivo
                MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                        .get(MainUtils.getPreconditionList().size() - 1);
                String preconditionId = lastPrecondition.id.split("_")[0]; // Obtiene la parte antes del primer guion
                                                                           // bajo
                jsonFileName = preconditionId + "/" + lastPrecondition.id + ".json";
            } else {
                // Si no hay precondiciones activas, usa el ID del test actual
                testId = MainUtils.testId;
                jsonFileName = testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json";
            }
            for (int i = 0; i < cookies.length; i++) {
                String key = cookies[i].split("=")[0];
                String value = cookies[i].split("=")[1];
                editJSONDataWithBar(MainUtils.stepTest() + "/cookies/" + key, value);
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[EXCEPCIÓN DE ACCIÓN] No se pudo realizar la lectura del fichero.");
            throw e;
        }
    }

    /**
     * <strong>API REST ONLY</strong><br/>
     * <br/>
     * Extrae los datos de un fichero .json de la ruta webFiles
     * 
     * @return String: objeto tipo String
     * 
     */
    @SuppressWarnings("unused")
    public static void TestAPIEnd(String request, String response) throws IOException {
        try {
            String requestEscaped = request
                    .replace("<", "")
                    .replace(">", "")
                    .replace("\b", "")
                    .replace("\f", "")
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\t", "").trim();

            String responseEscaped = response
                    .replace("<", "\"")
                    .replace(">", "\"")
                    .replace("\b", "")
                    .replace("\f", "")
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\t", "").trim();

            System.out.println("vemos responseEscaped: " + responseEscaped);

            String reqMethod = "";
            String reqURI = "";
            String reqProxy = "";
            String reqRequestParams = "";
            String reqQueryParams = "";
            String reqFormParams = "";
            String reqPathParams = "";
            String reqHeaders = "";
            String reqCookies = "";
            String reqMultipart = "";

            String reqBody = "";
            String resStatus = "";
            String resHeader = "";
            String resBody = "";

            // Paso 1: Dividir la cadena en segmentos
            String[] reqSplit = requestEscaped.split("Request method:");
            if (reqSplit.length > 1) {
                String[] parts = reqSplit[1].split("URI:");
                reqMethod = parts[0].trim();

                if (parts.length > 1) {
                    parts = parts[1].split("Proxy:");
                    reqURI = parts[0].trim();

                    if (parts.length > 1) {
                        parts = parts[1].split("Request params:");
                        reqProxy = parts[0].trim();

                        if (parts.length > 1) {
                            parts = parts[1].split("Query params:");
                            reqRequestParams = parts[0].trim();

                            if (parts.length > 1) {
                                parts = parts[1].split("Form params:");
                                reqQueryParams = parts[0].trim();

                                if (parts.length > 1) {
                                    parts = parts[1].split("Path params:");
                                    reqFormParams = parts[0].trim();

                                    if (parts.length > 1) {
                                        parts = parts[1].split("Headers:");
                                        reqPathParams = parts[0].trim();

                                        if (parts.length > 1) {
                                            parts = parts[1].split("Cookies:");
                                            reqHeaders = parts[0].trim();

                                            if (parts.length > 1) {
                                                parts = parts[1].split("Multiparts:");
                                                reqCookies = parts[0].trim();

                                                if (parts.length > 1) {
                                                    parts = parts[1].split("Body:");
                                                    reqMultipart = parts[0].trim();
                                                    if (parts.length > 1) {
                                                        reqBody = parts[1].trim();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            String[] resSplit = responseEscaped.split("Cache-Control:");
            if (resSplit.length > 1) {
                String[] parts = resSplit[0].split("HTTP/");
                resStatus = "HTTP/ " + parts[1].trim();

                if (resSplit.length > 1) {
                    parts = resSplit[1].split("Content-Length: \\s*\\d+\\s*");
                    resHeader = parts[0].trim();

                    if (parts.length > 1) {
                        resBody = parts[1].trim();
                    }
                }
            }

            APIEvidence.RequestDetails req = new RequestDetails(reqMethod, reqURI, reqHeaders, reqCookies, reqBody);

            System.out.println("vemos resStatus: " + resStatus);
            System.out.println("vemos resHeader: " + resHeader);
            System.out.println("vemos resBody:" + resBody);
            resBody = responseEscaped;
            APIEvidence.ResponseDetails res = new ResponseDetails(resStatus, resHeader, resBody);

            System.out.println("vemos req:" + req);
            System.out.println("vemos res:" + res);
            APIEvidence apiEvidence = new APIEvidence(req, res);
            System.out.println("vemos apiEvidence:" + apiEvidence);
            StringToJsonFile.convertAPIStringToJsonFile(apiEvidence);
            MainUtils.imageCount++;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("[ACTION EXCEPTION] No se pudo generar los JSON del TC.");
            throw e;
        }
    }
}
