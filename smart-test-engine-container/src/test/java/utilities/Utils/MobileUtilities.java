package utilities.Utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.google.common.collect.ImmutableMap;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static utilities.Utils.DriverUtils.androidDriver;
import static utilities.Utils.DriverUtils.touchAction;

public class MobileUtilities {

    public int[] enterKeyCoor;
    private static MainUtils mainUtils;
    private static MobileUtilities mobileUtils_instance = null;

    public static MobileUtilities setInstance() {
        if (mobileUtils_instance == null) {
            mobileUtils_instance = new MobileUtilities();
        }
        return mobileUtils_instance;
    }

    public MobileUtilities() {
        mainUtils = MainUtils.setInstance();
        enterKeyCoor = new int[] { 644, 1374 };

    }

    /**************************************************
     *
     * ACCIONES MOBILE
     *
     **************************************************/

    /***
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para hacer tap en un elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void mobileTap(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    DriverUtils.searchAndroidElement(objeto).click(); // Objeto es la ruta del xpath/id/A_ID del objeto
                                                                      // a clickar
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento tap número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("tap" + MainUtils.contadorTap,
                            "[ACTION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + objeto + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    public static void mobilePressNativeKey(AndroidKey aKey) {
        MainUtils.forceWait(Duration.ofMillis(500));
        DriverUtils.androidDriver.pressKey(new KeyEvent(aKey));
    }

    /***
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para hacer tap largo en un elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    @SuppressWarnings("rawtypes")
    public static void mobileLongtap(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    PointOption pointFrom = (new PointOption()).withCoordinates(
                            DriverUtils.searchAndroidElement(objeto).getLocation().getX(),
                            DriverUtils.searchAndroidElement(objeto).getLocation().getY());
                    touchAction.longPress(pointFrom).perform();
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se pulsó (longtap) el objeto -> " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento longtap número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("longtap" + MainUtils.contadorLongTap,
                            "[ACTION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorLongTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("longtapException" + MainUtils.contadorLongTap,
                    "[ACTION EXCEPTION] longtap.");
            MainUtils.contadorLongTap++;
            throw e;
        }
    }

    /***
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para hacer tap largo en un elemento del repositorio de objetos durante
     * un tiempo determinado
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    @SuppressWarnings("rawtypes")
    public static void mobileLongtaptime(String objeto, int time) {
        Duration duration = Duration.ofSeconds(time);
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    PointOption pointFrom = (new PointOption()).withCoordinates(
                            DriverUtils.searchAndroidElement(objeto).getLocation().getX(),
                            DriverUtils.searchAndroidElement(objeto).getLocation().getY());
                    touchAction.longPress(pointFrom).waitAction(WaitOptions.waitOptions(duration)).release().perform();
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se pulsó (longtap) el objeto -> " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento longtap número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("longtap" + MainUtils.contadorLongTap,
                            "[ACTION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorLongTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("longtapException" + MainUtils.contadorLongTap,
                    "[ACTION EXCEPTION] longtap.");
            MainUtils.contadorLongTap++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para hacer tap en las coordenadas indicadas por parámetro
     *
     * @param x
     * @param y
     */
    @SuppressWarnings("rawtypes")
    public static void mobileTapCoordinates(int x, int y) {
        try {
            MainUtils.forceWait(Duration.ofSeconds(1));
            touchAction.tap(new PointOption().withCoordinates(x, y)).perform();
            MainUtils.highlightElement("Coordenadas x:" + x + " y:" + y, "Accion");
            MainUtils.passedAction("[ACTION OK] Se pulsó en las coordenadas x=" + x + " e y=" + y);
            MainUtils.forceWait(Duration.ofSeconds(1));
        } catch (Exception e) {
            MainUtils.screenShotRecord("tapCoordenadasException" + MainUtils.contadorTapCoordenadas,
                    "[ACTION EXCEPTION] Error al hacer TAP en las coordenadas -> (" + x + " , " + y + ")");
            MainUtils.contadorTapCoordenadas++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para enviar un texto bruto a un objeto del repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param texto  Texto en bruto.
     */
    public static void mobileSendkeys(String objeto, String texto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    DriverUtils.searchAndroidElement(objeto).sendKeys(texto);
                    androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se introdujo " + texto + " en " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY]Reintento sendkeys número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("sendkeys" + MainUtils.contadorSendkeys,
                            "[ACTION KO] El objeto no existe (" + objeto + ").");
                    MainUtils.contadorSendkeys++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("sendkeysException" + MainUtils.contadorSendkeys,
                    "[ACTION EXCEPTION] No se introdujo " + texto + " en " + objeto);
            MainUtils.contadorSendkeys++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Presionar DONE en el teclado, el botón verde con el tick.
     */
    public static void mobileKeyboardSendDone() {
        androidDriver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Done"));
        MainUtils.printReportMessage("[TECLADO] Pulsar Botón Done");
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para enviar un texto del repositorio de datos a un objeto del
     * repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param texto  Nombre de la variables del texto del repositorio de datos.
     * @param testId ID de la prueba.
     * @throws Exception
     */
    public static void mobileSendkeysRepository(String objeto, String texto) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (MainUtils.obtainJSONData(texto) != null) {
                        DriverUtils.searchAndroidElement(objeto)
                                .sendKeys(MainUtils.obtainJSONData(texto));
                        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                        MainUtils.passedAction(
                                "[ACTION OK] Se introdujo " + MainUtils.obtainJSONData(texto) + " en "
                                        + objeto);
                    } else {
                        MainUtils.printReportMessage("[ACTION KO] No existe el texto en el repositorio de datos.");
                        MainUtils.contadorSendkeysRepository++;
                        throw new SkipException("[ACTION STOP] No existe el texto en el repositorio de datos");
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento sendkeysRepository número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "sendkeysRepository" + MainUtils.contadorSendkeysRepository,
                            "[ACTION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorSendkeysRepository++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "sendkeysRepositoryException" + MainUtils.contadorSendkeysRepository,
                    "[ACTION EXCEPTION] NO se introdujo (" + MainUtils.obtainJSONData(texto) + ") en ("
                            + objeto + ")");
            MainUtils.contadorSendkeysRepository++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para borrar los valores de un objeto.
     */
    public static void mobileClear(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    DriverUtils.searchAndroidElement(objeto).clear();
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se limpió el objeto -> " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento clear número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("clear" + MainUtils.contadorClear,
                            "[ACTION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorClear++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("clear" + MainUtils.contadorClear,
                    "[ACTION EXCEPTION] clear.");
            MainUtils.contadorClear++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para desplazar la pantalla.
     * 
     * @param xFrom     Coordenada x de inicio
     * @param yFrom     Coordenada y de inicio
     * @param xTo       Coordenada x de destino
     * @param yTo       Coordenada y de destino
     * @param time      Duración del movimiento entre coordenadas
     * @param timeafter Tiempo de espera después del swipe.
     * 
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void mobileSwipe(int xFrom, int yFrom, int xTo, int yTo, int time, int timeafter) {
        MainUtils.forceWait(Duration.ofSeconds(1));
        try {
            PointOption pointFrom = (new PointOption()).withCoordinates(xFrom, yFrom);
            PointOption pointTo = (new PointOption()).withCoordinates(xTo, yTo);
            if (time == 0) {
                touchAction.press(pointFrom).moveTo(pointTo).release().perform();
            } else {
                touchAction.press(pointFrom).waitAction(WaitOptions.waitOptions(Duration.ofMillis(time)))
                        .moveTo(pointTo).release().perform();
            }
            MainUtils.printReportMessage("[ACTION OK] Se hizo swipe con éxito ");
            MainUtils.forceWait(Duration.ofMillis(timeafter));

        } catch (Exception e) {
            MainUtils.failedCondition("swipeException" + MainUtils.contadorIsObjectDisplayed,
                    "[ACTION EXCEPTION] swipe.");
            MainUtils.contadorSwipe++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para desplazar la pantalla.
     * 
     * @param xFrom     Coordenada x de inicio
     * @param yFrom     Coordenada y de inicio
     * @param xTo       Coordenada x de destino
     * @param yTo       Coordenada y de destino
     * @param time      Duración del movimiento entre coordenadas
     * @param timeafter Tiempo de espera antes del swipe.
     * 
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void mobileSwipe(int xFrom, int yFrom, int xTo, int yTo, int time) {
        mobileSwipe(xFrom, yFrom, xTo, yTo, time, 2000);
    }

    public static void mobileSwipeToElementWithText2(String objeto, String textvalue, String direction, int fuerza) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                MainUtils.forceWait(Duration.ofMillis(1200));

                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    MainUtils.forceWait(Duration.ofMillis(1200));
                    if (DriverUtils.searchAndroidElement(objeto).getAttribute("text").equals(textvalue)) {
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
                        MainUtils.passedAction("[ACTION OK] Se encontró el texto (" + textvalue + ") al hacer SwipeTo");
                        repeat = false;
                        break;
                    } else {
                        MainUtils.printReportMessage("[SWIPE KO] No coincide el texto (" + textvalue + ")");
                        mobileSwipeScreenSmall(direction, fuerza);
                        MainUtils.forceWait(Duration.ofMillis(800));
                    }
                }

                if (retries < 50) {
                    MainUtils.printReportMessage(
                            "[SWIPE RETRY] Reintento swipeToElementWithText número " + retries + ".");
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    retries++;
                    mobileSwipeScreenSmall(direction, fuerza);
                    // CommonUtilities.screenShotRecord("swipeToElementWithText" +
                    // contadorIsObjectDisplayed ,
                    // "[SWIPE KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsObjectDisplayed++;
                } else {
                    MainUtils.printReportMessage("[SWIPE KO] Superado número max. de reintentos (" + retries + ")");
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();

        } catch (Exception e) {
            MainUtils.forceWait(Duration.ofSeconds(1));
            MainUtils.failedActionOrImportantCondition(
                    "isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[SWIPE EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * Performs small swipe from the center of screen
     *
     * @param dir the direction of swipe
     * @version java-client: 7.3.0
     **/
    @SuppressWarnings("rawtypes")
    public static void mobileSwipeScreenSmall(String dir, int mult) {

        MainUtils.forceWait(Duration.ofMillis(200));
        MainUtils.printReportMessage("[SWIPE INFO] Moviendo pantalla: " + dir); // always log your actions

        // Animation default time:
        // - Android: 300 ms
        // - iOS: 200 ms
        // final value depends on your app and could be greater

        final int ANIMATION_TIME = 300; // ms Luis: originalmente 200
        final int PRESS_TIME = 300; // ms Luis: originalmente 200

        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = DriverUtils.androidDriver.manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

        // reduce swipe move into multiplier times comparing to swipeScreen move
        // int mult = 3; // multiplier Luis: original 10
        MainUtils.forceWait(Duration.ofMillis(100));
        //
        switch (dir) {
            case "DOWN": // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, (dims.height / 2) + (dims.height / 2) / mult);
                break;
            case "UP": // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, (dims.height / 2) - (dims.height / 2) / mult);
                break;
            case "LEFT": // center of left side
                pointOptionEnd = PointOption.point((dims.width / 2) - (dims.width / 2) / mult, dims.height / 2);
                break;
            case "RIGHT": // center of right side
                pointOptionEnd = PointOption.point((dims.width / 2) + (dims.width / 2) / mult, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreenSmall(): dir: '" + dir.toString() + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            new TouchAction(DriverUtils.androidDriver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            MainUtils.failedCondition("swipeException" + MainUtils.contadorIsObjectDisplayed,
                    "[ACTION EXCEPTION] swipe.");
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            MainUtils.failedCondition("swipeException" + MainUtils.contadorIsObjectDisplayed,
                    "[ACTION EXCEPTION] swipe.");
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * aparece en la aplicación.
     *
     * @param objeto Donde lo espero?
     */
    public static void mobileIsObjectDisplayed(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] Se encontró el objeto (" + objeto + ")");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectDisplayed número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsObjectDisplayed++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedCondition("isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * Método para verificar si un elemento está marcado (checked) o no.
     *
     * @param objeto ID del objeto en el repositorio de objetos
     * @return true si el elemento está marcado, false si no lo está o no se
     *         encuentra el elemento
     */
    public static boolean isElementChecked(String objeto) {
        try {
            int retries = 0;
            boolean repeat = true;
            boolean isChecked = false;
            do {
                AndroidElement element = DriverUtils.searchAndroidElement(objeto);
                if (element != null) {
                    isChecked = Boolean.parseBoolean(element.getAttribute("checked"));
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    MainUtils.printReportMessage(
                            "[CHECK STATUS] El estado de 'Checked' del objeto " + objeto + " es: " + isChecked);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[CONDITION RETRY] Reintento de verificación número " + retries + ".");
                    retries++;
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("checkStatus" + MainUtils.contadorCheckStatus,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorTap++;
                    repeat = false;
                    throw new SkipException("[CONDITION STOP] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
            return isChecked;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("checkException" + MainUtils.contadorCheckStatus,
                    "[CONDITION EXCEPTION] Error al verificar el estado del objeto (" + objeto + ")");
            MainUtils.contadorCheckStatus++;
            throw e;
        }
    }

    /**
     * Comprueba si un objeto dado se muestra en la pantalla.
     * 
     * @param objeto El nombre del objeto que se va a buscar.
     * @throws Exception Si se produce un error durante la búsqueda del objeto.
     */
    public static void mobileIsObjectDisplayedMandatory(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] Se encontró el objeto (" + objeto + ")");
                    repeat = false;
                    Assert.assertTrue(true, "[CONDITION OK] Se encontró el objeto (" + objeto + ")");
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectDisplayed número " + retries + ".");
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    retries++;
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsObjectDisplayed++;
                    repeat = false;
                    Assert.fail("[CONDITION KO] El objeto no existe (" + objeto + ")");
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * Comprueba si una lista de objetos se muestra en la pantalla.
     * 
     * @param objetos Los nombres de los objetos que se van a buscar.
     * @throws Exception Si se produce un error durante la búsqueda de alguno de los
     *                   objetos.
     */
    public static void mobileListOfIsObjectDisplayed(String... objetos) {
        for (String obj : objetos) {
            mobileIsObjectDisplayed(obj);
        }
    }

    public static void mobileIsObjectNotDisplayed(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() <= 0) {
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] El objeto no existe (" + objeto + ")");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectNotDisplayed número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition(
                            "isObjectNotDisplayed" + MainUtils.contadorIsObjectNotDisplayed,
                            "[CONDITION KO] El objeto existe (" + objeto + ")");
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedCondition("isObjectNotDisplayedException" + MainUtils.contadorIsObjectNotDisplayed,
                    "[CONDITION EXCEPTION] isObjectNotDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectNotDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto en bruto.
     *
     * @param objeto        Identificador del objeto del repositorio de objetos.
     * @param textoAValidar Identificador del dato del repositorio de datos.
     */
    public static void mobileIsTextInObject(String objeto, String textoAValidar) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (DriverUtils.searchAndroidElement(objeto).getText().equals(textoAValidar)) {
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                        MainUtils.passedCondition(
                                "[CONDITION OK] Texto correcto encontrado (" + textoAValidar + ") en el objeto "
                                        + objeto);
                    } else {
                        MainUtils.failedCondition("isTextInObject" + MainUtils.contadorIsTextInObject,
                                "[CONDITION KO] El objeto no existe (" + objeto + "). No se encuentra el texto "
                                        + textoAValidar + " en el objeto " + objeto);
                        MainUtils.contadorIsTextInObject++;
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isTextInObject número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("isTextInObject" + MainUtils.contadorIsTextInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsTextInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedCondition("isTextInObjectException" + MainUtils.contadorIsTextInObject,
                    "[CONDITION EXCEPTION] isTextInObject. No se encuentra el texto " + textoAValidar + " en el objeto "
                            + objeto);
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos no
     * contiene un texto dado
     *
     * @param objeto        Identificador del objeto del repositorio de objetos.
     * @param textoAValidar Identificador del dato del repositorio de datos.
     */
    public static void mobileIsTextNotInObject(String objeto, String textoAValidar) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (!DriverUtils.searchAndroidElement(objeto).getText().equals(textoAValidar)) {
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                        MainUtils.passedCondition(
                                "[CONDITION OK] El texto (" + textoAValidar
                                        + ") no coincide con el atributo Text del objeto ("
                                        + objeto + ")");
                    } else {
                        MainUtils.failedCondition("isTextInObject" + MainUtils.contadorIsTextInObject,
                                "[CONDITION KO] El texto coincide. El texto " + textoAValidar
                                        + " se ha encontrado en el objeto " + objeto);
                        MainUtils.contadorIsTextInObject++;
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isTextInObject número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("isTextInObject" + MainUtils.contadorIsTextInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsTextInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedCondition("isTextInObjectException" + MainUtils.contadorIsTextInObject,
                    "[CONDITION EXCEPTION] isTextInObject." + objeto);
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * Comprueba si un objeto dado contiene un texto específico y bloquea la
     * ejecución hasta que se cumpla la condición.
     * 
     * @param objeto        El nombre del objeto que se va a buscar.
     * @param textoAValidar El texto que se va a buscar en el objeto.
     * @throws Exception Si se produce un error durante la búsqueda del objeto o la
     *                   validación del texto.
     */
    public static void mobileIsTextInObjectMandatory(String objeto, String textoAValidar) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (DriverUtils.searchAndroidElement(objeto).getAttribute("text").equals(textoAValidar)) {
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                        MainUtils.passedCondition(
                                "[CONDITION OK] Texto correcto mandatory encontrado (" + textoAValidar + ")");
                    } else {
                        MainUtils.failedCondition(
                                "isTextInObjectMandatory" + MainUtils.contadorIsTextInObject,
                                "[CONDITION KO] El objeto (" + objeto + ") no existe con el texto buscado ("
                                        + textoAValidar + ")");
                        MainUtils.contadorIsTextInObject++;
                        throw new SkipException("[ACTION STOP] El texto mandatory no coincide.");
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isTextInObjectMandatory número " + retries + ".");
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    retries++;
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "isTextInObjectMandatory" + MainUtils.contadorIsTextInObject,
                            "[CONDITION KO] El objeto mandatory no existe (" + objeto + ")");
                    MainUtils.contadorIsTextInObject++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto mandatory no existe (" + objeto + ")");
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isTextInObjectException" + MainUtils.contadorIsTextInObject,
                    "[ACTION STOP] El texto no coincide.");
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto    Identificador del objeto del repositorio de objetos.
     * @param textoData Identificador del dato del repositorio de datos.
     * @param testId    Identificador de la prueba.
     * @throws Exception
     */
    public static void mobileIsDataInObject(String objeto, String textoData) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (MainUtils.obtainJSONData(textoData) != null) {
                        if (DriverUtils.searchAndroidElement(objeto).getText()
                                .equals(MainUtils.obtainJSONData(textoData))) {
                            MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                            MainUtils.passedCondition(
                                    "[CONDITION OK] Texto correcto encontrado (" + textoData + ")");
                        } else {
                            MainUtils.failedCondition("isDataInObject" + MainUtils.contadorIsDataInObject,
                                    "[CONDITION KO] El texto no corresponde (" + objeto + ")");
                            MainUtils.contadorIsDataInObject++;
                        }
                    } else {
                        MainUtils
                                .printReportMessage("[CONDITION KO] No existe el texto en el repositorio de datos");
                        MainUtils.contadorIsDataInObject++;
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isDataInObject número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("isDataInObject" + MainUtils.contadorIsDataInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsDataInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();
        } catch (Exception e) {
            MainUtils.failedCondition("isDataInObjectException" + MainUtils.contadorIsDataInObject,
                    "[CONDITION EXCEPTION] isDataInObject");
            MainUtils.contadorIsDataInObject++;
            throw e;
        }
    }

    /**
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto   Identificador del objeto del repositorio de objetos.
     * @param atributo Nombre del atributo.
     * @param valor    Valor del atributo.
     */
    public static void mobileIsAttributeInObject(String objeto, String atributo, String valor) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaMOBILE(1);
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    if (DriverUtils.searchAndroidElement(objeto).getAttribute(atributo).equals(valor)) {
                        MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                        MainUtils.passedCondition("[CONDITION OK] Se encontró '" + valor + "' en el atributo '"
                                + atributo + "' del objeto '" + objeto + "'");
                    } else {
                        MainUtils.failedCondition(
                                "isAttributeInObject" + MainUtils.contadorIsAttributeInObject,
                                "[CONDITION KO] No se encuentra el atributo '" + atributo + "' con valor '"
                                        + valor + "' en el objeto '" + objeto
                                        + "'");
                        MainUtils.contadorIsAttributeInObject++;
                    }
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isAttributeInObject número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition(
                            "isAttributeInObject" + MainUtils.contadorIsAttributeInObject,
                            "[CONDITION KO]  El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsAttributeInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaMOBILE();

        } catch (Exception e) {
            MainUtils.failedCondition("isAttributeInObjectException" + MainUtils.contadorIsAttributeInObject,
                    "[CONDITION EXCEPTION] No se encuentra el atributo '" + atributo + "' con valor '" + valor
                            + "' en el objeto '" + objeto + "'. Evidencia generada: isAttributeInObjectException"
                            + MainUtils.contadorIsAttributeInObject);
            MainUtils.contadorIsAttributeInObject++;
            throw e;
        }
    }

    /*
     * METODOS ESPECÍFICOS
     */

    /**
     * <p>
     * Método para esconder el teclado.
     * </p>
     */
    public static void hideKeyboard() {
        MainUtils.printReportMessage("[INFO] El teclado está -> " + androidDriver.isKeyboardShown());
        DriverUtils.getAndroidDriver().hideKeyboard();
        MainUtils.printReportMessage("[INFO] Ocultando el teclado");
        MainUtils.forceWait(Duration.ofSeconds(3));
    }

    /**
     * <p>
     * Método para hacer tap en el botón de enviar del teclado
     * </p>
     */
    public static void tapSendButton() {
        MainUtils.forceWait(Duration.ofSeconds(1));
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                // if (Utils.GetAndroidDriver().isKeyboardShown()) {
                // if (DriverUtils.searchAndroidElements("home.buscar").size() > 0) {
                if (checkkeyboard()) {
                    mobileTapCoordinates(1312, 2259);
                    MainUtils.highlightElement("Boton Enviar", "Accion");
                    MainUtils.passedAction("[ACTION OK] Se pulsó el boton de enviar del teclado");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    mobileTap("home.buscar");
                    MainUtils.printReportMessage(
                            "[ACTION RETRY] Reintento tap botón de enviar del teclado número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    MainUtils.forceWait(Duration.ofSeconds(1));
                } else {
                    MainUtils.failedActionOrImportantCondition("tap" + MainUtils.contadorTap,
                            "[ACTION STOP] El botón de enviar del teclado no existe");
                    MainUtils.contadorTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El botón de enviar del teclado no existe");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el botón de enviar del teclado");
            MainUtils.contadorTap++;
            throw e;
        }
        MainUtils.forceWait(Duration.ofSeconds(10));
    }

    /**
     * <p>
     * Método que devuelve la hora actual formateada sin minutos y segundos
     * </p>
     */
    public static String currentTime() {
        // Obtener la hora actual
        LocalTime currentTime = LocalTime.now();
        // Redondear la hora hacia abajo eliminando los minutos y segundos
        LocalTime roundedDownTime = currentTime.withMinute(0).withSecond(0);
        // Formatear la hora redondeada hacia abajo a un String en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String roundedDownTimeString = roundedDownTime.format(formatter);

        return roundedDownTimeString;
    }

    /**
     * <p>
     * Método que espera a que aparezca un elemento
     * </p>
     */
    public static void mobileWaitElement(String objeto, int maxRetries) {
        MainUtils.forceWait(Duration.ofSeconds(1));
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchAndroidElements(objeto).size() > 0) {
                    MainUtils.highlightElement(MainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedAction("[ACTION OK] Objeto encontrado WaitElement -> " + objeto);
                    MainUtils.printReportMessage("[ACTION OK] Objeto encontrado WaitElement -> " + objeto);
                    repeat = false;
                } else if (retries < maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Esperando " + maxRetries
                            + " reintentos al objeto WaitElement -> " + objeto);
                    MainUtils.printReportMessage("[ACTION RETRY] Reintentando buscar objeto. Nº " + retries + ".");
                    MainUtils.forceWait(Duration.ofSeconds(1));
                    retries++;
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                } else {
                    MainUtils.failedActionOrImportantCondition("WaitElement " + MainUtils.contadorWaitElement,
                            "[ACTION STOP] El objeto no aparece.");
                    MainUtils.contadorWaitElement++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no aparece.");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("WaitElementException" + MainUtils.contadorWaitElement,
                    "[ACTION EXCEPTION] Se ha producido un fallo al buscar el objeto.");
            MainUtils.contadorWaitElement++;
            throw e;
        }
    }

    /*
     * <p>
     * Método que busca un objeto cuando no se puede registrar en el repositorio de
     * Objetos
     * </p>
     */
    public static String mobileSearchObjects(String objetoPadre, String texto, String objetoHijo) {
        String HijoEncontrado = "";
        try {
            for (int i = 10; i > 0; i--) {
                if (DriverUtils.searchAndroidElements(objetoPadre + i).size() > 0) {
                    if (DriverUtils.searchAndroidElement(objetoPadre + i).getText().equals(texto)) {
                        MainUtils.highlightElement(MainUtils.objectID(objetoPadre + i), "Accion");
                        MainUtils.passedAction(
                                "[ACTION OK] Objeto encontrado SearchObjectsClick -> " + objetoPadre + i
                                        + " con el texto: " + texto);
                        if (DriverUtils.searchAndroidElements(objetoHijo + i).size() > 0) {
                            HijoEncontrado = objetoHijo + i;
                        } else {
                            MainUtils.failedCondition("isObjectDisplayed",
                                    "[CONDITION KO] El objeto no existe (" + objetoHijo + i + ")");
                        }
                        i = 0;
                    }
                } else if (DriverUtils.searchAndroidElements(objetoPadre + i).size() == 0 && i == 0) {
                    MainUtils.failedCondition("isObjectDisplayed",
                            "[CONDITION KO] El objeto no existe (" + objetoPadre + i + ")");
                }
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("SearchObjectsClick" + MainUtils.contadorWaitElement,
                    "[CONDITION EXCEPTION] SearchObjectsClick");
            MainUtils.contadorWaitElement++;
            throw e;
        }
        return HijoEncontrado;
    }

    /**
     * Método para simular un tap en el botón de atrás de los dispositivos móviles.
     */
    public static void mobileTapBackButton() {
        try {
            // Utilizar AndroidKey.BACK para simular el tap en el botón de atrás
            DriverUtils.androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            MainUtils.passedAction("[ACTION OK] Se pulsó el botón de atrás");
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapBackException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pudo pulsar el botón de atrás");
            throw e;
        }
    }

    /***
     * <strong>MOBILE ONLY</strong><br/>
     * <br/>
     * Método para arrastrar y soltar un elemento
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void mobileDragAndDrop(String objeto, int x, int y) {
        try {
            // Intenta encontrar el elemento directamente, asumiendo que solo habrá uno
            AndroidElement elementToDrag = DriverUtils.searchAndroidElement(objeto);
            if (elementToDrag == null) {
                MainUtils.failedActionOrImportantCondition("dragAndDrop" + MainUtils.contadorDragAndDrop,
                        "[ACTION KO] El objeto no existe (" + objeto + ")");
                MainUtils.contadorDragAndDrop++;
                throw new SkipException("No se encontró el elemento con el identificador: " + objeto);
            }

            MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");

            // Modificado para usar longPress en lugar de press
            new TouchAction(DriverUtils.androidDriver)
                    .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(elementToDrag))
                            .withDuration(Duration.ofMillis(1000))) // Aumenta la duración del longPress
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Espera después del longPress
                    .moveTo(PointOption.point(x, y)) // Mueve a la posición de destino
                    .release() // Suelta el elemento
                    .perform(); // Ejecuta la acción
            MainUtils.passedAction("[ACTION OK] Se arrastró el objeto -> " + objeto);

        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("dragAndDropException" + MainUtils.contadorDragAndDrop,
                    "[ACTION EXCEPTION] No se arrastró el objeto (" + objeto + ")");
            MainUtils.contadorDragAndDrop++;
            throw e;
        }
    }

    /*
     * <p>
     * 
     * </p>
     */
    public static void waitForElementToDisappear(String objeto, int timeoutInSeconds, long pollIntervalInMillis) {
        MainUtils.forceWait(Duration.ofSeconds(1));
        DriverUtils.androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), timeoutInSeconds, pollIntervalInMillis);
        By locator;
        switch (mainUtils.typeObjectID(objeto)) {
            case "XPATH":
                locator = By.xpath(MainUtils.objectID(objeto));
                break;
            case "ID":
                locator = By.id(MainUtils.objectID(objeto));
                break;
            default:
                locator = null;
                MainUtils.failedActionOrImportantCondition(
                        "[ACTION EXCEPTION] Se ha producido un fallo al buscar el elemento de espera (elemento a esperar no facilitado)");
                MainUtils.contadorWaitElement++;
        }
        try {
            MainUtils.highlightElement(MainUtils.objectID(objeto), "Accion");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            MainUtils.forceWait(Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            DriverUtils.androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] Se ha superado el tiempo de espera máximo del elemento");
            MainUtils.contadorWaitElement++;
            DriverUtils.androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            throw e;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[ACTION EXCEPTION] Se ha producido un fallo al buscar el elemento de espera");
            MainUtils.contadorWaitElement++;
            DriverUtils.androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            throw e;
        }
    }

    /*
     * <p>
     * Método que comprueba que el teclado esté abierto o cerrado
     * </p>
     */
    public static boolean checkkeyboard() {
        boolean estado = false;
        try {
            if (DriverUtils.androidDriver.isKeyboardShown()) {
                MainUtils.passedCondition("[CONDITION OK] El teclado está abierto correctamente.");
                estado = true;
            } else {
                MainUtils.failedCondition("[CONDITION KO] El teclado NO está abierto.");
            }
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "[CONDITION EXCEPTION] checkkeyboard. Error al comprobar si el teclado está abierto o cerrado");
            throw e;
        }
        return estado;
    }
}
