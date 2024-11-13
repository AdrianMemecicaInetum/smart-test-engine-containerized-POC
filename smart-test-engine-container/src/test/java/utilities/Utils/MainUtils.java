package utilities.Utils;

import net.coobird.thumbnailator.Thumbnails;
//import org.sejda.imageio.webp.WebPImageWriter;
//import org.sejda.imageio.webp.WebPImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import java.io.FileReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import utilities.Report.AzureAPI;
import utilities.Report.Evidence;
import utilities.Report.PreconditionSteps;
import utilities.Report.ResumeReport;
import utilities.Report.Steps;
import utilities.Report.StringToHTMLFile;
import utilities.Report.StringToJsonFile;
import utilities.Report.Test;
import utilities.Report.TestSuite;
import io.appium.java_client.TouchAction;
import io.restassured.path.json.JsonPath;

public class MainUtils {

    public static String testId;
    public int captura;
    private static boolean conditionsTrigger;
    static TouchAction<?> touchAction;
    public int[] enterKeyCoor;
    static int maxRetries = 3;
    static int contadorTextoIncorrecto;
    static int contadorAtributoIncorrecto;
    static int contadorTap;
    static int contadorLongTap;
    static int contadorTapCoordenadas;
    static int contadorSendkeys;
    static int contadorSendkeysRepository;
    static int contadorForceWait;
    static int contadorClear;
    static int contadorSwipe;
    static int contadorIsObjectDisplayed;
    static int contadorIsObjectNotDisplayed;
    static int contadorIsTextInObject;
    static int contadorIsDataInObject;
    static int contadorIsAttributeInObject;
    static int contadorScroll;
    static int contadorWaitElement;
    // static int contadorCheckStatusRequest;
    // static int contadorCheckTextRequest;
    // static int contadorGetValueRequest;
    // static int contadorGetRequest;
    // static int contadorPostRequest;

    public static long startTestExecution;
    public static long startTotalExecutionTime;
    public static long endTestExecution;
    public static long currentTestExecution;
    private static ResumeReport resumeReport;
    private static Map<String, TestSuite> testSuites = new HashMap<String, TestSuite>();
    private static TestSuite currentTestSuite;
    public static List<Test> tests;
    public static Test currentTest;
    private static List<Steps> steps;
    private static List<Evidence> actions;
    private static List<Evidence> conditions;
    private static Evidence currentEvidence;
    public static Integer currentStep;
    private static Steps currentSteps;
    private static PreconditionSteps currentPreconditionSteps;

    private static String currentPath = "";

    public static String dmahms;

    public static String testType;

    private static MainUtils mainUtils_instance = null;

    private static Map<String, List<String>> dataRepository = new HashMap<String, List<String>>();

    private static Map<String, List<String>> objectRepository = new HashMap<String, List<String>>();

    private static Boolean testSuiteEndExecuted = false;

    public static List<Precondition> preconditionList = new ArrayList<>();

    public static String codigoConfirmacion;
    static int imageCount;
    static int maxLista = 0;
    static int posicionInversa = 1;
    public static int contadorDragAndDrop;
    public static int contadorCheckStatus;

    public static class Precondition {
        public String id;
        int level;

        public Precondition(String id, int level) {
            this.id = id;
            this.level = level;
        }
    }

    public static List<Precondition> getPreconditionList() {
        return preconditionList;
    }

    public static void imageSum() {
        imageCount++;
    }

    public static int imageGet() {
        return imageCount;
    }

    public static void startPrecondition(String preconditionId) {
        int nextLevel = preconditionList.size() + 1;
        preconditionList.add(new Precondition(preconditionId, nextLevel));
    }

    public static void endPrecondition(String preconditionId) {
        preconditionList.removeIf(p -> p.id.equals(preconditionId));
        if (preconditionList.size() < maxLista) {
            posicionInversa++;
            maxLista = preconditionList.size();
        }
    }

    public static String getCurrentPreconditionSequence() {
        if (!preconditionList.isEmpty()) {
            if (preconditionList.size() > maxLista) {
                maxLista = preconditionList.size();
            }

            if (maxLista > preconditionList.size()) {
                posicionInversa++;
                maxLista--;

            }

            return "" + posicionInversa;
        } else {
            // No hay precondiciones activas
            return "";
        }
    }

