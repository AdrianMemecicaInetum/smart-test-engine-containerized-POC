package utilities.Report;

import utilities.Utils.MainUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class StringToJsonFile {

    public static void convertStringToJsonFile(ResumeReport resumeReport) {
        try {
            // Crear un objeto ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            // Convertir la cadena en un objeto JSON
            Object json = mapper.readValue(resumeReport.toString(), Object.class);
            // Exportar el objeto JSON a un archivo .json
            File destinationFolder = new File("./resume-report/" + resumeReport.getDmahms().substring(0, 10));
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            File destinationFolder2 = new File(destinationFolder + "/report" + resumeReport.getDmahms().substring(10));
            if (!destinationFolder2.exists()) {
                destinationFolder2.mkdirs();
            }
            File jsonFile = new File(destinationFolder2 + "/report.json");
            mapper.writeValue(jsonFile, json);
            // FileUtils.copyFile(jsonFile, new File("Resume-report/" + dmahms.substring(0,
            // 10) + "/report" + dmahms.substring(10) + ".json"));
            MainUtils.printReportMessage(
                    "[FRAMEWORK] Se ha generado un reporte en la ruta -> " + destinationFolder2 + "/report.json");
        } catch (IOException e) {
            MainUtils.printReportMessage("Error: " + e.getMessage());
        }
    }

    public static void convertAPIStringToJsonFile(APIEvidence apiEvidence) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File jsonPath = new File("./resume-report/" + MainUtils.dmahms.substring(0, 10) + "/report"
                    + MainUtils.dmahms.substring(10) + "/" + MainUtils.splitString(MainUtils.testId, "_")[0] + "/"
                    + MainUtils.testId);
            if (!jsonPath.exists()) {
                jsonPath.mkdirs();
            }
            File jsonFile = new File(
                    jsonPath + "/" + String.format("%03d", MainUtils.imageGet()) + " - " + MainUtils.testId + ".json");
            mapper.writeValue(jsonFile, apiEvidence);

            MainUtils.printReportMessage(
                    "[FRAMEWORK] Se ha generado un reporte en la ruta -> " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            MainUtils.printReportMessage("Error: " + e.getMessage());
        }
    }

}