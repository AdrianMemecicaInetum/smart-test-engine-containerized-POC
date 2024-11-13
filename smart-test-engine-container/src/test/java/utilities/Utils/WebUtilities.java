package utilities.Utils;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import org.testng.reporters.jq.Main;

public class WebUtilities {
    public static Boolean modoDemo = false;
    private static MainUtils mainUtils;

    public WebUtilities() {
        mainUtils = MainUtils.setInstance();
    }

    /**************************************************
     *
     * ACCIONES WEB
     *
     **************************************************/

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer click en un elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webClick(String objeto) {
        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.searchWebElements(objeto).size() > 0
                            && DriverUtils.searchWebElement(objeto).isEnabled()
                            && DriverUtils.searchWebElement(objeto).isDisplayed()) {
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        DriverUtils.searchWebElement(objeto).click();
                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + objeto);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                    if (retries >= MainUtils.maxRetries) {
                        MainUtils.contadorTap++;
                        throw e;
                    }
                    retries++;
                    MainUtils.forceWait(Duration.ofSeconds(1));
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + objeto + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer doble click en un elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webDoubleClick(String objeto) {
        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.searchWebElements(objeto).size() > 0
                            && DriverUtils.searchWebElement(objeto).isEnabled()
                            && DriverUtils.searchWebElement(objeto).isDisplayed()) {
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        WebElement peticion = DriverUtils.searchWebElement(objeto);
                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.doubleClick(peticion).perform();
                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + objeto);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                    if (retries >= MainUtils.maxRetries) {
                        MainUtils.contadorTap++;
                        throw e;
                    }
                    retries++;
                    MainUtils.forceWait(Duration.ofSeconds(1));
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + objeto + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer click derecho en un elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webClickRight(String objeto) {
        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.searchWebElements(objeto).size() > 0
                            && DriverUtils.searchWebElement(objeto).isEnabled()
                            && DriverUtils.searchWebElement(objeto).isDisplayed()) {
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        WebElement peticion = DriverUtils.searchWebElement(objeto);
                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.contextClick(peticion).perform();
                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + objeto);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] El objeto no existe o no está habilitado/visible (" + objeto + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                    if (retries >= MainUtils.maxRetries) {
                        MainUtils.contadorTap++;
                        throw e;
                    }
                    retries++;
                    MainUtils.forceWait(Duration.ofSeconds(1));
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + objeto + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer Drag&Drop de un elemento del repositorio de objetos a otro
     * elemento del repositorio de objetos
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webDragAndDrop(String objetoDrag, String objetoDrop) {
        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.searchWebElements(objetoDrag).size() > 0
                            && DriverUtils.searchWebElements(objetoDrop).size() > 0) {
                        MainUtils.highlightElement(mainUtils.objectID(objetoDrag), "Accion");
                        MainUtils.highlightElement(mainUtils.objectID(objetoDrop), "Accion");

                        Actions actions = new Actions(DriverUtils.webDriver);
                        WebElement dragElement = DriverUtils.searchWebElement(objetoDrag);
                        WebElement dropElement = DriverUtils.searchWebElement(objetoDrop);
                        actions.moveToElement(dragElement).clickAndHold().perform();
                        actions.moveToElement(dropElement).perform();
                        actions.release(dropElement).perform();

                        MainUtils.passedAction(
                                "[ACTION OK] Se arrastró el objeto -> " + objetoDrag + " sobre el objeto -> "
                                        + objetoDrop);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento de arrastrar número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        if (!(DriverUtils.searchWebElements(objetoDrag).size() > 0
                                && DriverUtils.searchWebElements(objetoDrop).size() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + objetoDrag + " - " + objetoDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException(
                                    "[ACTION STOP] Los objetos no existen (" + objetoDrag + " - " + objetoDrop + ")");
                        } else if (!(DriverUtils.searchWebElements(objetoDrag).size() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + objetoDrag + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + objetoDrag + ")");
                        } else {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + objetoDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + objetoDrop + ")");
                        }
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento arrastrar número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en los objetos (" + objetoDrag + " - "
                                        + objetoDrop + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] ElementClickInterceptedException en los objetos ("
                                + objetoDrag + " - " + objetoDrop + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("dragException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se arrastró el objeto (" + objetoDrag + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para enviar un texto del repositorio de datos a un objeto del
     * repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param texto  Nombre de la variables del texto del repositorio de datos.
     * @param testId ID de la prueba.
     * @throws Exception
     */
    public static void webSendkeysSharedRepository(String objeto, String texto) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (MainUtils.obtainSharedJSONData(texto) != null) {
                        DriverUtils.searchWebElement(objeto)
                                .sendKeys(MainUtils.obtainSharedJSONData(texto));
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        MainUtils.passedAction(
                                "[ACTION OK] Se introdujo " + MainUtils.obtainSharedJSONData(texto)
                                        + " en " + objeto);
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
                    "[ACTION EXCEPTION] NO se introdujo (" + MainUtils.getDataFromRepository(MainUtils.testId, texto)
                            + ") en (" + objeto + ")");
            MainUtils.contadorSendkeysRepository++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer Drag&Drop de un elemento WebElement a otro
     * elemento WebElement
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webDragAndDropByElement(WebElement elementoDrag, WebElement elementoDrop) {
        String xpathDrag = elementoDrag.toString().split("xpath: ")[1].replace("]", "") + "]";
        String xpathDrop = elementoDrop.toString().split("xpath: ")[1].replace("]", "") + "]";
        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (xpathDrag.length() > 0 && xpathDrop.length() > 0) {
                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.moveToElement(elementoDrag).clickAndHold().perform();
                        actions.moveToElement(elementoDrop).perform();
                        actions.release(elementoDrop).perform();

                        MainUtils.passedAction(
                                "[ACTION OK] Se arrastró el objeto -> " + xpathDrag + " sobre el objeto -> "
                                        + xpathDrop);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento de arrastrar número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        if (!(xpathDrag.length() > 0 && xpathDrop.length() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrag + " - " + xpathDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException(
                                    "[ACTION STOP] Los objetos no existen (" + xpathDrag + " - " + xpathDrop + ")");
                        } else if (!(xpathDrag.length() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrag + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + xpathDrag + ")");
                        } else {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + xpathDrop + ")");
                        }
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento arrastrar número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en los objetos (" + xpathDrag + " - "
                                        + xpathDrop + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] ElementClickInterceptedException en los objetos ("
                                + xpathDrag + " - " + xpathDrop + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("dragException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se arrastró el objeto (" + xpathDrag + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer Drag&Drop de un elemento xpath a otro
     * elemento xpath
     *
     * @param objeto ID del objeto en el repositorio de objetos
     */
    public static void webDragAndDropByXpath(String xpathDrag, String xpathDrop) {
        WebDriver driver = DriverUtils.getDriver();
        WebElement elementoDrag = driver.findElement(By.xpath(xpathDrag));
        WebElement elementoDrop = driver.findElement(By.xpath(xpathDrop));

        try {
            if (modoDemo) {
                MainUtils.forceWait(Duration.ofSeconds(2));
            }
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (xpathDrag.length() > 0 && xpathDrop.length() > 0) {
                        MainUtils.highlightElement(mainUtils.objectID(xpathDrag), "Accion");
                        MainUtils.highlightElement(mainUtils.objectID(xpathDrop), "Accion");

                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.moveToElement(elementoDrag).clickAndHold().perform();
                        actions.moveToElement(elementoDrop).perform();
                        actions.release(elementoDrop).perform();

                        MainUtils.passedAction(
                                "[ACTION OK] Se arrastró el objeto -> " + xpathDrag + " sobre el objeto -> "
                                        + xpathDrop);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento de arrastrar número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        if (!(DriverUtils.getDriver().findElements(By.xpath(xpathDrag)).size() > 0
                                && DriverUtils.getDriver().findElements(By.xpath(xpathDrop)).size() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrag + " - " + xpathDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException(
                                    "[ACTION STOP] Los objetos no existen (" + xpathDrag + " - " + xpathDrop + ")");
                        } else if (!(DriverUtils.getDriver().findElements(By.xpath(xpathDrag)).size() > 0)) {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrag + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + xpathDrag + ")");
                        } else {
                            MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                    "[ACTION KO]  (" + xpathDrop + ")");
                            MainUtils.contadorTap++;
                            repeat = false;
                            throw new SkipException("[ACTION STOP] El objeto no existe (" + xpathDrop + ")");
                        }
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage(
                                "[ACTION RETRY] Reintento arrastrar por ElementClickInterceptedException número "
                                        + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("Arrastrar" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en los objetos (" + xpathDrag + " - "
                                        + xpathDrop + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] ElementClickInterceptedException en los objetos ("
                                + xpathDrag + " - " + xpathDrop + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("dragException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se arrastró el objeto (" + xpathDrag + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para obtener la cantidad de elementos con los tres primeros caracteres
     *
     * @param elements Una List<WebElement>
     * @param objeto
     */

    /**
     * Metodo para hacer scroll a un elemento en un contenedor cuando no esta
     * visible
     * 
     * @param WebElement
     * @throws Exception
     */

    public static void scrollToElement(WebElement element) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (element.isDisplayed()) {
                    JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getDriver();
                    // Hacer scroll hasta que el elemento sea visible
                    js.executeScript("arguments[0].scrollIntoView(true);", element);
                    MainUtils.passedCondition(
                            "[CONDITION OK] Se realizo el scroll hasta el elemento  (" + element + ")");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.passedCondition(
                            "[CONDITION KO] No se pudo realizar el scroll hasta el elemento  (" + element + ")");
                    repeat = false;
                } else {
                    MainUtils.failedActionOrImportantCondition("scroll" + MainUtils.contadorTap,
                            "[ACTION KO] El objeto no existe (" + element + ")");
                    MainUtils.contadorTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + element + ")");
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pudo hacer scroll sobre (" + element + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /**
     * Metodo para comprobar si un WebElement tiene el mismo nombre
     * 
     * @param String element,
     * @param String textoEsperado
     */

    public static boolean checkTextElementEqual(String locator, String textoEsperado) {

        try {
            int retries = 1;
            boolean repeat = true;
            WebElement element = DriverUtils.webDriver.findElement(By.xpath(locator));
            String textoElemento = element.getText();
            do {
                if (textoElemento.contains(textoEsperado)) {
                    MainUtils.passedCondition("[CONDITION OK] El texto del elemento es:  (" + textoElemento + ")");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.passedCondition("[CONDITION KO] El elemento no tiene el texto: (" + textoEsperado + ")");
                    repeat = false;
                } else {
                    MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                            "[ACTION KO] El objeto no existe (" + locator + ")");
                    MainUtils.contadorTap++;
                    repeat = false;
                    throw new SkipException("[ACTION STOP] El objeto no existe (" + locator + ")");
                }
                return textoElemento.contains(textoEsperado);
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se obtuvo el texto del objeto (" + locator + ")");
            MainUtils.contadorTap++;
            throw e;
        }

    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer click en un elemento directamente mediante xpath.
     * 
     * @param identificador xpath del objeto.
     * @throws Exception
     */
    public static void webClickWithout(String xpath) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.webDriver.findElements(By.xpath(xpath)).size() > 0) {
                        MainUtils.highlightElement(xpath, "Accion");
                        DriverUtils.webDriver.findElement(By.xpath(xpath)).click();
                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + xpath);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] El objeto no existe (" + xpath + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage(
                                "[ACTION RETRY] Reintento click número " + retries
                                        + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en el objeto (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] ElementClickInterceptedException en el objeto (" + xpath + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + xpath + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer click en un elemento directamente mediante xpath.
     * 
     * @param identificador xpath del objeto.
     * @throws Exception
     */
    public static void webDoubleClickWithout(String xpath) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.webDriver.findElements(By.xpath(xpath)).size() > 0) {
                        MainUtils.highlightElement(xpath, "Accion");

                        WebElement peticion = DriverUtils.webDriver.findElement(By.xpath(xpath));
                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.doubleClick(peticion).perform();

                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto -> " + xpath);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] El objeto no existe (" + xpath + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage(
                                "[ACTION RETRY] Reintento click número " + retries
                                        + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en el objeto (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] ElementClickInterceptedException en el objeto (" + xpath + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + xpath + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /***
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para hacer click derecho en un elemento directamente mediante xpath.
     * 
     * @param identificador xpath del objeto.
     * @throws Exception
     */
    public static void webClicRightkWithout(String xpath) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                try {
                    if (DriverUtils.webDriver.findElements(By.xpath(xpath)).size() > 0) {
                        MainUtils.highlightElement(xpath, "Accion");

                        WebElement peticion = DriverUtils.webDriver.findElement(By.xpath(xpath));
                        Actions actions = new Actions(DriverUtils.webDriver);
                        actions.contextClick(peticion).perform();

                        MainUtils.passedAction("[ACTION OK] Se pulsó el objeto con el click derecho-> " + xpath);
                        repeat = false;
                    } else if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage("[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] El objeto no existe (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException("[ACTION STOP] El objeto no existe (" + xpath + ")");
                    }
                } catch (ElementClickInterceptedException e) {
                    if (retries < MainUtils.maxRetries) {
                        MainUtils.printReportMessage(
                                "[ACTION RETRY] Reintento click número " + retries + ".");
                        retries++;
                        MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                        MainUtils.forceWait(Duration.ofSeconds(1));
                    } else {
                        MainUtils.failedActionOrImportantCondition("click" + MainUtils.contadorTap,
                                "[ACTION KO] ElementClickInterceptedException en el objeto (" + xpath + ")");
                        MainUtils.contadorTap++;
                        repeat = false;
                        throw new SkipException(
                                "[ACTION STOP] ElementClickInterceptedException en el objeto (" + xpath + ")");
                    }
                }
            } while (repeat);
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("tapException" + MainUtils.contadorTap,
                    "[ACTION EXCEPTION] No se pulsó el objeto (" + xpath + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para enviar un texto bruto a un objeto del repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param texto  Texto en bruto.
     */
    public static void webSendkeys(String objeto, String texto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {

                    DriverUtils.searchWebElement(objeto).sendKeys(texto);
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                    MainUtils.passedAction("[ACTION OK] Se introdujo " + texto + " en " + objeto);

                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento sendkeys número " + retries + ".");
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
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para enviar un objeto Keys a un objeto del repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param keys   Objeto Keys que identifica a la acción a realizar.
     */
    public static void webSendkeysWithKey(String objeto, Keys keys) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                    DriverUtils.searchWebElement(objeto).sendKeys(keys);
                    MainUtils.passedAction("[ACTION OK] Se realizó " + keys + " en " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento sendkeys número " + retries + ".");
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
                    "[ACTION EXCEPTION] No se realizó " + keys + " en " + objeto);
            MainUtils.contadorSendkeys++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para enviar un texto del repositorio de datos a un objeto del
     * repositorio de objetos.
     *
     * @param objeto Nombre del objeto del repositorio de objetos.
     * @param texto  Nombre de la variables del texto del repositorio de datos.
     * @param testId ID de la prueba.
     * @throws Exception
     */
    public static void webSendkeysRepository(String objeto, String texto) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {

                    if (MainUtils.obtainJSONData(texto) != null) {
                        DriverUtils.searchWebElement(objeto)
                                .sendKeys(MainUtils.obtainJSONData(texto));
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        MainUtils.passedAction(
                                "[ACTION OK] Se introdujo " + MainUtils.obtainJSONData(texto)
                                        + " en " + objeto);
                    } else if (MainUtils.obtainSharedJSONData(texto) != null) {
                        DriverUtils.searchWebElement(objeto)
                                .sendKeys(MainUtils.obtainSharedJSONData(texto));
                        MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                        MainUtils.passedAction(
                                "[ACTION OK] Se introdujo " + MainUtils.obtainSharedJSONData(texto)
                                        + " en " + objeto);
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
                    "[ACTION EXCEPTION] NO se introdujo (" + MainUtils.getDataFromRepository(MainUtils.testId, texto)
                            + ") en (" + objeto + ")");
            MainUtils.contadorSendkeysRepository++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para borrar los valores de un objeto.
     */
    public static void webClear(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                    DriverUtils.searchWebElement(objeto).clear();
                    MainUtils.passedAction("[ACTION OK] Se limpió el objeto -> " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage("[ACTION RETRY] Reintento clear número " + retries + ".");
                    MainUtils.currentTest.setActionRetries(MainUtils.currentTest.getActionRetries() + 1);
                    retries++;
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

    public static void webScrollScreenDown(int pixeles) {
        DriverUtils.scrollDown(pixeles);
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web al principio del documento.
     * Utiliza la simulación de teclas de dirección para el desplazamiento.
     */
    public static void webScrollTop() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollTo(0, 0)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 50; i++) {
            element.sendKeys(Keys.UP);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web al final del documento.
     * Utiliza la simulación de teclas de dirección para el desplazamiento.
     */
    public static void webScrollBottom() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 50; i++) {
            element.sendKeys(Keys.DOWN);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web hacia la derecha.
     * Utiliza la simulación de teclas de dirección para el desplazamiento
     * horizontal.
     */
    public static void webScrollRight() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollBy(250, 0)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 5; i++) {
            element.sendKeys(Keys.RIGHT);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web hacia la izquierda.
     * Utiliza la simulación de teclas de dirección para el desplazamiento
     * horizontal.
     */
    public static void webScrollLeft() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollBy(-250, 0)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 5; i++) {
            element.sendKeys(Keys.LEFT);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web hacia abajo.
     * Utiliza la simulación de teclas de dirección para el desplazamiento vertical.
     */
    public static void webScrollDown() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollBy(0,250)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 5; i++) {
            element.sendKeys(Keys.DOWN);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para desplazar la página web hacia arriba.
     * Utiliza la simulación de teclas de dirección para el desplazamiento vertical.
     */
    public static void webScrollUp() {
        // JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        // jse.executeScript("window.scrollBy(0,-250)");
        WebElement element = DriverUtils.getDriver().findElement(By.tagName("html"));
        for (int i = 0; i < 5; i++) {
            element.sendKeys(Keys.UP);
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para aceptar una alerta emergente en la página web.
     * Realiza la acción de aceptar en el cuadro de diálogo de alerta.
     */
    public static void webAcceptAlert() {
        Alert alert = DriverUtils.getDriver().switchTo().alert();
        alert.accept();
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para refrescar la página web actual.
     * Esta operación es equivalente a pulsar el botón de refrescar en el navegador.
     */
    public static void webRefreshPage() {
        DriverUtils.getDriver().navigate().refresh();
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para limpiar un elemento de la página web introduciendo el valor '0'.
     * Primero selecciona el contenido actual del elemento y luego introduce el
     * número '0'.
     * 
     * @param element Elemento web a ser limpiado.
     */
    public static void webClearElementWithZero(WebElement element) {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.NUMPAD0);
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * aparece en la aplicación.
     *
     * @param objeto Donde lo espero?
     */
    public static boolean webIsObjectDisplayed(String objeto) {

        try {
            boolean state = false;
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] Se encontró el objeto (" + objeto + ")");
                    repeat = false;
                    state = true;
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
                    state = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
            return state;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto identificado mediante xpath
     * aparece en la aplicación.
     *
     * @param objeto Donde lo espero?
     */
    public static boolean webIsObjectDisplayedByXpath(String xpath) {
        try {
            boolean state = false;
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.getDriver().findElements(By.xpath(xpath)).size() > 0) {
                    MainUtils.highlightElement(xpath, "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] Se encontró el objeto con XPATH (" + xpath + ")");
                    repeat = false;
                    state = true;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectDisplayed número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                            "[CONDITION KO] El objeto no existe con el xpath (" + xpath + ")");
                    MainUtils.contadorIsObjectDisplayed++;
                    repeat = false;
                    state = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
            return state;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto con xpath " + xpath);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * aparece en la aplicación.
     *
     * @param objeto Donde lo espero?
     */
    public static boolean webIsImportantObjectDisplayed(String objeto) {
        try {
            boolean state = false;

            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    MainUtils.passedCondition("[CONDITION OK] Se encontró el objeto (" + objeto + ")");
                    repeat = false;
                    state = true;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectDisplayed número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsObjectDisplayed++;
                    repeat = false;
                    state = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
            return state;
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    public static void webIsObjectNotDisplayed(String objeto) {
        try {

            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() <= 0) {
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectNotDisplayedException" + MainUtils.contadorIsObjectNotDisplayed,
                    "[CONDITION EXCEPTION] isObjectNotDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectNotDisplayed++;
            throw e;
        }
    }

    public static void webIsImportantObjectNotDisplayed(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() <= 0) {
                    MainUtils.passedCondition("[CONDITION OK] El objeto no existe (" + objeto + ")");
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento isObjectNotDisplayed número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedActionOrImportantCondition(
                            "isObjectNotDisplayed" + MainUtils.contadorIsObjectNotDisplayed,
                            "[CONDITION KO] El objeto existe (" + objeto + ")");
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isObjectNotDisplayedException" + MainUtils.contadorIsObjectNotDisplayed,
                    "[CONDITION EXCEPTION] isObjectNotDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectNotDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto en bruto.
     *
     * @param objeto        Identificador del objeto del repositorio de objetos.
     * @param textoAValidar Identificador del dato del repositorio de datos.
     */
    public static void webIsTextInObject(String objeto, String textoAValidar) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    if (DriverUtils.searchWebElement(objeto).getText().equals(textoAValidar)) {
                        MainUtils.passedCondition(
                                "[CONDITION OK] Texto correcto encontrado (" + textoAValidar + ")");
                    } else {
                        MainUtils.failedCondition("isTextInObject" + MainUtils.contadorIsTextInObject,
                                "[CONDITION KO] El objeto existe (" + objeto + "). No se encuentra el texto "
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isTextInObjectException" + MainUtils.contadorIsTextInObject,
                    "[CONDITION EXCEPTION] isTextInObject. No se encuentra el texto " + textoAValidar + " en el objeto "
                            + objeto);
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto en bruto.
     *
     * @param objeto        Identificador del objeto del repositorio de objetos.
     * @param textoAValidar Identificador del dato del repositorio de datos.
     */
    public static void webIsImportantTextInObject(String objeto, String textoAValidar) {
        try {

            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {

                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    if (DriverUtils.searchWebElement(objeto).getText().equals(textoAValidar)) {
                        MainUtils.passedCondition(
                                "[CONDITION OK] Texto correcto encontrado (" + textoAValidar + ")");
                    } else {
                        MainUtils.failedActionOrImportantCondition(
                                "isTextInObject" + MainUtils.contadorIsTextInObject,
                                "[CONDITION KO] El objeto existe (" + objeto + "). No se encuentra el texto "
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
                    MainUtils.failedActionOrImportantCondition(
                            "isTextInObject" + MainUtils.contadorIsTextInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsTextInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isTextInObjectException" + MainUtils.contadorIsTextInObject,
                    "[CONDITION EXCEPTION] isTextInObject. No se encuentra el texto " + textoAValidar + " en el objeto "
                            + objeto);
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto    Identificador del objeto del repositorio de objetos.
     * @param textoData Identificador del dato del repositorio de datos.
     * @param testId    Identificador de la prueba.
     */
    public static void webIsDataInObject(String objeto, String textoData, String testId) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    MainUtils.highlightElement(mainUtils.objectID(objeto), "Condicion");
                    if (MainUtils.getDataFromRepository(testId, textoData) != null) {
                        if (DriverUtils.searchWebElement(objeto).getText()
                                .equals(MainUtils.getDataFromRepository(testId,
                                        textoData))) {
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isDataInObjectException" + MainUtils.contadorIsDataInObject,
                    "[CONDITION EXCEPTION] isDataInObject");
            MainUtils.contadorIsDataInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto    Identificador del objeto del repositorio de objetos.
     * @param textoData Identificador del dato del repositorio de datos.
     * @param testId    Identificador de la prueba.
     */
    public static void webIsImportantDataInObject(String objeto, String textoData, String testId) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (MainUtils.getDataFromRepository(testId, textoData) != null) {
                        if (DriverUtils.searchWebElement(objeto).getText()
                                .equals(MainUtils.getDataFromRepository(testId,
                                        textoData))) {
                            MainUtils.passedCondition(
                                    "[CONDITION OK] Texto correcto encontrado (" + textoData + ")");
                        } else {
                            MainUtils.failedActionOrImportantCondition(
                                    "isDataInObject" + MainUtils.contadorIsDataInObject,
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
                    MainUtils.failedActionOrImportantCondition(
                            "isDataInObject" + MainUtils.contadorIsDataInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsDataInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isDataInObjectException" + MainUtils.contadorIsDataInObject,
                    "[CONDITION EXCEPTION] isDataInObject");
            MainUtils.contadorIsDataInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto    Identificador del objeto del repositorio de objetos.
     * @param textoData Identificador del dato del repositorio de datos.
     * @param testId    Identificador de la prueba.
     */
    public static void webIsImportantDataInXpath(String xpath, String textoData, String testId) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.webDriver.findElements(By.xpath(xpath)).size() > 0) {
                    if (MainUtils.getDataFromRepository(testId, textoData) != null) {
                        if (DriverUtils.webDriver.findElement(By.xpath(xpath)).getText()
                                .equals(MainUtils.getDataFromRepository(testId,
                                        textoData))) {
                            MainUtils.passedCondition(
                                    "[CONDITION OK] Texto correcto encontrado (" + textoData + ")");
                        } else {
                            MainUtils.failedActionOrImportantCondition(
                                    "isDataInObject" + MainUtils.contadorIsDataInObject,
                                    "[CONDITION KO] El texto no corresponde (" + xpath + ")");
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
                    MainUtils.failedActionOrImportantCondition(
                            "isDataInObject" + MainUtils.contadorIsDataInObject,
                            "[CONDITION KO] El objeto no existe (" + xpath + ")");
                    MainUtils.contadorIsDataInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isDataInObjectException" + MainUtils.contadorIsDataInObject,
                    "[CONDITION EXCEPTION] isDataInObject");
            MainUtils.contadorIsDataInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto   Identificador del objeto del repositorio de objetos.
     * @param atributo Nombre del atributo.
     * @param valor    Valor del atributo.
     */
    public static void webIsAttributeInObject(String objeto, String atributo, String valor) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (DriverUtils.searchWebElement(objeto).getAttribute(atributo).equals(valor)) {
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isAttributeInObjectException" + MainUtils.contadorIsAttributeInObject,
                    "[CONDITION EXCEPTION] No se encuentra el atributo '" + atributo + "' con valor '" + valor
                            + "' en el objeto '" + objeto + "'. highlightElement generada: isAttributeInObjectException"
                            + MainUtils.contadorIsAttributeInObject);
            MainUtils.contadorIsAttributeInObject++;
            throw e;
        }

    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto del repositorio de objetos
     * contiene un texto del repositorio de datos.
     *
     * @param objeto   Identificador del objeto del repositorio de objetos.
     * @param atributo Nombre del atributo.
     * @param valor    Valor del atributo.
     * @throws Exception
     */
    public static void webIsImportantAttributeInObject(String objeto, String atributo, String valor) throws Exception {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(1);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (DriverUtils.searchWebElement(objeto).getAttribute(atributo)
                            .equals(MainUtils.obtainJSONData(valor))) {
                        MainUtils.passedCondition(
                                "[CONDITION OK] Se encontró '" + MainUtils.obtainJSONData(valor) + "' en el atributo '"
                                        + atributo + "' del objeto '" + objeto + "'");
                    } else {
                        MainUtils.failedActionOrImportantCondition(
                                "isAttributeInObject" + MainUtils.contadorIsAttributeInObject,
                                "[CONDITION KO] No se encuentra el atributo '" + atributo + "' con valor '"
                                        + MainUtils.obtainJSONData(valor) + "' en el objeto '" + objeto
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
                    MainUtils.failedActionOrImportantCondition(
                            "isAttributeInObject" + MainUtils.contadorIsAttributeInObject,
                            "[CONDITION KO]  El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsAttributeInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition(
                    "isAttributeInObjectException" + MainUtils.contadorIsAttributeInObject,
                    "[CONDITION EXCEPTION] No se encuentra el atributo '" + atributo + "' con valor '" + valor
                            + "' en el objeto '" + objeto + "'. highlightElement generada: isAttributeInObjectException"
                            + MainUtils.contadorIsAttributeInObject);
            MainUtils.contadorIsAttributeInObject++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto checkbox está seleccionado.
     * Es válido únicamente para objetos tipo checkbox o input con propiedad
     * type="checkbox"
     *
     * @param objeto
     */
    public static void webIsObjectSelected(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(5);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (DriverUtils.searchWebElement(objeto).isSelected()) {
                        MainUtils.passedCondition("[CONDITION OK] El objeto está seleccionado (" + objeto + ")");
                    } else {
                        MainUtils.failedCondition(
                                "isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                                "[CONDITION KO] El objeto no está seleccionado (" + objeto + ")");
                    }
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedCondition("isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método condicional para comprobar si un objeto checkbox no está seleccionado.
     * Es válido únicamente para objetos tipo checkbox o input con propiedad
     * type="checkbox"
     *
     * @param objeto
     */
    public static void webIsObjectNotSelected(String objeto) {
        try {
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(5);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    if (DriverUtils.searchWebElement(objeto).isSelected()) {
                        MainUtils.failedCondition(
                                "isObjectDisplayed" + MainUtils.contadorIsObjectDisplayed,
                                "[CONDITION KO] El objeto está seleccionado (" + objeto + ")");
                    } else {
                        MainUtils.passedCondition("[CONDITION OK] El objeto no está seleccionado (" + objeto + ")");
                    }
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
            DriverUtils.restaurarTiempoDeEsperaWEB();
        } catch (Exception e) {
            MainUtils.failedCondition("isObjectDisplayedException" + MainUtils.contadorIsObjectDisplayed,
                    "[CONDITION EXCEPTION] isObjectDisplayed. No se encuentra el objeto " + objeto);
            MainUtils.contadorIsObjectDisplayed++;
            throw e;
        }
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para obtener el texto que contiene un objeto
     *
     * @param objeto Identificador del objeto del repositorio de objetos.
     */
    public static String webGetText(String objeto) {
        try {
            String text = "";
            int retries = 1;
            boolean repeat = true;
            DriverUtils.cambiarTiempoDeEsperaWEB(5);
            do {
                if (DriverUtils.searchWebElements(objeto).size() > 0) {
                    text = DriverUtils.searchWebElement(objeto).getText();
                    MainUtils.passedCondition("[CONDITION OK] Se ha obtenido el texto del objeto " + objeto);
                    repeat = false;
                } else if (retries < MainUtils.maxRetries) {
                    MainUtils.printReportMessage(
                            "[CONDITION RETRY] Reintento getTextObject número " + retries + ".");
                    retries++;
                    MainUtils.currentTest.setConditionRetries(MainUtils.currentTest.getConditionRetries() + 1);
                    MainUtils.forceWait(Duration.ofMillis(100));
                } else {
                    MainUtils.failedCondition("getTextObject" + MainUtils.contadorIsTextInObject,
                            "[CONDITION KO] El objeto no existe (" + objeto + ")");
                    MainUtils.contadorIsTextInObject++;
                    repeat = false;
                }
            } while (repeat);
            DriverUtils.restaurarTiempoDeEsperaWEB();
            return text;
        } catch (Exception e) {
            MainUtils.failedCondition("getTextObject" + MainUtils.contadorIsTextInObject,
                    "[CONDITION EXCEPTION] getTextObject. No se puede obtener el texto del objeto " + objeto);
            MainUtils.contadorIsTextInObject++;
            throw e;
        }
    }

    /**
     * <b>Convierte un WebElement a xpath</b>
     * <br>
     * 
     * @param elemento elemento del cual queremos capturar el xpath
     */
    public static String webElementToXpath(WebElement elemento) throws Exception {
        // TODO validaciones
        String webElementString = elemento.toString();
        int xpathIndex = webElementString.indexOf("xpath: ");
        if (xpathIndex == -1) {
            return "";
        }
        String xpathPart = webElementString.substring(xpathIndex + 7);
        if (xpathPart.endsWith("]")) {
            xpathPart = xpathPart.substring(0, xpathPart.length() - 1);
        }
        return xpathPart.trim();
    }

    /**
     * <strong>WEB ONLY</strong><br/>
     * <br/>
     * Método para acceder a una URL.
     */
    public static void navigateToURL(String url) {
        try {
            DriverUtils.getDriver().get(url);
            MainUtils.forceWait(Duration.ofSeconds(1));
            if (DriverUtils.getDriver().getCurrentUrl().equals(url)) {
                // MainUtils.highlightElement(mainUtils.objectID(objeto), "Accion");
                // MainUtils.imageSum();
                MainUtils.screenShotRecord(MainUtils.testId, "[SCREEN_CAPTURE] GetURL");
                MainUtils.passedAction("[ACTION OK] Se accidió correctamente a la URL -> " + url);

            } else {
                MainUtils.failedActionOrImportantCondition("Navigate",
                        "[ACTION KO] No se ha podido realizar la navegacion a la siguiente URL (" + url + ")");
                MainUtils.contadorTap++;
            }

        } catch (Exception e) {
            MainUtils.failedActionOrImportantCondition("Navigate",
                    "[ACTION KO] No se ha podido realizar con la navegacion a la siguiente URL (" + url + ")");
            MainUtils.contadorTap++;
            throw e;
        }
    }

}