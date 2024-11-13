package api.TestSuites;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.Utils.APIUtilities;
import utilities.Utils.DriverUtils;
import utilities.Utils.MainUtils;

public class TESTAPI {

    // Instanciar acciones/condiciones
    public static String testId;
    private static TESTAPI ohsq_instance = null;

    // Instanciar clases de test con patrón Singleton
    public static TESTAPI setInstance() {
        if (ohsq_instance == null) {
            ohsq_instance = new TESTAPI();
        }
        return ohsq_instance;
    }

    @BeforeClass(alwaysRun = true)
    public void executionSetup() throws Exception {
        MainUtils.testSuiteGeneration(this.getClass().getName().substring(15));
    }

    @BeforeMethod
    public void setup_test() throws Exception {
        testId = "TESTAPI";
        // DriverUtils.instanciador_Chrome();
        DriverUtils.instanciador_api();
        MainUtils.setConditionsTrigger(true); // Se pone a false para evitar ejecución de condiciones
    }

    @AfterMethod
    public void breakup_test() throws Exception {
        // MainUtils.testEnd(testId, "api.TestSuites.APIHOS");
        DriverUtils.framework_breakup();
    }

    @AfterSuite
    public static void testSuiteEnd() throws Exception {
        MainUtils.testSuiteEnd();
    }

    /**************************************************
     * 
     * 
     * VARIABLES GLOBALES
     * 
     * 
     **************************************************/
    public static String token;
    public static String[] cookies;

    /**************************************************
     * 
     * Precondiciones
     * 
     **************************************************/

    /**************************************************
     * 
     * Pasos Precondiciones
     * 
     **************************************************/

    /**************************************************
     * 
     * Pruebas
     * 
     **************************************************/

    /**************************************************
     * TESTAPI_TC001 - GET
     * 
     * @throws InterruptedException, Exception
     *
     **************************************************/

    @Test(description = "TESTAPI_TC001 - GET", enabled = true)
    public void TESTAPI_TC001() throws InterruptedException, Exception {
        /*****************************************/
        /* Métodos precondiciones */
        /*****************************************/
        if (MainUtils.getConditionsTrigger()) {
            MainUtils.testInicialization("TESTAPI_TC001",
                    "Realiza una peticion GET de ejemplo y devuelve una lista de  nombres");
        }
        testId = MainUtils.testId;
        MainUtils.printReportMessage("-----------------------------------");
        MainUtils.printReportMessage("[FRAMEWORK] Comienzo de Test Case: " + testId);
        /*****************************************/
        /* Métodos de la prueba */
        /*****************************************/
        TESTAPI_TC001_step01();
    }

    /**************************************************
     * TESTAPI_TC002 - POST
     * 
     * @throws InterruptedException, Exception
     *
     **************************************************/

    @Test(description = "TESTAPI_TC002 - POST", enabled = true)
    public void TESTAPI_TC002() throws InterruptedException, Exception {
        /*****************************************/
        /* Métodos precondiciones */
        /*****************************************/
        if (MainUtils.getConditionsTrigger()) {
            MainUtils.testInicialization("TESTAPI_TC002",
                    "Realiza una peticion POST de ejemplo y devuelve un código HTTP 201");
        }
        testId = MainUtils.testId;
        MainUtils.printReportMessage("-----------------------------------");
        MainUtils.printReportMessage("[FRAMEWORK] Comienzo de Test Case: " + testId);
        /*****************************************/
        /* Métodos de la prueba */
        /*****************************************/
        TESTAPI_TC002_step01();
    }

    /**************************************************
     * TESTAPI_TC003 - PUT
     * 
     * @throws InterruptedException, Exception
     *
     **************************************************/

    @Test(description = "TESTAPI_TC003 - PUT", enabled = true)
    public void TESTAPI_TC003() throws InterruptedException, Exception {
        /*****************************************/
        /* Métodos precondiciones */
        /*****************************************/
        if (MainUtils.getConditionsTrigger()) {
            MainUtils.testInicialization("TESTAPI_TC003",
                    "Realiza una peticion PUT de ejemplo y devuelve un código HTTP 200 y una respuesta de ejemplo");
        }
        testId = MainUtils.testId;
        MainUtils.printReportMessage("-----------------------------------");
        MainUtils.printReportMessage("[FRAMEWORK] Comienzo de Test Case: " + testId);
        /*****************************************/
        /* Métodos de la prueba */
        /*****************************************/
        TESTAPI_TC003_step01();
    }

    /**************************************************
     * TESTAPI_TC004 - PATCH
     * 
     * @throws InterruptedException, Exception
     *
     **************************************************/

    @Test(description = "TESTAPI_TC004 - PATCH", enabled = true)
    public void TESTAPI_TC004() throws InterruptedException, Exception {
        /*****************************************/
        /* Métodos precondiciones */
        /*****************************************/
        if (MainUtils.getConditionsTrigger()) {
            MainUtils.testInicialization("TESTAPI_TC004", "Realizamos una petición PATCH y obtiene un código HTTP 200");
        }
        testId = MainUtils.testId;
        MainUtils.printReportMessage("-----------------------------------");
        MainUtils.printReportMessage("[FRAMEWORK] Comienzo de Test Case: " + testId);
        /*****************************************/
        /* Métodos de la prueba */
        /*****************************************/
        TESTAPI_TC004_step01();
    }