    // Instanciar clases de test con patrón Singleton
    public static MainUtils setInstance() {

        if (mainUtils_instance == null) {
            startTotalExecutionTime = System.currentTimeMillis();
            mainUtils_instance = new MainUtils();
            DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            dmahms = formatter.format(new Date());
        }
        return mainUtils_instance;
    }

    private MainUtils() {
        captura = 0;
        contadorTap = 0;
        contadorLongTap = 0;
        contadorTapCoordenadas = 0;
        contadorSendkeys = 0;
        contadorSendkeysRepository = 0;
        contadorForceWait = 0;
        contadorClear = 0;
        contadorSwipe = 0;
        contadorIsObjectDisplayed = 0;
        contadorIsObjectNotDisplayed = 0;
        contadorIsTextInObject = 0;
        contadorIsDataInObject = 0;
        contadorIsAttributeInObject = 0;
        contadorScroll = 0;
        contadorWaitElement = 0;
        contadorCheckStatus = 0;
        contadorDragAndDrop = 0;
        // contadorGetRequest = 0;
        // contadorCheckStatusRequest = 0;
        // contadorCheckTextRequest = 0;
        // contadorGetValueRequest = 0;
        // contadorPostRequest = 0;
        conditionsTrigger = false;
    }

    public static void setConditionsTrigger(boolean activador) {
        conditionsTrigger = activador;
    }

    public static boolean getConditionsTrigger() {
        return conditionsTrigger;
    }

    public static String getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    /**************************************************
     * 
     * ACCIONES COMUNES
     * 
     **************************************************/

