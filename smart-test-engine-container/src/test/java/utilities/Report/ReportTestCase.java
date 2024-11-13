package utilities.Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ReportTestCase {
        private static String TestSuite;
        private static String TestId;
        private static String TestType;
        private static String TestDescription;
        private static String ExecutionTime;
        private static Integer ActionsExecuted;
        private static Integer ActionsRetries;
        private static Integer ConditionsExecuted;
        private static Integer ConditionsRetries;
        private static String StepsExecuted;
        private static String Status;
        private static List<PreconditionSteps> PreconditionSteps;
        private static List<Steps> Steps;

        public static void createHTML(String pathReport, String caseName) {
                // Inicializamos HTMLContent dentro del método para que sea único para cada
                // llamada
                StringBuilder HTMLContent = new StringBuilder();

                String pathLogos = "../../../../../src/test/resources/report";
                String HTMLpath = "";

                try {
                        ObjectMapper objectMapper = new ObjectMapper()
                                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        System.out.println("vemos la ruta del reporte: " + pathReport + "/report.json");

                        Report report = objectMapper.readValue(new File(pathReport + "/report.json"), Report.class);

                        // Obtiene los datos del JSON usando bucles para cada nodo
                        try {
                                for (TestSuite testSuite : report.getTestSuites()) {
                                        try {
                                                for (Test test : testSuite.getTests()) {
                                                        if ((test.getTestId()).equals(caseName)) {
                                                                TestSuite = testSuite.getTestSuiteName();
                                                                TestType = test.getTestType();
                                                                TestId = test.getTestId();
                                                                ExecutionTime = test.getExecutionTime();
                                                                ActionsExecuted = test.getActionExecuted();
                                                                ActionsRetries = test.getActionRetries();
                                                                ConditionsExecuted = test.getConditionExecuted();
                                                                ConditionsRetries = test.getConditionRetries();
                                                                StepsExecuted = testSuite.getTestsExecuted();
                                                                Status = test.getStatus();
                                                                TestDescription = test.getTestDescription();
                                                                PreconditionSteps = test.getPreconditionSteps();
                                                                System.out.println(
                                                                                "PRECONDITIONS" + PreconditionSteps);
                                                                Steps = test.getSteps();
                                                                System.out.println("PASOS" + Steps);

                                                                File destinationFolder = new File(
                                                                                "./" + pathReport + "/report.json");
                                                                if (!destinationFolder.exists()) {
                                                                        System.out.println("[ERROR] La carpeta "
                                                                                        + destinationFolder
                                                                                        + " no existe.");
                                                                }

                                                                HTMLContent.append("<!DOCTYPE html>\n");
                                                                HTMLContent.append("<html lang=\"en\">\n");
                                                                HTMLContent.append("<html>\n");
                                                                HTMLContent.append("<head>\n");
                                                                HTMLContent.append(
                                                                                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">\n");

                                                                HTMLContent.append("    <style>\n");
                                                                HTMLContent.append("        body {\n");
                                                                HTMLContent.append(
                                                                                "          background: linear-gradient(to bottom, #141d33, #ffffff);\n");
                                                                HTMLContent.append(
                                                                                "          background-attachment: fixed;\n");
                                                                HTMLContent.append("          margin: 0;\n");
                                                                HTMLContent.append("          padding: 0;\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Arial, Helvetica, sans-serif;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        html,\n");
                                                                HTMLContent.append("        body {\n");
                                                                HTMLContent.append("          margin: 0;\n");
                                                                HTMLContent.append("          height: 100%;\n");
                                                                HTMLContent.append("          overflow: hidden;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .nav-bar {\n");
                                                                HTMLContent.append(
                                                                                "          justify-content: space-between;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #232d4b;\n");
                                                                HTMLContent.append("          display: inline-flex;\n");
                                                                HTMLContent.append("          width: 100%;\n");
                                                                HTMLContent.append("          height: 70px;\n");
                                                                HTMLContent.append("          z-index: 999;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .nav-buttons a {\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Arial, Helvetica, sans-serif;\n");
                                                                HTMLContent.append(
                                                                                "          text-decoration: none;\n");
                                                                HTMLContent.append("          font-size: 15px;\n");
                                                                HTMLContent.append(
                                                                                "          display: inline-block;\n");
                                                                HTMLContent.append("          margin: 10px;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .nav-buttons a:hover {\n");
                                                                HTMLContent.append(
                                                                                "          text-decoration: underline;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .top-panel {\n");
                                                                HTMLContent.append("          margin-top: 10px;\n");
                                                                HTMLContent.append("          width: 100%;\n");
                                                                HTMLContent.append(
                                                                                "          justify-content: center;\n");
                                                                HTMLContent.append(
                                                                                "          display: inline-block;\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Arial, Helvetica, sans-serif;\n");
                                                                HTMLContent.append("          text-align: center;\n");
                                                                HTMLContent.append(
                                                                                "          align-content: center;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #resume-column-chart {\n");
                                                                HTMLContent.append("          width: 100%;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #leftPanelHeader {\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Arial, Helvetica, sans-serif;\n");
                                                                HTMLContent.append("          font-size: 18px;\n");
                                                                HTMLContent.append("          color: #fff;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #232d4b;\n");
                                                                HTMLContent.append("          line-height: 1.2;\n");
                                                                HTMLContent.append("          font-weight: unset;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #rightPanelHeaderDiv {\n");
                                                                HTMLContent.append("          position: sticky;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #stackedchart {\n");
                                                                HTMLContent.append("          height: 35%;\n");
                                                                HTMLContent.append("          width: 99%;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #columnchart {\n");
                                                                HTMLContent.append("          height: 35%;\n");
                                                                HTMLContent.append("          width: 99%;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .resume-panel {\n");
                                                                HTMLContent.append("          margin: auto;\n");
                                                                HTMLContent.append("          width: 94.6%;\n");
                                                                HTMLContent.append("          border-radius: 20px;\n");
                                                                HTMLContent.append("          padding-bottom: 0.5%;\n");
                                                                HTMLContent.append("          padding-top: 0.5%;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #232f4d;\n");
                                                                HTMLContent.append("          padding-right: 0.5vw;\n");
                                                                HTMLContent.append("          margin-left: 2.2vw;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .sticky {\n");
                                                                HTMLContent.append("          position: sticky;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .top-resume-panel {\n");
                                                                HTMLContent.append("          padding: 0;\n");

                                                                HTMLContent.append("          font-size: 1.5rem;\n");
                                                                HTMLContent.append("          display: flex;\n");
                                                                HTMLContent.append(
                                                                                "          justify-content: space-between;\n");
                                                                HTMLContent.append("          align-items: center;\n");
                                                                HTMLContent.append("          flex-wrap: wrap;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .GifNoExiste {\n");
                                                                HTMLContent.append("          text-align: center;\n");
                                                                HTMLContent.append("          font-size: 2rem;\n");
                                                                HTMLContent.append("          width: 100%;\n");
                                                                HTMLContent.append(
                                                                                "          font-family: \"Trebuchet MS\", sans-serif;\n");
                                                                HTMLContent.append("          color: #005573;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .top-resume-panel div {\n");
                                                                HTMLContent.append("          display: flex;\n");
                                                                HTMLContent.append("          align-items: center;\n");
                                                                HTMLContent.append("           margin: 5px 20px;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .div-Panels {\n");
                                                                HTMLContent.append("          display: flex;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append(
                                                                                "        @media (max-width: 1300px) {\n");
                                                                HTMLContent.append("          .cabecera {\n");
                                                                HTMLContent.append(
                                                                                "            font-size: 1rem !important;\n");
                                                                HTMLContent.append("            margin-right: 0;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("          .butonGIF {\n");
                                                                HTMLContent.append(
                                                                                "            min-width: fit-content;\n");
                                                                HTMLContent.append("            width: 10vw;\n");
                                                                HTMLContent.append(
                                                                                "            font-size: 1.15rem !important;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("          .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "            font-size: 1rem !important;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append(
                                                                                "        @media (max-width: 800px) {\n");
                                                                HTMLContent.append("          .cabecera {\n");
                                                                HTMLContent.append("            font-size: 0.75rem;\n");
                                                                HTMLContent.append("            margin-right: 0;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("          .butonGIF {\n");
                                                                HTMLContent.append(
                                                                                "            min-width: fit-content;\n");
                                                                HTMLContent.append("            width: 10vw;\n");
                                                                HTMLContent.append("            font-size: 1rem;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("          .dato-cabecera {\n");
                                                                HTMLContent.append("            font-size: 0.75rem;\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .test {\n");
                                                                HTMLContent.append("          cursor: pointer;\n");
                                                                HTMLContent.append("        }\n");
                                                                HTMLContent.append(
                                                                                "        .right-panel {\n  vertical-align: middle;\r\n"
                                                                                                + //
                                                                                                "  margin-left: 1%;\r\n"
                                                                                                + //
                                                                                                "  margin-right: 2.5%;\r\n"
                                                                                                + //
                                                                                                "  width: 37%;\r\n" + //
                                                                                                "  height: 65vh;\r\n" + //
                                                                                                "  border-radius: 15px;\r\n"
                                                                                                + //
                                                                                                "  background-color: white;\r\n"
                                                                                                + //
                                                                                                "  display: flex;\r\n" + //
                                                                                                "  flex-direction: column;\r\n"
                                                                                                + //
                                                                                                "  padding: 10px;\r\n" + //
                                                                                                "  box-sizing: border-box;}\n");
                                                                HTMLContent.append("        .botonAgrandar {\n");
                                                                HTMLContent.append("          display: flex;\n");
                                                                HTMLContent.append(
                                                                                "          flex-direction: row; \n");
                                                                HTMLContent.append(
                                                                                "           gap: 10px;\n");
                                                                HTMLContent.append(
                                                                                "           margin-top: auto;\n");
                                                                HTMLContent.append(
                                                                                "          justify-content: center;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .Agrandar2 {\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Poppins, sans-serif;\n");
                                                                HTMLContent.append("         width: 110px;\n");
                                                                HTMLContent.append("         height: 40px;\n");
                                                                HTMLContent.append("          font-size: 1.2rem;\n");
                                                                HTMLContent.append("          font-weight: bold;\n");
                                                                HTMLContent.append(
                                                                                "          border: 2px solid #c33430;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #c33430;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("          border-radius: 25px;\n");
                                                                HTMLContent.append(
                                                                                "          transition: 0.3s ease;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .Agrandar2:hover {\n");
                                                                HTMLContent.append("          cursor: pointer;\n");
                                                                HTMLContent.append(
                                                                                "          border: 2px solid #c54542;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #c54542;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .imagen {\n");
                                                                HTMLContent.append("          max-width: 95%;\n");
                                                                HTMLContent.append("          max-height: 85%;\n");
                                                                HTMLContent.append("          object-fit: cover;\n");
                                                                HTMLContent.append("          height: min-content;\n");
                                                                HTMLContent.append("          margin: auto;\n");
                                                                HTMLContent.append("          justify-self: center;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .cabecera {\n");
                                                                HTMLContent.append("          font-size: 1.75rem;\n");
                                                                HTMLContent.append("          font-weight: bold;\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Poppins, sans-serif !important;\n");
                                                                HTMLContent.append("          color: #028075;\n");
                                                                HTMLContent.append(
                                                                                "          font-stretch: ultra-condensed;\n");
                                                                HTMLContent.append("          margin-right: 0.75vw;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .butonGIF {\n");
                                                                HTMLContent.append(
                                                                                "          font-family: Poppins, sans-serif;\n");
                                                                HTMLContent.append("          width: 18vw;\n");
                                                                HTMLContent.append("          height: 50px;\n");
                                                                HTMLContent.append("          font-size: 1.25rem;\n");
                                                                HTMLContent.append("          font-weight: bold;\n");
                                                                HTMLContent.append(
                                                                                "          border: 2px solid #c33430;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #c33430;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("          border-radius: 25px;\n");
                                                                HTMLContent.append(
                                                                                "          transition: 0.5s ease;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #current-success-rate {\n");
                                                                HTMLContent.append("          margin-right: 5px;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .butonGIF:hover {\n");
                                                                HTMLContent.append("          cursor: pointer;\n");
                                                                HTMLContent.append(
                                                                                "          border: 2px solid #c54542;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #c54542;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .dato-cabecera {\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        @keyframes rotar {\n");
                                                                HTMLContent.append("          from {\n");
                                                                HTMLContent.append(
                                                                                "            transform: rotate(0deg);\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("          to {\n");
                                                                HTMLContent.append(
                                                                                "            transform: rotate(360deg);\n");
                                                                HTMLContent.append("          }\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .carga {\n");
                                                                HTMLContent.append(
                                                                                "          width: 20% !important;\n");
                                                                HTMLContent.append(
                                                                                "          animation: rotar 3s linear infinite;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .active {\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #232d4b;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("          transition: 1s ease;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .contenedorImagen {\n");
                                                                HTMLContent.append("          height: 90%;\n");
                                                                HTMLContent.append("          width: 100%;\n");
                                                                HTMLContent.append("          display: flex;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .iframe {\n");
                                                                HTMLContent.append(
                                                                                "          border: solid 2px #232d4b;\n");
                                                                HTMLContent.append("          margin: auto;\n");
                                                                HTMLContent.append("          width: 95%;\n");
                                                                HTMLContent.append(
                                                                                "          box-shadow: 5px 5px 15px #141d33;\n");
                                                                HTMLContent.append("        }\n");
                                                                HTMLContent.append("        .test-description {\n");
                                                                HTMLContent.append("          flex: 0 1 auto;\n");
                                                                HTMLContent.append(
                                                                                "          height: 100px; /* Ajusta según sea necesario */\n");
                                                                HTMLContent.append("          padding: 10px;\n");
                                                                HTMLContent.append(
                                                                                "          box-sizing: border-box;\n");
                                                                HTMLContent.append("          overflow-y: auto;\n");
                                                                HTMLContent.append(
                                                                                "          background-color: #232d4b;\n");
                                                                HTMLContent.append("          border-radius: 20px;\n");
                                                                HTMLContent.append("          display: flex;\n");

                                                                HTMLContent.append(
                                                                                "          justify-content: center; /* Centra verticalmente */\n");
                                                                HTMLContent.append(
                                                                                "          align-items: center; /* Centra horizontalmente */\n");
                                                                HTMLContent.append(
                                                                                "          text-align: center; /* Alinea el texto al centro */\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        .description {\n");
                                                                HTMLContent.append("          font-size: 1.75rem;\n");
                                                                HTMLContent.append("          font-weight: bold;\n");
                                                                HTMLContent.append(
                                                                                "          font-family: 'Poppins', sans-serif;\n");
                                                                HTMLContent.append("          color: #028075;\n");
                                                                HTMLContent.append(
                                                                                "          font-stretch: ultra-condensed;\n");
                                                                HTMLContent.append(
                                                                                "          margin-bottom: 0.10vw; /* Espacio debajo del primer span */\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append("        #text-description {\n");
                                                                HTMLContent.append("          font-size: 1rem;\n");
                                                                HTMLContent.append("          color: white;\n");
                                                                HTMLContent.append("        }\n");

                                                                HTMLContent.append(
                                                                                "/* Estilo para la lista */\n");
                                                                HTMLContent.append(
                                                                                "ul { list-style-type: none; margin: 0; padding: 0; }\n");
                                                                HTMLContent.append(
                                                                                "li { background-color: #f9f9f9; border-color: #999999; margin: 2px 0; cursor: pointer; padding: 5px 20px; position: relative; transition: background-color 0.3s, border-color 0.3s; }\n");
                                                                HTMLContent.append(
                                                                                "li:hover { background-color: #f9f9f9; border-color: #999999; }\n");
                                                                HTMLContent.append(
                                                                                "li::before { content: '\\f078'; font-family: 'FontAwesome'; position: absolute; left: 0; top: 9px; transform: translateY(0); font-size: 1.25rem; color: #232d4b; transition: transform 0.3s, color 0.3s; }\n");
                                                                HTMLContent.append(
                                                                                "li.open::before { content: '\\f077'; transform: translateY(0) rotate(90deg); color: #026d5b; }\n");
                                                                HTMLContent.append(
                                                                                "ul ul { padding-left: 20px; margin: 0; display: none; }\n");
                                                                HTMLContent.append(
                                                                                "li.open > ul { display: block; }\n");
                                                                HTMLContent.append(
                                                                                "ul ul ul { padding-left: 20px; }\n");
                                                                HTMLContent.append(
                                                                                "ul ul ul ul { cursor: text; }\n");
                                                                HTMLContent.append(
                                                                                "li::after { content: ''; position: absolute; left: -10px; top: 0; bottom: 0; width: 2px; background-color: #007bff; z-index: -1; }\n");
                                                                HTMLContent.append(
                                                                                "li > ul::before { content: ''; position: absolute; left: -10px; top: 9px; width: 2px; height: 100%; background-color: #007bff; z-index: -1; }\n");
                                                                HTMLContent.append(
                                                                                "li.open > ul::before { display: none; }\n");
                                                                HTMLContent.append(
                                                                                "ul ul ul ul:last-of-type > li::before { content: ''; cursor: text; }\n");
                                                                HTMLContent.append(
                                                                                "h2, h4 { font-family: 'Arial', sans-serif; color: #333; margin: 0; }\n");
                                                                HTMLContent.append(
                                                                                "h4 { margin-top: 5px; }\n");
                                                                HTMLContent.append(
                                                                                ".botones-expandir { display: flex; justify-content: center; margin-bottom: 10px; }\n");
                                                                HTMLContent.append(
                                                                                ".expandir, .colapsar { font-family: Arial, sans-serif; width: 30px; height: 30px; font-size: 1rem; font-weight: bold; background-color: #007bff; color: white; margin: 0 3px; cursor: pointer; transition: background-color 0.3s, border-color 0.3s; border-radius: 50%; display: flex; align-items: center; justify-content: center; }\n");
                                                                HTMLContent.append(
                                                                                ".expandir:hover, .colapsar:hover { background-color: #0056b3; border-color: #004494; }\n");
                                                                HTMLContent.append(
                                                                                ".expandir:active, .colapsar:active { background-color: #004494; border-color: #003366; }\n");
                                                                HTMLContent.append(
                                                                                ".left-panel { margin-left: 2%; background-color: #f9f9f9; border: 1px solid #ddd; border-radius: 8px; width: 60%; height: 65vh; overflow-y: auto; display: flex; flex-direction: column; }\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .tree-container { flex: 1; overflow-y: auto; }\n");
                                                                HTMLContent.append(
                                                                                ".left-panel ul { padding: 5px; margin: 0; }\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .botones-expandir { display: flex; justify-content: flex-end; padding: 5px; gap:0; margin-right: 7px;  flex-wrap: nowrap; }\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .botones-expandir2 {display: flex; justify-content: flex-end; padding: 5px; }\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .expandir { font-family: Arial, sans-serif; width: 110px; height: 40px; font-size: 1.2rem; font-weight: bold; border: 1px solid #232d4b; background-color: #232d4b; color: white; border-radius: 25px; margin-left: 5px; margin-right: 20px;  cursor: pointer; transition: background-color 0.3s, border-color 0.3s;}\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .colapsar { font-family: Arial, sans-serif; width: 40px; height: 40px; font-size: 1.2rem; font-weight: bold; border: 1px solid #232d4b; background-color: #232d4b; color: white; border-radius: 50%; margin-left: 5px; margin-right: 100px;  cursor: pointer; transition: background-color 0.3s, border-color 0.3s;}\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .Agrandar2{margin-left: 40px; margin-right: 20px;width: 130px; height: 40px;}\n");
                                                                HTMLContent.append(
                                                                                ".right-panel .expandir:hover, .right-panel .colapsar:hover { background-color: #003366; }\n");
                                                                HTMLContent.append(
                                                                                "/* Zoom al 125% (Estilo por defecto) */\n");
                                                                HTMLContent.append(
                                                                                "@media screen and (min-resolution: 120dpi) {\n");
                                                                HTMLContent.append("    .right-panel .expandir, \n");
                                                                HTMLContent.append("    .right-panel .Agrandar2 {\n");
                                                                HTMLContent.append("        width: 100px;\n");
                                                                HTMLContent.append("        height: 35px;\n");
                                                                HTMLContent.append("        font-size: 1.1rem;\n");
                                                                HTMLContent.append("        margin-left: 3px;\n");
                                                                HTMLContent.append("        margin-right: 15px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .right-panel .colapsar {\n");
                                                                HTMLContent.append("        width: 35px;\n");
                                                                HTMLContent.append("        height: 35px;\n");
                                                                HTMLContent.append("        font-size: 1.1rem;\n");
                                                                HTMLContent.append("        margin-left: 3px;\n");
                                                                HTMLContent.append("        margin-right: 70px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .resume-panel {\n");
                                                                HTMLContent.append("        width: 95%;\n");
                                                                HTMLContent.append("        padding-right: 1vw;\n");
                                                                HTMLContent.append("        margin-left: 1.5vw;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .cabecera, .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.5rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .butonGIF {\n");
                                                                HTMLContent.append("        width: 12vw;\n");
                                                                HTMLContent.append("        height: 45px;\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.15rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("}\n");

                                                                HTMLContent.append("/* Zoom al 100% */\n");
                                                                HTMLContent.append(
                                                                                "@media screen and (max-resolution: 110dpi) {\n");
                                                                HTMLContent.append("    .right-panel .expandir, \n");
                                                                HTMLContent.append("    .right-panel .Agrandar2 {\n");
                                                                HTMLContent.append("        width: 110px;\n");
                                                                HTMLContent.append("        height: 38px;\n");
                                                                HTMLContent.append("        font-size: 1.2rem;\n");
                                                                HTMLContent.append("        margin-left: 5px;\n");
                                                                HTMLContent.append("        margin-right: 20px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .right-panel .colapsar {\n");
                                                                HTMLContent.append("        width: 38px;\n");
                                                                HTMLContent.append("        height: 38px;\n");
                                                                HTMLContent.append("        font-size: 1.2rem;\n");
                                                                HTMLContent.append("        margin-left: 5px;\n");
                                                                HTMLContent.append("        margin-right: 75px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .resume-panel {\n");
                                                                HTMLContent.append("        width: 96%;\n");
                                                                HTMLContent.append("        padding-right: 1vw;\n");
                                                                HTMLContent.append("        margin-left: 1.2vw;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .cabecera, .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.6rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .butonGIF {\n");
                                                                HTMLContent.append("        width: 16vw;\n");
                                                                HTMLContent.append("        height: 48px;\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.2rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("}\n");

                                                                HTMLContent.append("/* Zoom al 150% */\n");
                                                                HTMLContent.append(
                                                                                "@media screen and (min-resolution: 150dpi) and (max-resolution: 180dpi) {\n");
                                                                HTMLContent.append("    .right-panel .expandir, \n");
                                                                HTMLContent.append("    .right-panel .Agrandar2 {\n");
                                                                HTMLContent.append("        width: 90px;\n");
                                                                HTMLContent.append("        height: 30px;\n");
                                                                HTMLContent.append("        font-size: 1rem;\n");
                                                                HTMLContent.append("        margin-left: 2px;\n");
                                                                HTMLContent.append("        margin-right: 10px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .right-panel .colapsar {\n");
                                                                HTMLContent.append("        width: 30px;\n");
                                                                HTMLContent.append("        height: 30px;\n");
                                                                HTMLContent.append("        font-size: 1rem;\n");
                                                                HTMLContent.append("        margin-left: 2px;\n");
                                                                HTMLContent.append("        margin-right: 50px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .resume-panel {\n");
                                                                HTMLContent.append("        width: 95%;\n");
                                                                HTMLContent.append("        padding-right: 0.7vw;\n");
                                                                HTMLContent.append("        margin-left: 1.5vw;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .cabecera, .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.3rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .butonGIF {\n");
                                                                HTMLContent.append("        width: 14vw;\n");
                                                                HTMLContent.append("        height: 40px;\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.1rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("}\n");

                                                                HTMLContent.append(
                                                                                "/* Adaptaciones adicionales para resolución Full HD (1920x1080) */\n");
                                                                HTMLContent.append(
                                                                                "@media screen and (min-width: 1920px) and (max-width: 1920px) and \n");
                                                                HTMLContent.append(
                                                                                "       (min-height: 1080px) and (max-height: 1080px) {\n");
                                                                HTMLContent.append("    .right-panel .expandir, \n");
                                                                HTMLContent.append("    .right-panel .Agrandar2 {\n");
                                                                HTMLContent.append("        width: 100px;\n");
                                                                HTMLContent.append("        height: 35px;\n");
                                                                HTMLContent.append("        font-size: 1.1rem;\n");
                                                                HTMLContent.append("        margin-left: 3px;\n");
                                                                HTMLContent.append("        margin-right: 15px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .right-panel .colapsar {\n");
                                                                HTMLContent.append("        width: 35px;\n");
                                                                HTMLContent.append("        height: 35px;\n");
                                                                HTMLContent.append("        font-size: 1.1rem;\n");
                                                                HTMLContent.append("        margin-left: 3px;\n");
                                                                HTMLContent.append("        margin-right: 70px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .resume-panel {\n");
                                                                HTMLContent.append("        width: 95%;\n");
                                                                HTMLContent.append("        padding-right: 1vw;\n");
                                                                HTMLContent.append("        margin-left: 1.5vw;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .cabecera, .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.5rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .butonGIF {\n");
                                                                HTMLContent.append("        width: 15vw;\n");
                                                                HTMLContent.append("        height: 45px;\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.15rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("}\n");

                                                                HTMLContent.append(
                                                                                "/* Adaptaciones adicionales para resolución 1280x720 */\n");
                                                                HTMLContent.append(
                                                                                "@media screen and (max-width: 1280px) and (max-height: 720px) {\n");
                                                                HTMLContent.append("    .right-panel .expandir, \n");
                                                                HTMLContent.append("    .right-panel .Agrandar2 {\n");
                                                                HTMLContent.append("        width: 90px;\n");
                                                                HTMLContent.append("        height: 30px;\n");
                                                                HTMLContent.append("        font-size: 1rem;\n");
                                                                HTMLContent.append("        margin-left: 2px;\n");
                                                                HTMLContent.append("        margin-right: 10px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .right-panel .colapsar {\n");
                                                                HTMLContent.append("        width: 30px;\n");
                                                                HTMLContent.append("        height: 30px;\n");
                                                                HTMLContent.append("        font-size: 1rem;\n");
                                                                HTMLContent.append("        margin-left: 2px;\n");
                                                                HTMLContent.append("        margin-right: 50px;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .resume-panel {\n");
                                                                HTMLContent.append("        width: 95%;\n");
                                                                HTMLContent.append("        padding-right: 0.7vw;\n");
                                                                HTMLContent.append("        margin-left: 1.5vw;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .cabecera, .dato-cabecera {\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.3rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("    .butonGIF {\n");
                                                                HTMLContent.append("        width: 7vw;\n");
                                                                HTMLContent.append("        height: 40px;\n");
                                                                HTMLContent.append(
                                                                                "        font-size: 1.1rem !important;\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append("}\n");
                                                                HTMLContent.append("    </style>\n");
                                                                HTMLContent.append("</head>\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("<body>\n");
                                                                HTMLContent.append("<div class=\"body-panel\">\n");
                                                                HTMLContent.append(
                                                                                "<nav id=\"headerId\" class=\"nav-bar\"\n>");
                                                                HTMLContent.append(
                                                                                "<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAC3EAAANSCAYAAABxj6aRAAAACXBIWXMAAC4jAAAuIwF4pT92AAAOZmlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczppbGx1c3RyYXRvcj0iaHR0cDovL25zLmFkb2JlLmNvbS9pbGx1c3RyYXRvci8xLjAvIiB4bWxuczp4bXBUUGc9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC90L3BnLyIgeG1sbnM6c3REaW09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9EaW1lbnNpb25zIyIgeG1sbnM6eG1wRz0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL2cvIiB4bWxuczpwZGY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGRmLzEuMy8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDgtMDNUMTI6MDU6MzcrMDI6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDIwLTA4LTAzVDEyOjA1OjM3KzAyOjAwIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wNy0zMVQxNzo0MTo1OSswMjowMCIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBJbGx1c3RyYXRvciBDUzYgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NTdhOTQ0MmQtY2Q5Ni00ODk0LThhNTItYmMxYmQyODEyYjA3IiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6ZTdkYjg2Y2EtYTJiMy00NjRiLWFiNTQtYzkxOTkwOGY0ZjI4IiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InV1aWQ6NUQyMDg5MjQ5M0JGREIxMTkxNEE4NTkwRDMxNTA4QzgiIHhtcE1NOlJlbmRpdGlvbkNsYXNzPSJwcm9vZjpwZGYiIGlsbHVzdHJhdG9yOlR5cGU9IkRvY3VtZW50IiBpbGx1c3RyYXRvcjpTdGFydHVwUHJvZmlsZT0iUHJpbnQiIHhtcFRQZzpIYXNWaXNpYmxlT3ZlcnByaW50PSJGYWxzZSIgeG1wVFBnOkhhc1Zpc2libGVUcmFuc3BhcmVuY3k9IkZhbHNlIiB4bXBUUGc6TlBhZ2VzPSIxIiBwZGY6UHJvZHVjZXI9IkFkb2JlIFBERiBsaWJyYXJ5IDEwLjAxIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIj4gPGRjOnRpdGxlPiA8cmRmOkFsdD4gPHJkZjpsaSB4bWw6bGFuZz0ieC1kZWZhdWx0Ij5RX0lORVRVTTwvcmRmOmxpPiA8L3JkZjpBbHQ+IDwvZGM6dGl0bGU+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjliYThhMjg2LTA3NjAtNDhmMC04NWNhLTBjYmY5N2VmNzNiNyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5YmE4YTI4Ni0wNzYwLTQ4ZjAtODVjYS0wY2JmOTdlZjczYjciIHN0UmVmOm9yaWdpbmFsRG9jdW1lbnRJRD0idXVpZDo1RDIwODkyNDkzQkZEQjExOTE0QTg1OTBEMzE1MDhDOCIgc3RSZWY6cmVuZGl0aW9uQ2xhc3M9InByb29mOnBkZiIvPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDowMjgwMTE3NDA3MjA2ODExODNEMTlGNkQ5MUExMTY0NCIgc3RFdnQ6d2hlbj0iMjAyMC0wNy0zMFQxNjozMDowOSswMjowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgSWxsdXN0cmF0b3IgQ1M2IChNYWNpbnRvc2gpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDowNzgwMTE3NDA3MjA2ODExODNEMUZBMzcxQjgzNEYyQiIgc3RFdnQ6d2hlbj0iMjAyMC0wNy0zMVQxNzo0MTo1OSswMjowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgSWxsdXN0cmF0b3IgQ1M2IChNYWNpbnRvc2gpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjb252ZXJ0ZWQiIHN0RXZ0OnBhcmFtZXRlcnM9ImZyb20gYXBwbGljYXRpb24vcGRmIHRvIGFwcGxpY2F0aW9uL3ZuZC5hZG9iZS5waG90b3Nob3AiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjliYThhMjg2LTA3NjAtNDhmMC04NWNhLTBjYmY5N2VmNzNiNyIgc3RFdnQ6d2hlbj0iMjAyMC0wOC0wM1QxMjowNTozNyswMjowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKE1hY2ludG9zaCkiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBhcHBsaWNhdGlvbi9wZGYgdG8gaW1hZ2UvcG5nIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJkZXJpdmVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJjb252ZXJ0ZWQgZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NTdhOTQ0MmQtY2Q5Ni00ODk0LThhNTItYmMxYmQyODEyYjA3IiBzdEV2dDp3aGVuPSIyMDIwLTA4LTAzVDEyOjA1OjM3KzAyOjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoTWFjaW50b3NoKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcFRQZzpNYXhQYWdlU2l6ZSBzdERpbTp3PSIyOTYuOTk5OTU5IiBzdERpbTpoPSIyMTAuMDAxNjUyIiBzdERpbTp1bml0PSJNaWxsaW1ldGVycyIvPiA8eG1wVFBnOlBsYXRlTmFtZXM+IDxyZGY6U2VxPiA8cmRmOmxpPkN5YW48L3JkZjpsaT4gPHJkZjpsaT5ZZWxsb3c8L3JkZjpsaT4gPC9yZGY6U2VxPiA8L3htcFRQZzpQbGF0ZU5hbWVzPiA8eG1wVFBnOlN3YXRjaEdyb3Vwcz4gPHJkZjpTZXE+IDxyZGY6bGk+IDxyZGY6RGVzY3JpcHRpb24geG1wRzpncm91cE5hbWU9Ikdyb3VwZSBkZSBudWFuY2VzIHBhciBkw6lmYXV0IiB4bXBHOmdyb3VwVHlwZT0iMCI+IDx4bXBHOkNvbG9yYW50cz4gPHJkZjpTZXE+IDxyZGY6bGkgeG1wRzpzd2F0Y2hOYW1lPSJSPTAgVj0xNzAgQj0xNTUiIHhtcEc6dHlwZT0iUFJPQ0VTUyIgeG1wRzp0aW50PSIxMDAuMDAwMDAwIiB4bXBHOm1vZGU9IlJHQiIgeG1wRzpyZWQ9IjAiIHhtcEc6Z3JlZW49IjE3MCIgeG1wRzpibHVlPSIxNTUiLz4gPC9yZGY6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 \"\n>");
                                                                HTMLContent.append(
                                                                                "<img src=\"data:image/png;base64,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\" alt=\"MiimagenSVG\" style='border-radius:8px;margin:0.5%;margin-right:8%'\">\n");
                                                                HTMLContent.append(
                                                                                "<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKUAAABwCAYAAAB/2LBGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAF6SURBVHhe7d0hDoNQEEBB2qtgMD1J759gepbWIGoIBsqjmTF8iXhZt9nb+Hi+Bza9pml57Wuc5+W1r6v977f78oUMUZIjSnJESY4oyRElOaIkR5TkiJIcUZIjSnJESY4oyRElOaIkR5TkiJIcUZIjSnJESY4oybHNeLIrbx0exaQkR5TkiJIcUZIjSnJESY4oyRElOaIkR5TkiJIcUZIjSnJESY4oyRElOaIkR5TkiJIcUZIjSnJsM/4ptxlhR6IkR5TkiJIcUZIjSnJESY4oyRElOaIkR5TkiJIcUZIjSnJESY4oyRElOaIkR5TkiJIcUZJz2jajm4SsMSnJESU5oiRHlOSIkhxRkiNKckRJjijJESU5oiRHlOSIkhxRkiNKckRJjijJESU5oiRHlOSIkpzNbUZbh/yaSUmOKMkRJTmiJEeU5IiSHFGSI0pyREmOKMkRJTmiJEeU5IiSHFGSI0pyREmOKMkRJTmiJEeU5Jx2mxHWmJTEDMMHI2kcdzscSRQAAAAASUVORK5CYII=\" alt=\"pixels\">\n");
                                                                HTMLContent.append("</nav>\n </div>\n");
                                                                HTMLContent.append(
                                                                                "        <div id=\"toop-panel\" class=\"top-panel\">\n");
                                                                HTMLContent.append(
                                                                                "            <div class=\"resume-panel\">\n");
                                                                HTMLContent.append(
                                                                                "                <div class=\"top-resume-panel\">\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append("                        </td>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">TEST SUITE:</span>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"test-suites-involved\" class='dato-cabecera'>"
                                                                                                + TestSuite
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">TEST ID:</span>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"tests-executed\" class='dato-cabecera'>"
                                                                                                + TestId
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">EXECUTION TIME:</span>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"total-execution-time\" class='dato-cabecera'>"
                                                                                                + ExecutionTime
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">ACTION EXECUTED:</span>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"current-success-rate\" class='dato-cabecera'>"
                                                                                                + ActionsExecuted
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append(
                                                                                "                    <button class='butonGIF'>GIF</button>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append(
                                                                                "                <div class=\"top-resume-panel\">\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">ACTION RETRIES:</span>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"last-success-rate\" class='dato-cabecera'>"
                                                                                                + ActionsRetries
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">CONDITIONS EXECUTED:</span><br>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"average-time\" class='dato-cabecera'>"
                                                                                                + ConditionsExecuted
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">CONDITIONS RETRIES:</span><br>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"average-time\" class='dato-cabecera'>"
                                                                                                + ConditionsRetries
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">STEPS EXECUTED:</span><br>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"average-time\" class='dato-cabecera'>"
                                                                                                + StepsExecuted
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                    <div>\n");
                                                                HTMLContent.append(
                                                                                "                        <span class=\"cabecera\">STATUS:</span><br>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"average-time\" class='dato-cabecera'>"
                                                                                                + Status
                                                                                                + "</span>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("        <div>\n");
                                                                HTMLContent.append(
                                                                                "          <span class=\"cabecera\">TEST DESCRIPTION:</span><br>\n");
                                                                HTMLContent.append(
                                                                                "                        <span id=\"average-time\" class='dato-cabecera'>"
                                                                                                + TestDescription
                                                                                                + "</span>\n");
                                                                HTMLContent.append("        </div>\n");

                                                                HTMLContent.append("                </div>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("            </div>\n");
                                                                HTMLContent.append("        <div></div>\n");
                                                                HTMLContent.append("    </div>\n");
                                                                HTMLContent.append("    <br>\n");
                                                                HTMLContent.append("    <div class=\"div-Panels\">\n");
                                                                HTMLContent.append(
                                                                                "<div class=\"left-panel\" style=\"display: flex\">\n");
                                                                ;
                                                                HTMLContent.append(
                                                                                "    <div class=\"tree-container\">\n");
                                                                HTMLContent.append("        <ul id=\"tree\">\n");
                                                                if (!PreconditionSteps.isEmpty()) {
                                                                        // Add precondition steps section
                                                                        HTMLContent.append("            <li>\n");
                                                                        HTMLContent.append(
                                                                                        "                <h2>PRECONDITION STEPS</h2>\n");
                                                                        HTMLContent.append("                <ul>\n");

                                                                        for (PreconditionSteps preconditionStep : PreconditionSteps) {
                                                                                HTMLContent.append(
                                                                                                "                    <li>\n");
                                                                                HTMLContent.append(
                                                                                                "                        <h4>")
                                                                                                .append(preconditionStep
                                                                                                                .getStep())
                                                                                                .append("</h4>\n");
                                                                                HTMLContent.append(
                                                                                                "                        <ul>\n");
                                                                                HTMLContent.append(
                                                                                                "                            <li>\n");
                                                                                HTMLContent.append(
                                                                                                "                                ACTIONS - ")
                                                                                                .append(preconditionStep
                                                                                                                .getActionDescription())
                                                                                                .append("\n");
                                                                                HTMLContent.append(
                                                                                                "                                <ul>\n");

                                                                                for (Evidence action : preconditionStep
                                                                                                .getActions()) {
                                                                                        String fileName = findFile(
                                                                                                        pathReport,
                                                                                                        TestSuite,
                                                                                                        TestId,
                                                                                                        action.getEvidence());

                                                                                        String style = ""; // Inicializa
                                                                                                           // una cadena
                                                                                                           // vacía para
                                                                                                           // el estilo

                                                                                        // Verifica si fileName no
                                                                                        // contiene "TC"
                                                                                        if (!fileName.contains("TC")
                                                                                                        || action.getValue()
                                                                                                                        .contains("KO")
                                                                                                        || action.getValue()
                                                                                                                        .contains("EXCEPTION")) {
                                                                                                style = " style='background-color:  #c54542'";
                                                                                        }

                                                                                        HTMLContent.append(
                                                                                                        "<li data-id='"
                                                                                                                        + fileName
                                                                                                                        + "' onclick='cargarContenido(this.id)'"
                                                                                                                        + style
                                                                                                                        + ">\n");

                                                                                        HTMLContent.append(
                                                                                                        "                                        ")
                                                                                                        .append("[" + action
                                                                                                                        .getEvidence()
                                                                                                                        + "] "

                                                                                                                        + action.getValue()
                                                                                                                                        .replaceAll("\\[.*?\\]",
                                                                                                                                                        "")
                                                                                                                                        .trim())

                                                                                                        .append("\n");
                                                                                        System.out.println(
                                                                                                        "ACTION VALUE" + action
                                                                                                                        .getValue());
                                                                                        HTMLContent.append(
                                                                                                        "                                    </li>\n");
                                                                                }

                                                                                HTMLContent.append(
                                                                                                "                                </ul>\n");
                                                                                HTMLContent.append(
                                                                                                "                            </li>\n");
                                                                                HTMLContent.append(
                                                                                                "                            <li>\n");
                                                                                HTMLContent.append(
                                                                                                "                                CONDITIONS - ")
                                                                                                .append(preconditionStep
                                                                                                                .getConditionDescription())
                                                                                                .append("\n");
                                                                                HTMLContent.append(
                                                                                                "                                <ul>\n");

                                                                                for (Evidence condition : preconditionStep
                                                                                                .getConditions()) {
                                                                                        String fileName = findFile(
                                                                                                        pathReport,
                                                                                                        TestSuite,
                                                                                                        TestId,
                                                                                                        condition.getEvidence());

                                                                                        String style = "";
                                                                                        if (!fileName.contains("TC")
                                                                                                        || condition.getValue()
                                                                                                                        .contains("KO")
                                                                                                        || condition.getValue()
                                                                                                                        .contains("EXCEPTION")) {
                                                                                                style = " style='background-color: #c54542;'";
                                                                                        }

                                                                                        HTMLContent.append(
                                                                                                        "<li data-id='"
                                                                                                                        + fileName
                                                                                                                        + "' onclick='cargarContenido(this.id)'"
                                                                                                                        + style
                                                                                                                        + ">\n");
                                                                                        HTMLContent.append(
                                                                                                        "                                        ")
                                                                                                        .append("[" + condition
                                                                                                                        .getEvidence()
                                                                                                                        + "] "

                                                                                                                        + condition
                                                                                                                                        .getValue()
                                                                                                                                        .replaceAll("\\[.*?\\]",
                                                                                                                                                        "")
                                                                                                                                        .trim())
                                                                                                        .append("\n");
                                                                                        HTMLContent.append(
                                                                                                        "                                    </li>\n");
                                                                                }

                                                                                HTMLContent.append(
                                                                                                "                                </ul>\n");
                                                                                HTMLContent.append(
                                                                                                "                            </li>\n");
                                                                                HTMLContent.append(
                                                                                                "                        </ul>\n");
                                                                                HTMLContent.append(
                                                                                                "                    </li>\n");
                                                                        }

                                                                        HTMLContent.append("                </ul>\n");
                                                                        HTMLContent.append("            </li>\n");
                                                                }
                                                                // Add steps section
                                                                HTMLContent.append("            <li>\n");
                                                                HTMLContent.append("                <h2>STEPS</h2>\n");
                                                                HTMLContent.append("                <ul>\n");

                                                                for (Steps step : Steps) {
                                                                        HTMLContent.append(
                                                                                        "                    <li>\n");
                                                                        HTMLContent.append(
                                                                                        "                        <h4>")
                                                                                        .append(step.getStep())
                                                                                        .append("</h4>\n");
                                                                        HTMLContent.append(
                                                                                        "                        <ul>\n");
                                                                        HTMLContent.append(
                                                                                        "                            <li>\n");
                                                                        HTMLContent.append(
                                                                                        "                                ACTIONS - ")
                                                                                        .append(step.getActionDescription())
                                                                                        .append("\n");
                                                                        HTMLContent.append(
                                                                                        "                                <ul>\n");

                                                                        for (Evidence action : step.getActions()) {
                                                                                String fileName = findFile(
                                                                                                pathReport,
                                                                                                TestSuite,
                                                                                                TestId,
                                                                                                action.getEvidence());

                                                                                String style = "";
                                                                                if (!fileName.contains("TC") || action
                                                                                                .getValue()
                                                                                                .contains("KO")
                                                                                                || action
                                                                                                                .getValue()
                                                                                                                .contains("EXCEPTION")) {
                                                                                        style = " style='background-color: #c54542;'";
                                                                                }

                                                                                HTMLContent.append(
                                                                                                "<li data-id='"
                                                                                                                + fileName
                                                                                                                + "' onclick='cargarContenido(this.id)'"
                                                                                                                + style
                                                                                                                + ">\n");
                                                                                HTMLContent.append(
                                                                                                "                                        ")
                                                                                                .append("[" + action
                                                                                                                .getEvidence()
                                                                                                                + "] "

                                                                                                                + action.getValue()
                                                                                                                                .replaceAll("\\[.*?\\]",
                                                                                                                                                "")
                                                                                                                                .trim())
                                                                                                .append("\n");
                                                                                HTMLContent.append(
                                                                                                "                                    </li>\n");
                                                                        }

                                                                        HTMLContent.append(
                                                                                        "                                </ul>\n");
                                                                        HTMLContent.append(
                                                                                        "                            </li>\n");
                                                                        HTMLContent.append(
                                                                                        "                            <li>\n");
                                                                        HTMLContent.append(
                                                                                        "                                CONDITIONS - ")
                                                                                        .append(step.getConditionDescription())
                                                                                        .append("\n");
                                                                        HTMLContent.append(
                                                                                        "                                <ul>\n");

                                                                        for (Evidence condition : step
                                                                                        .getConditions()) {
                                                                                String fileName = findFile(
                                                                                                pathReport,
                                                                                                TestSuite,
                                                                                                TestId,
                                                                                                condition.getEvidence());

                                                                                String style = "";
                                                                                if (!fileName.contains("TC")
                                                                                                || condition.getValue()
                                                                                                                .contains("KO")
                                                                                                || condition.getValue()
                                                                                                                .contains("EXCEPTION")) {
                                                                                        style = " style='background-color: #c54542;'";
                                                                                }

                                                                                HTMLContent.append(
                                                                                                "<li data-id='"
                                                                                                                + fileName
                                                                                                                + "' onclick='cargarContenido(this.id)'"
                                                                                                                + style
                                                                                                                + ">\n");
                                                                                HTMLContent.append(
                                                                                                "                                        ")
                                                                                                .append("[" + condition
                                                                                                                .getEvidence()
                                                                                                                + "] "

                                                                                                                + condition
                                                                                                                                .getValue()
                                                                                                                                .replaceAll("\\[.*?\\]",
                                                                                                                                                "")
                                                                                                                                .trim())
                                                                                                .append("\n");
                                                                                HTMLContent.append(
                                                                                                "                                    </li>\n");
                                                                        }

                                                                        HTMLContent.append(
                                                                                        "                                </ul>\n");
                                                                        HTMLContent.append(
                                                                                        "                            </li>\n");
                                                                        HTMLContent.append(
                                                                                        "                        </ul>\n");
                                                                        HTMLContent.append(
                                                                                        "                    </li>\n");
                                                                }

                                                                HTMLContent.append("                </ul>\n");
                                                                HTMLContent.append("            </li>\n");

                                                                HTMLContent.append("        </ul>\n");
                                                                HTMLContent.append("    </div>\n");
                                                                HTMLContent.append("</div>\n");
                                                                HTMLContent.append(
                                                                                "            <div class=\"right-panel\" style=\"display: flex; flex-direction: column; justify-content: center;\">\n");
                                                                HTMLContent.append(
                                                                                "                <div class='botonAgrandar'>\n");
                                                                HTMLContent.append(
                                                                                "                    <div class=\"botones-expandir\">\n");
                                                                HTMLContent.append(
                                                                                "                        <button class=\"expandir funcional\" title=\"Expandir acciones y condiciones\">Funcional</button>\n");
                                                                HTMLContent.append(
                                                                                "                        <button class=\"expandir tecnico\" title=\"Expandir todo\">Técnico</button>\n");
                                                                HTMLContent.append(
                                                                                "                        <button class=\"colapsar\" title=\"Colapsar todo\">-</button>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append(
                                                                                "                    <div class=\"botones-expandir2\">\n");
                                                                HTMLContent.append(
                                                                                "                        <button class='Agrandar2'>Agrandar</button>\n");
                                                                HTMLContent.append("                    </div>\n");
                                                                HTMLContent.append("                </div>\n");

                                                                HTMLContent.append(
                                                                                "        <div class='contenedorImagen'>\n");
                                                                HTMLContent.append(
                                                                                "<img class='imagen' src='data:image/png;base64,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' alt='Descripción de la imagen'>\n");

                                                                HTMLContent.append("    </div>\n");
                                                                HTMLContent.append("        </div>\n");
                                                                HTMLContent.append("    </div>\n");

                                                                HTMLContent.append("<script>\n");
                                                                HTMLContent.append(
                                                                                "document.querySelectorAll(\"li\").forEach((li) => {\n");
                                                                HTMLContent.append(
                                                                                "  if (li.querySelector(\"ul\")) {\n");
                                                                HTMLContent.append(
                                                                                "    li.addEventListener(\"click\", function (event) {\n");
                                                                HTMLContent.append(
                                                                                "      // Evitar que el click se propague a elementos padres\n");
                                                                HTMLContent.append("      event.stopPropagation();\n");
                                                                HTMLContent.append(
                                                                                "      // Alternar la clase 'open' para mostrar/ocultar la sublista\n");
                                                                HTMLContent.append(
                                                                                "      this.classList.toggle(\"open\");\n");
                                                                HTMLContent.append("    });\n");
                                                                HTMLContent.append("  }\n");
                                                                HTMLContent.append("});\n");
                                                                HTMLContent.append(
                                                                                "// Maneja clics en ACTIONS y CONDITIONS para cargar archivos\n");
                                                                HTMLContent.append(
                                                                                "document.querySelectorAll('#tree li[data-id]').forEach(function(item) {\n");
                                                                HTMLContent.append(
                                                                                "  item.addEventListener('click', function(event) {\n");
                                                                HTMLContent.append(
                                                                                "    var idElementoClicado = this.getAttribute('data-id');\n");
                                                                HTMLContent.append(
                                                                                "    loadFile(idElementoClicado);\n");
                                                                HTMLContent.append(
                                                                                "    // Evita que el clic en el <li> se propague al <ul> contenedor\n");
                                                                HTMLContent.append("    event.stopPropagation();\n");
                                                                HTMLContent.append("  });\n");
                                                                HTMLContent.append("});\n");
                                                                HTMLContent.append(
                                                                                "// Maneja el botón para mostrar GIF\n");
                                                                HTMLContent.append(
                                                                                "document.querySelector('.butonGIF').addEventListener('click', function() {\n");
                                                                HTMLContent.append("  // Mostrar la imagen de carga\n");
                                                                HTMLContent.append(
                                                                                "  loadFile(\"data:image/png;base64,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\");\n");
                                                                HTMLContent.append(
                                                                                "  document.querySelector('.imagen').classList.add(\"carga\");\n");
                                                                HTMLContent.append("  setTimeout(function() {\n");
                                                                HTMLContent.append(
                                                                                "    document.querySelector('.imagen').classList.remove(\"carga\");\n");
                                                                HTMLContent.append(
                                                                                "    var gifPath = \"VerificationTestCase.gif\";\n");
                                                                HTMLContent.append("    var img = new Image();\n");
                                                                HTMLContent.append("    img.src = gifPath;\n");
                                                                HTMLContent.append("    img.onload = function() {\n");
                                                                HTMLContent.append("      loadFile(gifPath);\n");
                                                                HTMLContent.append("    };\n");
                                                                HTMLContent.append("    img.onerror = function() {\n");
                                                                HTMLContent.append(
                                                                                "      var contenedor = document.querySelector('.contenedorImagen');\n");
                                                                HTMLContent.append(
                                                                                "      contenedor.innerHTML = \"<p class='GifNoExiste'>El GIF no se ha creado todavía.</p>\";\n");
                                                                HTMLContent.append("    };\n");
                                                                HTMLContent.append("  }, 2000);\n");
                                                                HTMLContent.append("});\n");
                                                                HTMLContent.append(
                                                                                "// Función para cargar archivos en el contenedor\n");
                                                                HTMLContent.append(
                                                                                "function loadFile(nombreImagen) {\n");
                                                                HTMLContent.append(
                                                                                "    var extension;\n");
                                                                HTMLContent.append(
                                                                                "    var contenedor = document.querySelector('.contenedorImagen');\n");
                                                                HTMLContent.append(
                                                                                "    var img = new Image();\n");
                                                                HTMLContent.append(
                                                                                "    var iframeExiste = document.querySelector('.iframe');\n");
                                                                HTMLContent.append(
                                                                                "    var imagenExistente = contenedor.querySelector('.imagen');\n");
                                                                HTMLContent.append(
                                                                                "    var MensajeGif = contenedor.querySelector('.GifNoExiste');\n");
                                                                HTMLContent.append(
                                                                                "    // Verificar si nombreImagen es una cadena base64\n");
                                                                HTMLContent.append(
                                                                                "    if (nombreImagen.startsWith('data:image/')) {\n");
                                                                HTMLContent.append(
                                                                                "        // Es una imagen base64\n");
                                                                HTMLContent.append(
                                                                                "        img.src = nombreImagen;\n");
                                                                HTMLContent.append(
                                                                                "        extension = nombreImagen.split(';')[0].split('/')[1];\n");
                                                                HTMLContent.append(
                                                                                "    } else {\n");
                                                                HTMLContent.append(
                                                                                "        // Obtener la extensión del archivo\n");
                                                                HTMLContent.append(
                                                                                "        extension = nombreImagen.split('.').pop();\n");
                                                                HTMLContent.append(
                                                                                "    }\n");
                                                                HTMLContent.append(
                                                                                "    if (extension == 'json') {\n");
                                                                HTMLContent.append(
                                                                                "        var iframe = document.createElement('iframe');\n");
                                                                HTMLContent.append(
                                                                                "        iframe.classList.add('iframe');\n");
                                                                HTMLContent.append(
                                                                                "        iframe.src = nombreImagen;\n");
                                                                HTMLContent.append(
                                                                                "        iframe.width = '100%';\n");
                                                                HTMLContent.append(
                                                                                "        iframe.height = '500px';\n");
                                                                HTMLContent.append(
                                                                                "        if (iframeExiste) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(iframeExiste);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        if (imagenExistente) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(imagenExistente);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        if (MensajeGif) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(MensajeGif);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        contenedor.appendChild(iframe);\n");
                                                                HTMLContent.append(
                                                                                "    } else if (extension == 'webp' || extension == 'png' || extension == 'gif') {\n");
                                                                HTMLContent.append(
                                                                                "       img.src = nombreImagen;\n");
                                                                HTMLContent.append(
                                                                                "        img.alt = 'Error al encontrar rutaJSON';\n");

                                                                HTMLContent.append(
                                                                                "        if (imagenExistente) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(imagenExistente);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        if (MensajeGif) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(MensajeGif);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        if (iframeExiste) {\n");
                                                                HTMLContent.append(
                                                                                "            contenedor.removeChild(iframeExiste);\n");
                                                                HTMLContent.append(
                                                                                "        }\n");
                                                                HTMLContent.append(
                                                                                "        img.classList.add('imagen');\n");
                                                                HTMLContent.append(
                                                                                "        img.classList.remove('carga');\n");
                                                                HTMLContent.append(
                                                                                "        contenedor.appendChild(img);\n");
                                                                HTMLContent.append(
                                                                                "    } else {\n");
                                                                HTMLContent.append(
                                                                                "        // Manejo para otros tipos de archivo, si es necesario\n");
                                                                HTMLContent.append(
                                                                                "    }\n");
                                                                HTMLContent.append(
                                                                                "}\n");
                                                                HTMLContent.append(
                                                                                "// Maneja la activación de los elementos\n");
                                                                HTMLContent.append("var filaActiva = null;\n");
                                                                HTMLContent.append(
                                                                                "document.querySelectorAll('#tree li[data-id]').forEach(function(item) {\n");
                                                                HTMLContent.append(
                                                                                "  item.addEventListener('click', function() {\n");
                                                                HTMLContent.append("    if (filaActiva !== null) {\n");
                                                                HTMLContent.append(
                                                                                "      filaActiva.classList.remove(\"active\");\n");
                                                                HTMLContent.append("    }\n");
                                                                HTMLContent.append(
                                                                                "    this.classList.add(\"active\");\n");
                                                                HTMLContent.append("    filaActiva = this;\n");
                                                                HTMLContent.append("  });\n");
                                                                HTMLContent.append("});\n");
                                                                HTMLContent.append(
                                                                                "// Maneja el botón para agrandar la imagen\n");
                                                                HTMLContent.append(
                                                                                "document.querySelector('.Agrandar2').addEventListener('click', function() {\n");
                                                                HTMLContent.append(
                                                                                "  var imagenElemento = document.querySelector('.imagen');\n");
                                                                HTMLContent.append(
                                                                                "  var jsonElemento = document.querySelector('.iframe');\n");
                                                                HTMLContent.append("  var src = '';\n");
                                                                HTMLContent.append(
                                                                                "  if (imagenElemento) { src = imagenElemento.src; }\n");
                                                                HTMLContent.append(
                                                                                "  if (jsonElemento) { src = jsonElemento.src; }\n");
                                                                HTMLContent.append("  var ventanaAncho = 1200;\n");
                                                                HTMLContent.append("  var ventanaAlto = 1000;\n");
                                                                HTMLContent.append(
                                                                                "  var ventanaIzquierda = (screen.width - ventanaAncho) / 2;\n");
                                                                HTMLContent.append(
                                                                                "  var ventanaArriba = (screen.height - ventanaAlto) / 2;\n");
                                                                HTMLContent.append(
                                                                                "  var opciones = 'width=' + ventanaAncho + ',height=' + ventanaAlto + ',left=' + ventanaIzquierda + ',top=' + ventanaArriba;\n");
                                                                HTMLContent.append(
                                                                                "  window.open(src, '_blank', opciones);\n");
                                                                HTMLContent.append("});\n");
                                                                HTMLContent.append(
                                                                                "document.addEventListener('DOMContentLoaded', function() {\n"
                                                                                                +
                                                                                                "  // Función para expandir el árbol hasta el nivel de 'ACTIONS' y 'CONDITIONS'\n"
                                                                                                +
                                                                                                "  document.querySelector('.funcional').addEventListener('click', function() {\n"
                                                                                                +
                                                                                                "    expandToLevel(3); // Expandir hasta el tercer nivel\n"
                                                                                                +
                                                                                                "  });\n" +
                                                                                                "\n" +
                                                                                                "  // Función para expandir todo el árbol\n"
                                                                                                +
                                                                                                "  document.querySelector('.tecnico').addEventListener('click', function() {\n"
                                                                                                +
                                                                                                "    document.querySelectorAll('#tree li').forEach(function(li) {\n"
                                                                                                +
                                                                                                "      li.classList.add('open');\n"
                                                                                                +
                                                                                                "    });\n" +
                                                                                                "  });\n" +
                                                                                                "\n" +
                                                                                                "  // Función para colapsar todo el árbol\n"
                                                                                                +
                                                                                                "  document.querySelector('.colapsar').addEventListener('click', function() {\n"
                                                                                                +
                                                                                                "    document.querySelectorAll('#tree li').forEach(function(li) {\n"
                                                                                                +
                                                                                                "      li.classList.remove('open');\n"
                                                                                                +
                                                                                                "    });\n" +
                                                                                                "  });\n" +
                                                                                                "\n" +
                                                                                                "  // Función para expandir hasta un nivel específico\n"
                                                                                                +
                                                                                                "  function expandToLevel(level) {\n"
                                                                                                +
                                                                                                "    document.querySelectorAll('#tree li').forEach(function(li) {\n"
                                                                                                +
                                                                                                "      const depth = getDepth(li);\n"
                                                                                                +
                                                                                                "      if (depth <= level) {\n"
                                                                                                +
                                                                                                "        li.classList.add('open');\n"
                                                                                                +
                                                                                                "      } else {\n" +
                                                                                                "        li.classList.remove('open'); // Asegúrate de colapsar los elementos que están más allá del nivel especificado\n"
                                                                                                +
                                                                                                "      }\n" +
                                                                                                "    });\n" +
                                                                                                "\n" +
                                                                                                "    // Abre todos los padres necesarios para que los elementos expandidos sean visibles\n"
                                                                                                +
                                                                                                "    document.querySelectorAll('#tree li.open').forEach(function(li) {\n"
                                                                                                +
                                                                                                "      let parent = li.parentElement;\n"
                                                                                                +
                                                                                                "      while (parent && parent.tagName === 'UL') {\n"
                                                                                                +
                                                                                                "        parent.parentElement.classList.add('open');\n"
                                                                                                +
                                                                                                "        parent = parent.parentElement.parentElement;\n"
                                                                                                +
                                                                                                "      }\n" +
                                                                                                "    });\n" +
                                                                                                "  }\n" +
                                                                                                "\n" +
                                                                                                "  // Función para obtener la profundidad de un elemento en el árbol\n"
                                                                                                +
                                                                                                "  function getDepth(element) {\n"
                                                                                                +
                                                                                                "    let depth = 0;\n" +
                                                                                                "    while (element.parentElement && element.parentElement.id !== 'tree') {\n"
                                                                                                +
                                                                                                "      depth++;\n" +
                                                                                                "      element = element.parentElement;\n"
                                                                                                +
                                                                                                "    }\n" +
                                                                                                "    return depth;\n" +
                                                                                                "  }\n" +
                                                                                                "});\n");
                                                                HTMLContent.append("</script>\n");

                                                                HTMLContent.append("</body>\n");
                                                                HTMLContent.append("\n");
                                                                HTMLContent.append("</html>");

                                                                // Al final del método, escribimos el contenido a un
                                                                // archivo y
                                                                // cerramos el FileWriter
                                                                HTMLpath = pathReport + "/" + TestSuite + "/" + TestId
                                                                                + "/report.html";

                                                                try (FileWriter fileWriter = new FileWriter(HTMLpath)) {
                                                                        fileWriter.write(HTMLContent.toString());
                                                                } catch (Exception e) {
                                                                        System.err.println(
                                                                                        "Error al escribir el archivo en la ruta: "
                                                                                                        + HTMLpath);
                                                                }

                                                                // Limpiamos el StringBuilder después de escribir el
                                                                // archivo
                                                                // Esta línea debe estar fuera del bucle de las
                                                                // evidencias y
                                                                // después de cerrar el FileWriter
                                                                HTMLContent.setLength(0);
                                                        }
                                                }
                                        } catch (Exception e) {
                                                System.err.println("Error al recorrer Test dentro del JSON.");
                                                e.printStackTrace();
                                        }
                                }
                        } catch (Exception e) {
                                System.err.println("Error al recorrer TestSuite dentro del JSON.");
                                e.printStackTrace();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        /**
         * Este metodo comprueba la existencia de la palabra ACTION o CONDITION dentro
         * de la variable value y lo escribe dentro del HTML respectivamente.
         * 
         * @param report
         * @param value
         * @param state
         * @param evidence
         * @return
         */
        public static String checkActionCondition(String report, String value, String state, String evidence) {
                try {
                        if (value != null) { // Verifica si value no es null
                                // Verificar si el texto contiene "CONDITION"
                                if (value.contains("[CONDITION")) { // Recibe la ruta completa, y la recorta, para que
                                                                    // solo sea
                                                                    // la carpeta especifica del reporte
                                                                    // reporte.substring(14)
                                        return "<td class=\"column3 test \" id='"
                                                        + findFile(report, TestSuite, TestId,
                                                                        evidence)
                                                        + "'>CONDITION</td>\n";
                                }

                                // Verificar si el texto contiene "ACTION"
                                if (value.contains("ACTION")) {
                                        return "<td class=\"column3 test \">ACTION</td>\n";
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return ""; // En caso de que value sea null o no contenga ni "CONDITION" ni "ACTION"
        }

        /**
         * Este metodo remueve el texto entre corchetes
         * 
         * @param message
         * @return
         */
        public static String removeTextBetweenBrackets(String message) {
                // Definir el patrón de la expresión regular para encontrar el texto entre
                // corchetes
                String pattern = "\\[.*?\\]";

                // Reemplazar el texto entre corchetes con una cadena vacía
                String result = message.replaceAll(pattern, "");

                return result.trim(); // Eliminar espacios en blanco adicionales al inicio y al final del resultado
        }

        /**
         * Este metodo busca dentro del directorio de cada testCase la existencia de un
         * archivo cuyo nombre comience por la misma cadena de texto que le pasamos por
         * parametro en fileName.
         * 
         * @param pathDirectory
         * @param testSuite
         * @param TestId
         * @param fileName
         * @return
         */
        public static String findFile(String pathDirectory, String testSuite, String TestId,
                        String fileName) {
                File directory = new File(pathDirectory + "/" + testSuite + "/" + TestId);
                try {
                        if (directory.exists() && directory.isDirectory()) {
                                File[] listOfFiles = directory.listFiles();
                                if (listOfFiles != null) {
                                        for (File file : listOfFiles) {
                                                try {
                                                        if (file.isFile()) {
                                                                if (file.getName().startsWith(fileName)
                                                                                && file.getName().endsWith(".webp")) {
                                                                        return file.getName();
                                                                }

                                                                if (file.getName().startsWith(fileName)
                                                                                && file.getName().endsWith(".json")) {
                                                                        return file.getName();
                                                                }
                                                        }
                                                } catch (Exception e) {
                                                        System.out.println("[ERROR] Error al procesar el archivo: "
                                                                        + file.getName());
                                                        e.printStackTrace();
                                                }
                                        }
                                } else {
                                        System.out.println("[ERROR] No se encontraron archivos en el directorio: "
                                                        + directory.getPath());
                                }
                        } else {
                                System.out.println("[ERROR] El directorio no existe o no es un directorio: "
                                                + directory.getPath());
                        }
                } catch (Exception e) {
                        System.out.println("[ERROR] Error al procesar el directorio: " + directory.getPath());
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * Este metodo formatea un archivo JSON para que se muestre con estructura de
         * arbol.
         * 
         * @param filePath
         * @return
         */
        public static String getStringJsonFormatted(String filePath) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                try {
                        File jsonFile = new File(filePath);
                        Object json = mapper.readValue(jsonFile, Object.class);
                        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * Este metodo recorre todas las carpetas dentro del reporte y genera un HTML en
         * cada carpeta TestCase personalizado, usando los datos del JSON para cada caso
         * de prueba.
         * 
         * Como parametro se le pasa una ruta completa hasta el report_HH_MM_ss
         * 
         * @param pathReport
         */
        public static void generateHTML(String pathReport) {
                File directoryReport = new File(pathReport);
                File jsonFile = new File(directoryReport, "report.json");
                try {
                        // Comprueba si el JSON existe
                        if (jsonFile.exists()) {
                                // Lista todos los directorios dentro de report
                                File[] listTestSuites = directoryReport.listFiles(File::isDirectory);
                                for (File testSuite : listTestSuites) {
                                        try {
                                                File[] listTestCases = testSuite.listFiles(File::isDirectory);
                                                for (File testCase : listTestCases) {
                                                        try {
                                                                System.out.println("Generando Reporte Tecnico: "
                                                                                + testCase.getName());
                                                                createHTML(pathReport, testCase.getName());
                                                        } catch (Exception e) {
                                                                System.err.println(
                                                                                "Error al procesar el caso de prueba: "
                                                                                                + testCase.getName());
                                                                e.printStackTrace();
                                                        }
                                                }
                                        } catch (Exception e) {
                                                System.err.println("Error al procesar la suite de pruebas: "
                                                                + testSuite.getName());
                                                e.printStackTrace();
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Error al generar HTML");
                        e.printStackTrace();
                }
        }
}