    /**************************************************
     * TESTAPI_TC005 - DELETE
     * 
     * @throws InterruptedException, Exception
     *
     **************************************************/

    @Test(description = "TESTAPI_TC005 - DELETE", enabled = true)
    public void TESTAPI_TC005() throws InterruptedException, Exception {
        /*****************************************/
        /* Métodos precondiciones */
        /*****************************************/
        if (MainUtils.getConditionsTrigger()) {
            MainUtils.testInicialization("TESTAPI_TC005", "Realizamos la petición delete y obtiene un código HTTP 204");
        }
        testId = MainUtils.testId;
        MainUtils.printReportMessage("-----------------------------------");
        MainUtils.printReportMessage("[FRAMEWORK] Comienzo de Test Case: " + testId);
        /*****************************************/
        /* Métodos de la prueba */
        /*****************************************/
        TESTAPI_TC005_step01();
    }

    /**************************************************
     * 
     * Pasos de Pruebas
     * 
     **************************************************/

    /**************************************************
     * 
     * PASOS TESTAPI_TC001
     * 
     **************************************************/

    public static void TESTAPI_TC001_step01() throws InterruptedException, Exception {
        MainUtils.newStep(1, "Realizar una petición GET a la página de demo https://reqres.in/api/users",
                "Obtenemos una lista de usuarios y un código http 200");
        /* Acciones -> */

        String status = APIUtilities.obtainJSONData(MainUtils.stepTest() + ".status");
        String responseBody = APIUtilities.getRequestBody(MainUtils.stepTest(), status);

        /* Condiciones -> */
        APIUtilities.checkStatusResponse(status);
        MainUtils.printReportMessage("Vemos el body de la respuesta: " + responseBody);
    }

    /**************************************************
     * 
     * PASOS TESTAPI_TC002
     * 
     **************************************************/

    public static void TESTAPI_TC002_step01() throws InterruptedException, Exception {
        MainUtils.newStep(1,
                "Realizar una petición POST a la página de demo https://reqres.in/api/users para crear un usuario",
                "Obtenemos al usuario creado con un id, la fecha de creacion y un código http 201");
        /* Acciones -> */
        String status = APIUtilities.obtainJSONData(MainUtils.stepTest() + ".status");
        String responseBody = APIUtilities.postRequestBody(MainUtils.stepTest(), status);

        /* Condiciones -> */
        APIUtilities.checkStatusResponse(status);
        MainUtils.printReportMessage("Vemos el body de la respuesta: " + responseBody);
    }

    /**************************************************
     * 
     * PASOS TESTAPI_TC003
     * 
     **************************************************/

    public static void TESTAPI_TC003_step01() throws InterruptedException, Exception {
        MainUtils.newStep(1,
                "Realizar una petición PUT a la página de demo https://reqres.in/api/users/2 para actualizar el usuario",
                "Obtenemos la fecha de actualizacion y un código http 200");
        /* Acciones -> */
        String status = APIUtilities.obtainJSONData(MainUtils.stepTest() + ".status");
        String responseBody = APIUtilities.putRequestBody(MainUtils.stepTest(), status);

        /* Condiciones -> */
        APIUtilities.checkStatusResponse(status);
        MainUtils.printReportMessage("Vemos el body de la respuesta: " + responseBody);
    }

    /**************************************************
     * 
     * PASOS TESTAPI_TC004
     * 
     **************************************************/

    public static void TESTAPI_TC004_step01() throws InterruptedException, Exception {
        MainUtils.newStep(1,
                "Realizar una petición PATCH a la página de demo https://reqres.in/api/users/2 para actualizar el usuario",
                "Obtenemos la fecha de actualizacion y un código http 200");
        /* Acciones -> */
        String status = APIUtilities.obtainJSONData(MainUtils.stepTest() + ".status");
        String responseBody = APIUtilities.patchRequestBody(MainUtils.stepTest(), status);

        /* Condiciones -> */
        APIUtilities.checkStatusResponse(status);
        MainUtils.printReportMessage("Vemos el body de la respuesta: " + responseBody);
    }

    /**************************************************
     * 
     * PASOS TESTAPI_TC005
     * 
     **************************************************/

    public static void TESTAPI_TC005_step01() throws InterruptedException, Exception {
        MainUtils.newStep(1,
                "Realizar una petición DELETE a la página de demo 'https://reqres.in/api/users/2' para eliminar el usuario",
                "Obtenemos un código http 204");
        /* Acciones -> */
        String status = APIUtilities.obtainJSONData(MainUtils.stepTest() + ".status");
        APIUtilities.deleteRequest(MainUtils.stepTest(), status);

        /* Condiciones -> */
        APIUtilities.checkStatusResponse(status);
    }

}