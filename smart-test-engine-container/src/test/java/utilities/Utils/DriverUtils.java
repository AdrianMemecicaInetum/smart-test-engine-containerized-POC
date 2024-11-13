package utilities.Utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DriverUtils {

    public static DesiredCapabilities capabilities;
    public static ChromeDriver webDriver;
    public static URL url;
    public static String apk_path;
    public static String hostname;
    public static IOSDriver<IOSElement> iOSdriver;
    public static AndroidDriver<AndroidElement> androidDriver;
    private static String rowIdentifier;
    private static int contadorObjetoError;
    private static MainUtils mainUtils;
    private static DriverUtils driverUtils_instance = null;

    static TouchAction<?> touchAction;

    // Dos reintentos de restaurar Android
    public static Integer reintentosRestauraAndroid = 3;

    // Clases
    // private static FlujoPrincipalAndroid
    private static MainUtils commonActions;

    // Instanciar clases de test con patrón Singleton
    public static DriverUtils setInstance() {
        if (driverUtils_instance == null) {
            driverUtils_instance = new DriverUtils();
        }
        return driverUtils_instance;
    }

    private DriverUtils() {
        new WebUtilities();
        new APIUtilities();
        new MobileUtilities();
        mainUtils = MainUtils.setInstance();
    }

    // Get clases de recuersos
    public static String getRowIdentifier() {
        return rowIdentifier;
    }

    public static URL getUrl() {
        return url;
    }

    public static WebDriver getDriver() {
        return webDriver;
    }

    public static AndroidDriver<AndroidElement> getDriverMobile() {
        return androidDriver;
    }

    /**
     * Metodos para restaurar el tiempo de espera de la aplicación a 30 segundos
     */
    public static void framework_setup() {
        driverUtils_instance = DriverUtils.setInstance();
        // MobileUtilities.setInstance();
        mainUtils.objectRepositoryReader("ObjectRepository.csv");
        mainUtils.testDataReader("DataRepository.csv");
        // reintentosRestauraAndroid = 3;
        rowIdentifier = "TESTID";
        // Reporte
        MainUtils.printReportMessage("[FRAMEWORK] Instanciador iniciado correctamente");
        contadorObjetoError = 0;
        // MainUtils.PrintReportMessage("SESIÓN CON ID " +
        // androidDriver.getSessionId() + " INICIADA.");

        MainUtils.startTestExecution = System.currentTimeMillis();
    }

    public static void framework_breakup() {
        MainUtils.currentTest.setStepsExecuted(MainUtils.currentStep);
        MainUtils.forceWait(Duration.ofSeconds(5));
        MainUtils.endTestExecution = System.currentTimeMillis();
        MainUtils.currentTestExecution = (MainUtils.endTestExecution - MainUtils.startTestExecution);
        MainUtils.testExecutionTimeCalculator(MainUtils.currentTestExecution);
    }

    /**************************************************
     * Utilidades MOBILE
     **************************************************/

    public void instanciador_iOS() {
        try {
            // Maquina Windows local
            final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
            // Maquina MAC PRE
            // final String URL_STRING = "http://0.0.0.0:4723/wd/hub";
            url = new URL(URL_STRING);

            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            capabilities.setCapability(MobileCapabilityType.APP, "/Users/diegobetabel/Desktop/IntegrationApp.app");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.4");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");

            // objRep = new ObjectRepositoryUtils("ObjectRepository.properties");

            iOSdriver = new IOSDriver<>(capabilities);

            iOSdriver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

            // Allure.addAttachment("Historico", "Instanciador iniciado correctamente")
            // Allure.addAttachment("Historico", "-------------------- - ");
        } catch (Exception e) {
            // Allure.addAttachment("Error", "Error al iniciar el instaciador : " + e);
        }
    }

    public static void instanciador_Android() throws IOException, InterruptedException {
        if (DriverUtils.androidDriver == null) {
            // capabilities
            // Maquina Windows local
            // final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
            // Maquina MAC PRE
            // final String URL_STRING = "http://0.0.0.0:4723/wd/hub";
            MainUtils.testType = "MOBILE";
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            // Configuración del idioma y la región
            capabilities.setCapability("language", "es");
            capabilities.setCapability("locale", "ES");
            /// -> Seleccionar uno de los 2 métodos:
            /// ->1. Inicio de APP instalada en el dispositivo emulado
            /*
             * capabilities.setCapability("appPackage", "com.radmas.iycp.casiopea.pre");
             * capabilities.setCapability("automationName","UiAutomator2");
             * capabilities.setCapability("appActivity",
             * "com.radmas.iycp.presentation.launcher.LauncherActivity"); ///Sentencia
             * Reiniciar(false)/No Reiniciar(true) la APP
             * capabilities.setCapability("noReset", false);
             */

            /// ->2. Inicio para instalar APP desde un directorio
            /// Local
            /*
             * capabilities.setCapability(MobileCapabilityType.APP,
             * "../../../../../apk/casiopeaMobility.apk");
             */
            String hostname = System.getenv("COMPUTERNAME");
            // String hostLuis = "GFI12971";
            // String hostIsmael = "IBLAESALIC05970";
            // String hostDaniel = "IBLAESMADC05892";
            // String hostname = InetAddress.getLocalHost().getHostName();
            // PATH del APK
            String currentDir = System.getProperty("user.dir");
            MainUtils.printReportMessage("[FRAMEWORK] vemos el directorio que coge :" + currentDir);
            String relativeApkPath = Paths.get(currentDir, "apk", "AplicacionEjemplo.apk").normalize().toString();
            System.out.println("current dir: " + currentDir);
            System.out.println("Ruta absoluta del APK: " + relativeApkPath);
            apk_path = relativeApkPath;
            MainUtils.printReportMessage("[FRAMEWORK] vemos hostname que coge :" + hostname);

            capabilities.setCapability(MobileCapabilityType.APP, apk_path);

            // How long (in seconds) Appium will wait for a new command from the client
            // before assuming the client quit and ending the session.
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60 * 60);
            // Don't reset app state before this session.
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            // Perform a complete reset.
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
            // Which automation engine to use. UiAutomator2 for Android and XCUITest for
            // iOS.
            capabilities.setCapability("automationName", "UiAutomator2");
            // Set this capability to true to run the Emulator headless when device display
            // is not needed to be visible.
            capabilities.setCapability("isHeadless", true);
            // Ignores Security exception: Permission denial alert and allows to continue
            // the session creation process.
            capabilities.setCapability("ignoreHiddenApiPolicyError", true);
            // Java package of the Android app you want to run.
            capabilities.setCapability("appPackage", "com.esfe.example");
            // Activity name for the Android activity you want to launch from your package.
            capabilities.setCapability("appActivity", "com.esfe.example.AppEjemplo");
            // Comprobar si hay timeout tras un test, debido a que cierra la segunda
            // ejecución consecutiva (en menos de 60 segundos)
            framework_setup();
            MainUtils.printReportMessage("[FRAMEWORK] antes de entrar en el instanciaAndroid");
            if (hostname == null) {
                restauraFabricaAndroid();
            }
            instanciaAndroid(capabilities);
            MainUtils.printReportMessage("[FRAMEWORK] despues de salir de instanciaAndroid");
            DriverUtils.androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            MainUtils.printReportMessage("[FRAMEWORK] Instanciador iniciado correctamente");
            contadorObjetoError = 0;
            touchAction = new TouchAction<>(DriverUtils.androidDriver);
        }

        MainUtils.startTestExecution = System.currentTimeMillis();

    }

    public static void instanciador_AndroidHibrido() throws IOException, InterruptedException {
        if (DriverUtils.androidDriver == null) {
            MainUtils.testType = "MOBILE";
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
            String hostname = System.getenv("COMPUTERNAME");
            framework_setup();
            MainUtils.printReportMessage("[FRAMEWORK] antes de entrar en el instanciaAndroid");
            if (hostname == null) {
                restauraFabricaAndroid();
            }
            instanciaAndroid(capabilities);
            MainUtils.printReportMessage("[FRAMEWORK] despues de salir de instanciaAndroid");
            DriverUtils.androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            MainUtils.printReportMessage("[FRAMEWORK] Instanciador iniciado correctamente");
            contadorObjetoError = 0;
            touchAction = new TouchAction<>(DriverUtils.androidDriver);
        }
        MainUtils.startTestExecution = System.currentTimeMillis();
    }

    /*
     * Método que cierra la instancia de AndroidDriver
     */
    public static void restauraFabricaAndroid() throws IOException, InterruptedException {
        MainUtils.printReportMessage("[FRAMEWORK] Cerrando posibles emulaciones.");
        if (DriverUtils.androidDriver != null) {
            DriverUtils.androidDriver.quit();
        }
        ProcessBuilder pb1 = new ProcessBuilder();
        pb1.command("sudo", "bash", "Plugins/Mobile/stopAllEmulators.sh");
        pb1.start();
        MainUtils.forceWait(Duration.ofSeconds(5));
        ProcessBuilder pb2 = new ProcessBuilder();
        pb2.command("sudo", "bash", "Plugins/Mobile/stopAllEmulators.sh");
        pb2.start();
        MainUtils.printReportMessage("[FRAMEWORK] Esperando al cierre de emulaciones.");
        MainUtils.forceWait(Duration.ofSeconds(25));
        MainUtils.printReportMessage("[FRAMEWORK] Iniciando emulación.");
        ProcessBuilder pba = new ProcessBuilder();
        pba.command("sudo", "bash", "Plugins/Mobile/startAllEmulators.sh");
        pba.start();
        MainUtils.forceWait(Duration.ofSeconds(20));
        MainUtils.printReportMessage("[FRAMEWORK] Emulación disponible.");
    }

    public static void instanciaAndroid(DesiredCapabilities capabilities) throws IOException, InterruptedException {
        reintentosRestauraAndroid--;
        do {
            try {
                DriverUtils.androidDriver = new AndroidDriver<>(capabilities);
                reintentosRestauraAndroid = 0;
            } catch (Exception e) {
                if (e.getMessage().contains("Unable to force stop app")) {
                    restauraFabricaAndroid();
                    instanciaAndroid(capabilities);
                } else {
                    throw e;
                }
            }
        } while (reintentosRestauraAndroid > 0);
    }

    /*
     * Método para cerrar la aplicación
     */
    public static void restauraAndroid() {
        MainUtils.currentTest.setStepsExecuted(MainUtils.currentStep);
        MainUtils.forceWait(Duration.ofSeconds(5));
        MainUtils.endTestExecution = System.currentTimeMillis();
        MainUtils.currentTestExecution = (MainUtils.endTestExecution - MainUtils.startTestExecution);
        MainUtils.testExecutionTimeCalculator(MainUtils.currentTestExecution);
        androidDriver.resetApp();
        MainUtils.forceWait(Duration.ofSeconds(10));
    }

    public static void closeMobile() {
        framework_breakup();
        androidDriver.resetApp();
        MainUtils.forceWait(Duration.ofSeconds(3));
    }

    /**
     * Metodos para restaurar el tiempo de espera de la aplicación a 30 segundos
     */
    public static void restaurarTiempoDeEsperaMOBILE() {
        androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * Metodo para cambiar el tiempo de espera de la aplicación
     */
    public static void cambiarTiempoDeEsperaMOBILE(int time) {
        androidDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /**
     * Devuelve un objeto identificado en el repositorio de objetos.
     * 
     * @param identificador String
     * @return AndroidElement
     */
    public static AndroidElement searchAndroidElement(String identificador) {
        try {
            AndroidElement valor;
            switch (mainUtils.typeObjectID(identificador)) {
                case "XPATH":
                    valor = androidDriver.findElementByXPath(mainUtils.objectID(identificador));
                    break;
                case "ID":
                    valor = androidDriver.findElementById(mainUtils.objectID(identificador));
                    break;
                case "A_ID":
                    valor = androidDriver.findElementByAccessibilityId(mainUtils.objectID(identificador));
                    break;
                default:
                    valor = null;
            }
            return valor;
        } catch (Exception generalException) {
            MainUtils.screenShotRecord("[FRAMEWORK ERROR] NoEncuentraObjetoError" + contadorObjetoError,
                    "No se encontró el objeto con identificador -> " + identificador + " Captura -> "
                            + "NoEncuentraObjetoError" + contadorObjetoError);
            contadorObjetoError++;
            throw generalException;
        }
    }

    /**
     * Devuelve una lista de objetos identificados en el repositorio de objetos.
     * 
     * @param identificador String
     * @return List<AndroidElement>
     */
    public static List<AndroidElement> searchAndroidElements(String identificador) {
        try {
            List<AndroidElement> valor;

            switch (mainUtils.typeObjectID(identificador)) {
                case "XPATH":
                    valor = androidDriver.findElementsByXPath(mainUtils.objectID(identificador));
                    break;
                case "ID":
                    valor = androidDriver.findElementsById(mainUtils.objectID(identificador));
                    break;
                case "A_ID":
                    valor = androidDriver.findElementsByAccessibilityId(mainUtils.objectID(identificador));
                    break;
                default:
                    valor = null;
            }

            return valor;
        } catch (Exception generalException) {
            MainUtils.screenShotRecord("[FRAMEWORK ERROR] NoEncuentraObjetoError" + contadorObjetoError,
                    "No se encontró la lista de objetos con identificador -> " + identificador + " Captura -> "
                            + "NoEncuentraObjetoError" + contadorObjetoError);
            contadorObjetoError++;
            throw generalException;
        }
    }

    /**************************************************
     * Utilidades WEB
     **************************************************/

    public static void instanciador_Chrome() throws MalformedURLException {
        MainUtils.testType = "WEB";
        framework_setup();

        // zvqpreg1
        String hostname = System.getenv("COMPUTERNAME");
        String hostServer1 = "zvqpreg1";
        String hostServer2 = "ZVQPREG1";
        // String hostname = InetAddress.getLocalHost().getHostName();
        // PATH del APK
        String chromeDriverPath;
        String os = System.getProperty("os.name").toLowerCase();
        
        if (hostname != null && (hostname.equals(hostServer1) || hostname.equals(hostServer2))) {
            // Se usa un chromedriver especifico para el servidor remoto
            chromeDriverPath = "src/test/resources/webFiles/chromedriverServerQP.exe";
        } else if (os.contains("win")) {
            // Se usa windows si se detecta el OS como windows
            chromeDriverPath = "src/test/resources/webFiles/chromedriver.exe";
        } else {
            // Se usa linux si no se detecta el OS como windows
            chromeDriverPath = "src/test/resources/webFiles/chromedriver";
        }
        
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        MainUtils.printReportMessage("[FRAMEWORK] ChromeDriver ruta establecida a: " + chromeDriverPath);

        // DesiredCapabilities cap = DesiredCapabilities.chrome();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments("--no-default-browser-check");
        chromeOptions.addArguments("--disable-default-apps");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-popup-blocking");
        if(!os.contains("win")) {
            chromeOptions.addArguments("--headless");  // Para contenedores
            chromeOptions.addArguments("--no-sandbox");  // Recomendado para contenedores
            chromeOptions.addArguments("--disable-dev-shm-usage");  // Recomendado para contenedores
            chromeOptions.addArguments("--remote-debugging-port=9222");  // Estabiliza el modo headless
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.setBinary("/opt/chrome/chrome"); //La ruta del binario de chrome en un contenedor
            chromeOptions.addArguments("--window-size=1920,1080"); // Para el tamaño de la ventana virtual
        }
        
        webDriver = new ChromeDriver(chromeOptions);

        // Comprobar si hay timeout tras un test, debido a que cierra la segunda
        // ejecución consecutiva (en menos de 60 segundos)
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // webDriver.manage().window().setSize(new Dimension(1920, 1080));
        webDriver.manage().window().maximize();
    }

    public static DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public static void closeBrowser() {
        framework_breakup();
        MainUtils.printReportMessage("[FRAMEWORK] Cerrando webDriver");
        webDriver.close();
    }

    /**
     * Metodos para cambiar el tiempo de espera de la aplicación
     */
    public static void restaurarTiempoDeEsperaWEB() {
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public static void cambiarTiempoDeEsperaWEB(int time) {
        webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /**
     * Devuelve un objeto identificado en el repositorio de objetos.
     * 
     * @param identificador String
     * @return AndroidElement
     */
    public static WebElement searchWebElement(String identificador) {
        try {
            WebElement valor;
            switch (mainUtils.typeObjectID(identificador)) {
                case "XPATH":
                    valor = webDriver.findElementByXPath(mainUtils.objectID(identificador));
                    break;
                case "ID":
                    valor = webDriver.findElementById(mainUtils.objectID(identificador));
                    break;
                default:
                    valor = null;
            }
            return valor;
        } catch (Exception generalException) {
            MainUtils.failedActionOrImportantCondition("[FRAMEWORK ERROR] NoEncuentraObjetoError" + contadorObjetoError,
                    "No se encontró el objeto con identificador -> " + identificador + " Captura -> "
                            + "NoEncuentraObjetoError" + contadorObjetoError);
            contadorObjetoError++;
            throw generalException;
        }
    }

    /**
     * Devuelve una lista de objetos identificados en el repositorio de objetos.
     * 
     * @param identificador String
     * @return List<AndroidElement>
     */
    public static List<WebElement> searchWebElements(String identificador) {
        try {
            List<WebElement> valor;
            switch (mainUtils.typeObjectID(identificador)) {
                case "XPATH":
                    valor = webDriver.findElementsByXPath(mainUtils.objectID(identificador));
                    break;
                case "ID":
                    valor = webDriver.findElementsById(mainUtils.objectID(identificador));
                    break;
                default:
                    valor = null;
            }
            return valor;
        } catch (Exception generalException) {
            MainUtils.failedActionOrImportantCondition("[FRAMEWORK ERROR] NoEncuentraObjetoError" + contadorObjetoError,
                    "No se encontró la lista de objetos con identificador -> " + identificador + " Captura -> "
                            + "NoEncuentraObjetoError" + contadorObjetoError);
            contadorObjetoError++;
            throw generalException;
        }
    }

    // Get clases Actions/conditios
    public static MainUtils getCommonActions() {
        return commonActions;
    }

    public static IOSDriver<IOSElement> getiOSdriver() {
        return iOSdriver;
    }

    public static AndroidDriver<AndroidElement> getAndroidDriver() {
        return androidDriver;
    }

    /**
     * Metodo para hacer scroll hacia abajo
     * 
     * @param identificador int
     */
    public static void scrollDown(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0, " + pixels + ");");
    }

    /**
     * Devuelve un objeto identificado mediante XPATH.
     *
     * @param identificador String
     * @return WebElement
     */
    public static WebElement searchWebElementByXPATH(String identificador) {
        try {
            WebElement valor;
            if (identificador.length() > 0) {
                valor = webDriver.findElementByXPath(identificador);
                return valor;
            } else {
                MainUtils.printReportMessage("[FRAMEWORK ERROR] No se ha proporcionado XPATH");
                return null;
            }
        } catch (Exception generalException) {
            MainUtils.failedActionOrImportantCondition("[FRAMEWORK ERROR] NoEncuentraObjetoError.png",
                    "No se encontró el objeto con identificador -> " + identificador + " Captura -> "
                            + "NoEncuentraObjetoError" + ".png");
            throw generalException;
        }
    }

    /**************************************************
     * Utilidades API
     **************************************************/

    public static void instanciador_api() throws MalformedURLException {
        MainUtils.testType = "API";
        framework_setup();

        // zvqpreg1
        String hostname = System.getenv("COMPUTERNAME");
        String hostServer1 = "zvqpreg1";
        String hostServer2 = "ZVQPREG1";
        // String hostname = InetAddress.getLocalHost().getHostName();
        // PATH del APK
        if (hostname == null) {
            MainUtils.printReportMessage("[FRAMEWORK] Detectada ejecución en PC local -> " + hostname);
            // System.setProperty("webdriver.chrome.driver",
            // "src/test/resources/webFiles/chromedriver.exe");
        } else if (hostname.equals(hostServer1) || hostname.equals(hostServer2)) {
            MainUtils.printReportMessage("[FRAMEWORK] Detectada ejecución en Servidor Remoto -> " + hostname);
            // System.setProperty("webdriver.chrome.driver",
            // "src/test/resources/webFiles/chromedriverServerQP.exe");
        } else {
            MainUtils.printReportMessage("[FRAMEWORK] Detectada ejecución en PC local -> " + hostname);
            // System.setProperty("webdriver.chrome.driver",
            // "src/test/resources/webFiles/chromedriver.exe");
        }

        // // DesiredCapabilities cap = DesiredCapabilities.chrome();
        // ChromeOptions chromeOptions = new ChromeOptions();
        // chromeOptions.addArguments("start-maximized");
        // chromeOptions.addArguments("--log-level=1");
        // chromeOptions.setAcceptInsecureCerts(true);
        // // chromeOptions.merge(cap);
        // webDriver = new ChromeDriver(chromeOptions);

        // // Comprobar si hay timeout tras un test, debido a que cierra la segunda
        // // ejecución consecutiva (en menos de 60 segundos)
        // webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

}