    /**
     * Método para forzar una espera del sistema.
     * 
     * @param duration Duration
     */
    public static void forceWait(Duration duration) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(duration);
            printReportMessage("[WAIT] " + duration.getSeconds() + " segs.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para hacer split de un string
     * 
     * @param beforeSplit
     * @param separador
     */
    public static String[] SplitString(String beforeSplit, String separador) {

        String[] afterSplit = beforeSplit.split(separador);
        return afterSplit;
    }

    /**
     * Método capturador de excepciones, extensible a toda la aplicación
     * 
     * @param fileName
     * @param message
     * @param calidad
     * @throws IOException
     */
    public static void screenShotRecord(String fileName, String message, int calidad) {
        try {
            // Captura de pantalla
            File scrFile;
            if (MainUtils.testType.equals("WEB")) {
                scrFile = ((TakesScreenshot) DriverUtils.getDriver()).getScreenshotAs(OutputType.FILE);
            } else {
                scrFile = ((TakesScreenshot) DriverUtils.getDriverMobile()).getScreenshotAs(OutputType.FILE);
            }

            String imagePath = "./resume-report/" + dmahms.substring(0, 10) + "/report"
                    + dmahms.substring(10) + "/" + splitString(testId, "_TC")[0] + "/" + testId + "/"
                    + String.format("%03d", imageCount) + " - " + fileName + ".jpeg";
            FileUtils.copyFile(scrFile, new File(imagePath));
            // printReportMessage("[FRAMEWORK] Evidencia generada:" + "capturas/" +
            // splitString(testId, "_")[0] + "/"
            // + testId + "/" + fileName);
            currentPath = "capturas/" + splitString(testId, "_")[0] + "/" + testId + "/" + fileName;
            BufferedImage image = ImageIO.read(scrFile);
            imageSum();
            float webpQuality;
            switch (calidad) {
                case 1:
                    Thumbnails.of(scrFile).scale(1).outputFormat("jpeg").toFile(imagePath);
                    webpQuality = 1.0f;
                    System.out.println("Hemos cogido la calidad1");
                    break;
                case 2:
                    Thumbnails.of(scrFile).scale(0.7).outputFormat("jpeg").toFile(imagePath);
                    webpQuality = 0.7f;
                    System.out.println("Hemos cogido la calidad2");
                    break;
                case 3:
                    Thumbnails.of(scrFile).scale(0.2).outputFormat("jpeg").toFile(imagePath);
                    webpQuality = 0.2f;
                    System.out.println("Hemos cogido la calidad3");
                    break;
                default:
                    webpQuality = 0.7f;
                    System.out.println("Calidad no especificada");
            }

            File outputWebP = new File(imagePath.replace(".jpeg", ".webp"));
            saveAsWebP(image, outputWebP, webpQuality);
            File jpegFile = new File(imagePath);
            if (jpegFile.exists()) {
                jpegFile.delete();
            }
        } catch (IOException e) {
            printReportMessage("[FRAMEWORK] Error al capturar excepción");
            e.printStackTrace();
        }
    }

    /**
     * Método capturador de excepciones, extensible a toda la aplicación
     * 
     * @param fileName
     * @param message
     * @throws IOException
     */
    public static void screenShotRecord(String fileName, String message) {
        if (MainUtils.testType.equals("WEB")) {
            screenShotRecord(fileName, message, 3);
        } else {
            screenShotRecord(fileName, message, 3);
        }
    }

    private static void saveAsWebP(BufferedImage image, File outputFile, float quality) throws IOException {
        System.out.println("Hemos PARA WEBP LA CALIDAD " + quality);
        ImageIO.scanForPlugins(); // Asegúrate de que el plugin esté cargado

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("webp");
        if (!writers.hasNext()) {
            throw new IOException(
                    "No WebP ImageWriter found. Ensure the webp-imageio library is configured correctly.");
        }

        ImageWriter writer = writers.next();
        try (ImageOutputStream output = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(output);
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            System.out.println("Hemos llegado hasta aqui");

            if (writeParam.canWriteCompressed()) {
                System.out.println("Hemos llegado hasta el if ");
                writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                String[] compressionTypes = writeParam.getCompressionTypes();
                if (compressionTypes != null && compressionTypes.length > 0) {
                    writeParam.setCompressionType(compressionTypes[0]); // Selecciona el primer tipo de compresión
                    System.out.println("WOW" + compressionTypes[0]);
                }
                // Establecer la calidad de compresión
                writeParam.setCompressionQuality(quality);
                System.out.println("Hemos ajustado la calidad: " + quality);
            } else {
                System.out.println("Compression not supported by this ImageWriteParam.");
            }

            try {
                writer.write(null, new IIOImage(image, null, null), writeParam);
                System.out.println("Imagen guardada correctamente en formato WebP.");
            } catch (IOException e) {
                System.out.println("Error al guardar la imagen en formato WebP: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            System.out.println("ADIOS");
            writer.dispose();
        }
    }

    /**
     * Método para hacer split de un string
     * 
     * @param beforeSplit
     * @param separador
     */
    public static String[] splitString(String beforeSplit, String separador) {

        String[] afterSplit = beforeSplit.split(separador);
        return afterSplit;
    }

    /**
     * Genera archivo json con la informacion enviada y lo agrega al reporte
     *
     * @param fileName
     * @param content
     */
    public static void createJsonRecord(String fileName, String content) {
        // String path = SplitString(testId, "_")[0] + "/";
        // createJsonFile(fileName, content, DataStore.INSTANCE.getJsonFilesFolder() +
        // "/" + path);
        // printReportMessage("[FRAMEWORK] Evidencia generada:" +
        // DataStore.INSTANCE.getJsonFilesFolder() + "/+ path + fileName);
    }

    /**
     * Método que imprime por consola y en el report.
     * 
     * @param message
     */
    public static void printReportMessage(String message) {
        if (currentStep == 0) {
            System.out.println("[" + testId + "]" + message);
        } else {
            System.out.println("[" + testId + "_STEP" + currentStep + "]" + message);
        }
    }

    /**
     * Método que devuelve el testId + step
     * 
     * @param message
     */
    public static String stepTest() {
        if (!MainUtils.getPreconditionList().isEmpty()) {
            MainUtils.Precondition lastPrecondition = MainUtils.getPreconditionList()
                    .get(MainUtils.getPreconditionList().size() - 1);

            return lastPrecondition.id + "_step" + MainUtils.currentStep;
        } else {
            return testId + "_step" + MainUtils.currentStep;
        }
    }

    /**************************************************
     * 
     * Data Repository
     * 
     **************************************************/

    public Map<String, List<String>> getDataRepository() {
        return dataRepository;
    }

    /**
     * Read all data of the csv and store as a database.
     */
    public void testDataReader(String csvFile) {
        Path myPath = Paths.get(csvFile);

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        try (CSVReader reader = new CSVReaderBuilder(Files.newBufferedReader(myPath, StandardCharsets.UTF_8))
                .withCSVParser(csvParser).build()) {
            String[] lineInArray = null;
            List<String> dataList = new ArrayList<String>();
            while ((lineInArray = reader.readNext()) != null) {

                // Filas con con solo 1 columna son para comentarios
                if (lineInArray.length > 1) {
                    dataList = Arrays.asList(lineInArray);
                    dataRepository.put(lineInArray[0], dataList.subList(1, lineInArray.length));
                }
            }
            printReportMessage("[FRAMEWORK] Se pudieron recuperar los valores del fichero de datos: " + csvFile);
        } catch (Exception e) {
            printReportMessage(
                    "[FRAMEWORK ERROR] No se pudieron recuperar los valores del fichero de datos: " + csvFile);
            e.printStackTrace();
        }
    }

    /**
     * Take the data from csv file with testID and the name of the column data.
     * 
     * @param testID     The testID of the test case that you need to search.
     * @param columnData The name of the column data that you need to take.
     * @return The value of the column data in the line of the testID in the csv.
     */
    public static String getDataFromRepository(String testID, String columnData) {
        try {
            int indexColumnData = dataRepository.get(DriverUtils.getRowIdentifier()).indexOf(columnData);
            return dataRepository.get(testID).get(indexColumnData);
        } catch (Exception e) {
            printReportMessage("[FRAMEWORK ERROR] Repositorio de Datos. No se pudo recuperar el valor del elemento "
                    + columnData + " del caso " + testID);
            throw e;
        }

    }

    /***
     * Add a new column into the map and add the value.
     * 
     * @param testID
     * @param //newCaseID
     */
    public void addData(String testID, String columnData, String data) {
        int indexColumnData;
        // añade la columna en la primera fila
        if (dataRepository.get(DriverUtils.getRowIdentifier()).indexOf(columnData) != -1) {
            indexColumnData = dataRepository.get(DriverUtils.getRowIdentifier()).indexOf(columnData);
        } else {
            dataRepository.get(DriverUtils.getRowIdentifier()).add(columnData);
            indexColumnData = dataRepository.get(DriverUtils.getRowIdentifier()).indexOf(columnData);
        }
        dataRepository.get(DriverUtils.getRowIdentifier()).get(indexColumnData).equals(data);
        // Ultimo paso - Añade el nuevo valor a la fila correspondiente
        List<String> changedList = dataRepository.get(testID);
        changedList.set(indexColumnData, data);
        dataRepository.put(testID, changedList);

    }

    /**************************************************
     * 
     * Object Repository
     * 
     **************************************************/

    /**
     * Read all data of the csv and store as a database.
     */
    public void objectRepositoryReader(String csvFile) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFile)).withCSVParser(csvParser).build()) {
            String[] lineInArray;
            List<String> dataList = new ArrayList<String>();
            lineInArray = reader.readNext();
            while ((lineInArray = reader.readNext()) != null) {
                if (lineInArray.length == 3) {
                    dataList = Arrays.asList(lineInArray);
                    objectRepository.put(lineInArray[0], dataList.subList(1, lineInArray.length));
                }
            }
            printReportMessage("[FRAMEWORK] Se pudieron recuperar los valores del fichero de Objetos: " + csvFile);
        } catch (Exception e) {
            printReportMessage(
                    "[FRAMEWORK ERROR] No se pudieron recuperar los valores del fichero de objetos: " + csvFile);
            e.printStackTrace();
        }
    }

    /**
     * Take the data from csv file with objectName and the name of the column data.
     *
     * @param objectName The objectName of the test case that you need to search.
     * @return The value of the column data in the line of the objectName in the
     *         csv.
     */
    public static String objectID(String objectName) {
        try {
            return objectRepository.get(objectName).get(1);
        } catch (NullPointerException npe) {
            printReportMessage("[FRAMEWORK ERROR] Devuelve un NULL del repositorio");
            throw npe;
        } catch (Exception e) {
            printReportMessage("[FRAMEWORK ERROR] Repositorio de Objetos. Identificador de objeto: " + objectName
                    + ". No se encuentra en el repositorio de Objetos");
            throw e;
        }
    }

    /**
     * Take the data from csv file with objectName and the name of the column data.
     *
     * @param objectName The objectName of the test case that you need to search.
     * @return The type value of the column data in the line of the objectName in
     *         the csv.
     * @throws Exception objectName not found
     */
    public String typeObjectID(String objectName) {
        try {
            return objectRepository.get(objectName).get(0);
        } catch (NullPointerException npe) {
            printReportMessage("[FRAMEWORK ERROR] Devuelve un NULL del repositorio");
            throw npe;
        } catch (Exception e) {
            printReportMessage("[FRAMEWORK ERROR] Repositorio de Objetos. Tipo de objeto: " + objectName
                    + ". No se encuentra en el repositorio de Objetos");
            throw e;
        }
    }

    /**************************************************
     * 
     * SQL Database Connection
     * 
     **************************************************/

    public static void ConexionBD(String medico) {

        Connection conn = null;

        try {
            // String conexion
            String dbURL = "jdbc:sqlserver://" + "10.170.5.109;" +
            // "CPDCASIOBD-PREN.idc.local;" +
                    "databaseName=Casiopea.MS.Security;";

            // Maquina MAC
            // "10.170.5.109;" +
            // Maquina Local
            // "CPDCASIOBD-PREN.idc.local;" +

            String user = "casiopea.HCE";
            String pass = "HCE";

            /*
                    */

            // driver
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            // DriverManager.registerDriver(new SQLServerDriver());
            // DriverManager.registerDriver(new
            // com.microsoft.sqlserver.jdbc.SQLServerDriver());
            // Class.forName("com.mysql.jdbc.Driver");

            // Conexion
            conn = DriverManager.getConnection(dbURL, user, pass);
            // conn.setAutoCommit(false);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("Conexion BD correcta: " + conn.isValid(60));

                // Conexion valida
                System.out.println("Conexion BD correcta: " + conn.isValid(60));

                // Ejecución QUERY
                String selectSql = "SELECT top (1) [VerifyCode]\n"
                        + "FROM [Casiopea.MS.WhiteList].[WhiteList].[VerifyCode] v with (nolock)\n"
                        + "inner join [Casiopea.MS.WhiteList].[WhiteList].[User] u with (nolock) on u.Id=v.UserId\n"
                        + "where UserName = '" + medico + "'\n" + "order by v.InsertDate DESC";

                System.out.println("Query a bbdd: " + selectSql);

                // v with (nolock)
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql);

                // Visualizar Resultados
                if (resultSet.next() == false) {
                    System.out.println("ResultSet in empty in Java");
                } else {

                    do {
                        String data = resultSet.getString(1);
                        printReportMessage("Código de confirmación recuperado correctamente: " + data);
                        codigoConfirmacion = data; // Exportar
                    } while (resultSet.next());
                }
            }

        } catch (SQLException ex) {
            MainUtils.screenShotRecord("ConexionBD",
                    "No se ha podido realizar correctamente la conexión a la BD");
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                MainUtils.screenShotRecord("ConexionBD",
                        "No se ha podido realizar correctamente la conexión a la BD");
                ex.printStackTrace();
            }
        }
    }

    /**************************************************
     * 
     * Acciones específicas de proyecto
     * 
     **************************************************/

    /*
     * NO USAR
     */
    public static void testSuiteGeneration(String testId) throws IOException, InterruptedException {
        MainUtils.testId = testId;
        currentStep = 0;

        /*
         * String hostname = System.getenv("COMPUTERNAME");
         * String hostLuis = "GFI12971";
         * if (hostname == null) {
         * DriverUtils.restauraFabricaAndroid();
         * } else if (hostname.equals(hostLuis)) {
         * printReportMessage("[FRAMEWORK] No es necesario restaurar Android en PC.");
         * }
         * androidDriver = null;
         */
        currentTestSuite = new TestSuite(testId);
        testSuites.put(testId, currentTestSuite);
    }

    public static void testExecutionTimeCalculator(long millis) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        printReportMessage("[FRAMEWORK] El test " + testId + " ha finalizado con la siguiente información: ");
        printReportMessage("[FRAMEWORK] Estado -> " + currentTest.getStatus());
        printReportMessage("[FRAMEWORK] Tiempo de ejecución en HH:mm:ss -> " + hms);
        printReportMessage("[FRAMEWORK] Número de acciones -> " + currentTest.getActionExecuted());
        printReportMessage("[FRAMEWORK] Número de condiciones -> " + currentTest.getConditionExecuted());
        printReportMessage("[FRAMEWORK] Registrando datos en el reporte de resultados... loading...");
        currentTest.setExecutionTime(hms);
    }

    public static void testInicialization(String testId) {
        imageCount = 1;
        maxLista = 0;
        posicionInversa = 1;
        MainUtils.testId = testId;
        currentTest = new Test(testId);
        currentTestSuite.getTests().add(currentTest);

    }

    public static void testInicialization(String testId, String testDescription) {
        testDescription = testDescription
                .replace("\\", "\\\\") // Escapa las barras invertidas
                .replace("\"", "\\\"") // Escapa las comillas dobles
                .replace("\b", "\\b") // Escapa el retroceso
                .replace("\f", "\\f") // Escapa el avance de página
                .replace("\n", "\\n") // Escapa el salto de línea
                .replace("\r", "\\r") // Escapa el retorno de carro
                .replace("\t", "\\t"); // Escapa el tabulador
        imageCount = 1;
        maxLista = 0;
        posicionInversa = 1;
        MainUtils.testId = testId;
        currentTest = new Test(testId, testDescription);
        currentTestSuite.getTests().add(currentTest);

    }

    public static void newEvidence(String status, String value, String type) {
        String stepId;
        String evidenceValue;
        value = value.replace("\"", "'");
        if (!preconditionList.isEmpty()) {
            stepId = getCurrentPreconditionSequence() + "." + currentStep;
            evidenceValue = "[PRECONDITION " + preconditionList.get(preconditionList.size() - 1).id + "] " + value;
        } else {
            stepId = Integer.toString(currentStep);
            evidenceValue = value;
        }

        // Assuming Evidence and evidences are defined elsewhere

        Evidence currentEvidence = new Evidence(status, evidenceValue, String.format("%03d", imageCount - 1));

        if (!preconditionList.isEmpty()) {
            currentPreconditionSteps.setStep(stepId);
            if (type == "Action") {
                currentPreconditionSteps.getActions().add(currentEvidence);
            } else {
                currentPreconditionSteps.getConditions().add(currentEvidence);
            }
        } else {
            currentSteps.setStep(stepId);
            if (type == "Action") {
                currentSteps.getActions().add(currentEvidence);
            } else {
                currentSteps.getConditions().add(currentEvidence);
            }
        }

    }

    public static void failedActionOrImportantCondition(String fileName, String errorTrace, String type) {
        printReportMessage(errorTrace);
        newEvidence("FAILED", errorTrace, type);
        screenShotRecord(fileName, errorTrace);
        currentTest.setActionExecuted(currentTest.getActionExecuted() + 1);
        currentTest.setStatus("FAILED");
    }

    public static void failedActionOrImportantCondition(String fileName, String errorTrace) {
        printReportMessage(errorTrace);
        newEvidence("FAILED", errorTrace, "Action");
        screenShotRecord(fileName, errorTrace);
        currentTest.setActionExecuted(currentTest.getActionExecuted() + 1);
        currentTest.setStatus("FAILED");
    }

    public static void failedActionOrImportantCondition(String errorTrace) {
        printReportMessage(errorTrace);
        newEvidence("FAILED", errorTrace, "Action");
        currentTest.setActionExecuted(currentTest.getActionExecuted() + 1);
        currentTest.setStatus("FAILED");
    }

    public static void passedAction(String value) {
        newEvidence("PASSED", value, "Action");
        printReportMessage(value);
        currentTest.setActionExecuted(currentTest.getActionExecuted() + 1);
        if (currentTest.getStatus() == "") {
            currentTest.setStatus("PASSED");
        }
    }

    public static void failedCondition(String fileName, String errorTrace) {
        printReportMessage(errorTrace);
        screenShotRecord(fileName, errorTrace);
        newEvidence("PASSED WITH FAILURES", errorTrace, "Condition");
        currentTest.setConditionExecuted(currentTest.getConditionExecuted() + 1);
        currentTest.setConditionsKO(currentTest.getConditionsKO() + 1);
        if (currentTest.getStatus() == "PASSED") {
            currentTest.setStatus("PASSED WITH FAILURES");
        }
    }

    public static void failedCondition(String errorTrace) {
        printReportMessage(errorTrace);
        newEvidence("PASSED WITH FAILURES", errorTrace, "Condition");
        currentTest.setConditionExecuted(currentTest.getConditionExecuted() + 1);
        currentTest.setConditionsKO(currentTest.getConditionsKO() + 1);
        if (currentTest.getStatus() == "PASSED") {
            currentTest.setStatus("PASSED WITH FAILURES");
        }
    }

    public static void passedCondition(String value) {
        newEvidence("PASSED", value, "Condition");
        printReportMessage(value);
        currentTest.setConditionExecuted(currentTest.getConditionExecuted() + 1);
        if (currentTest.getStatus() == "") {
            currentTest.setStatus("PASSED");
        }
    }

    public static void testSuiteEnd() throws ParseException {
        if (currentStep != null) {
            if (!testSuiteEndExecuted) {
                printReportMessage("Genera reporte");
                resumeReport = new ResumeReport(testSuites.values());
                resumeReport.setDmahms(dmahms);
                StringToJsonFile.convertStringToJsonFile(resumeReport);
                StringToHTMLFile.convertStringToHTMLFile(resumeReport);
                AzureAPI.AzureExecutions(resumeReport);
                testSuiteEndExecuted = true;
            } else {
                printReportMessage("NO Genera reporte");
            }
        }
    }

    public static void highlightElement(String xpath, String Tipo) {

        if (!testType.equals("MOBILE")) {
            JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getDriver();
            WebElement elemento = DriverUtils.searchWebElementByXPATH(xpath);
            String stilosActuales = elemento.getAttribute("style");
            String nuevosEstilos;

            if (Tipo.equals("Accion")) {
                nuevosEstilos = "border: 5px dashed red !important; z-index: 9999;";
            } else {
                nuevosEstilos = "border: 5px dashed blue !important; z-index: 9999;";
            }

            // Combina los estilos actuales con los nuevos estilos
            String estilosCombinados = (stilosActuales != null && !stilosActuales.isEmpty())
                    ? stilosActuales + nuevosEstilos
                    : nuevosEstilos;

            // Aplica los estilos combinados al elemento
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", elemento, estilosCombinados);

            MainUtils.forceWait(Duration.ofMillis(500));
            MainUtils.screenShotRecord(testId, "[SCREEN_CAPTURE] Captura evidencia del xpath (" + xpath + ")");

            // Restaura los estilos originales
            if (stilosActuales != null && !stilosActuales.isEmpty()) {
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", elemento, stilosActuales);
            } else {
                js.executeScript("arguments[0].removeAttribute('style');", elemento);
            }

        } else {
            MainUtils.screenShotRecord(testId, "[SCREEN_CAPTURE] Captura evidencia del xpath (" + xpath + ")");
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
    public static String obtainJSONData(String requestedDataID) throws IOException {
        try {
            String testId = MainUtils.testId;
            String environment = "PRE";
            String jsonContent = "";
            if (!testType.equals("MOBILE")) {
                jsonContent = new String(Files.readAllBytes(Paths.get("./src/test/resources/webFiles/dataFiles/"
                        + testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json")));
            } else {
                jsonContent = new String(Files.readAllBytes(Paths.get("./src/test/resources/mobileFiles/dataFiles/"
                        + testId.split("_TC")[0] + "/" + testId.split("_step")[0] + ".json")));
            }
            JsonPath jsonPath = new JsonPath(jsonContent);
            return jsonPath.get(environment + "." + requestedDataID);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar realizar la lectura del fichero.");
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
    public static String obtainSharedJSONData(String requestedDataID) throws IOException {
        try {
            String environment = "PRE";
            String jsonContent = "";
            if (!testType.equals("MOBILE")) {
                jsonContent = new String(Files.readAllBytes(Paths.get("./src/test/resources/webFiles/dataFiles/"
                        + MainUtils.testId.split("_")[0] + "/" + MainUtils.testId.split("_")[0] + ".json")));
            } else {
                jsonContent = new String(Files.readAllBytes(Paths.get("./src/test/resources/webFiles/dataFiles/"
                        + MainUtils.testId.split("_")[0] + "/" + MainUtils.testId.split("_")[0] + ".json")));
            }
            JsonPath jsonPath = new JsonPath(jsonContent);
            return jsonPath.get(environment + "." + requestedDataID);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] No se pudo realizar realizar la lectura del fichero.");
            throw e;
        }
    }

    public static void testEnd(String methodName, String className) throws Exception {
        int retries = 1;
        boolean repeat = false;
        do {
            try {
                if (currentTest.getStatus().equals("FAILED") && retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[TEST RETRY] Reintento test número " + retries + ".");
                    String imagePath = "./resume-report/" + dmahms.substring(0, 10) + "/report"
                            + dmahms.substring(10) + "/" + splitString(testId, "_")[0] + "/" + testId + "/";
                    deleteFiles(imagePath);
                    imageCount = 1;
                    currentTest.setActionExecuted(0);
                    currentTest.setStatus("");
                    if (preconditionList.size() == 0) {
                        currentTestSuite.getTests().removeLast();
                    } else {
                        // currentTest.getEvidences().removeAll(currentTest.getEvidences());
                        preconditionList = new ArrayList<>();
                        maxLista = 0;
                        posicionInversa = 1;

                    }
                    Class<?> clase = Class.forName(className);
                    Object instanciaDeOtraClase = clase.getDeclaredConstructor().newInstance();
                    Method metodo = clase.getMethod(methodName);
                    metodo.invoke(instanciaDeOtraClase);
                    retries++;
                    repeat = true;
                } else if (currentTest.getStatus().equals("FAILED") && retries == MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[TEST RETRY KO] Limite de reintentos " + retries);
                    repeat = false;
                } else {
                    if (preconditionList.size() == 0 && currentTest.getStatus().equals("FAILED")) {
                        currentTestSuite.getTests().removeLast();
                    } else if (preconditionList.size() > 0 && currentTest.getStatus().equals("FAILED")) {
                        // currentTest.getEvidences().removeAll(currentTest.getEvidences());
                        preconditionList = new ArrayList<>();
                        maxLista = 0;
                        posicionInversa = 1;
                    }
                    repeat = false;
                }
            } catch (InvocationTargetException e) {
                Throwable causaReal = e.getCause();
                System.out.println("Excepción original: " + causaReal);
                retries++;
                repeat = true;
            } catch (Exception e) {
                MainUtils.printReportMessage("[TEST RETRY EXCEPTION] No se realizó el reintento del test");
                throw e;
            }
        } while (repeat && retries < MainUtils.maxRetries);
        if (preconditionList.size() > 0) {
            preconditionList = new ArrayList<>();
            maxLista = 0;
            posicionInversa = 1;
        }

    }

    public static void deleteFiles(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        boolean success = f.delete();
                        if (success) {
                            MainUtils.printReportMessage("[TEST RETRY] Archivo borrado " + f.getName());
                        } else {
                            MainUtils.printReportMessage("[TEST RETRY] No se pudo borrar el archivo " + f.getName());
                        }
                    }
                }
            }
        } else {
            MainUtils.printReportMessage("[TEST RETRY] La ruta proporcionada no es una carpeta");
        }
    }

    public static void newStep(int stepNumber, String actionDescription, String conditionDescription) {
        actionDescription = actionDescription
                .replace("\\", "\\\\") // Escapa las barras invertidas
                .replace("\"", "\\\"") // Escapa las comillas dobles
                .replace("\b", "\\b") // Escapa el retroceso
                .replace("\f", "\\f") // Escapa el avance de página
                .replace("\n", "\\n") // Escapa el salto de línea
                .replace("\r", "\\r") // Escapa el retorno de carro
                .replace("\t", "\\t"); // Escapa el tabulador

        conditionDescription = conditionDescription
                .replace("\\", "\\\\") // Escapa las barras invertidas
                .replace("\"", "\\\"") // Escapa las comillas dobles
                .replace("\b", "\\b") // Escapa el retroceso
                .replace("\f", "\\f") // Escapa el avance de página
                .replace("\n", "\\n") // Escapa el salto de línea
                .replace("\r", "\\r") // Escapa el retorno de carro
                .replace("\t", "\\t"); // Escapa el tabulador
        // stepNumber = MainUtils.currentStep;
        MainUtils.currentStep = stepNumber;
        if (!preconditionList.isEmpty()) {
            currentPreconditionSteps = new PreconditionSteps(stepNumber, actionDescription, conditionDescription);
            currentTest.getPreconditionSteps().add(currentPreconditionSteps);
        } else {
            currentSteps = new Steps(stepNumber, actionDescription, conditionDescription);
            currentTest.getSteps().add(currentSteps);
        }

        /*
         * MainUtils.currentSteps.setActionDescription(actionDescription);
         * MainUtils.currentSteps.setConditionDescription(conditionDescription);
         * MainUtils.currentSteps.setStepNumber(stepNumber);
         */

    }

}
