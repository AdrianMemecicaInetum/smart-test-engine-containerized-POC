package utilities.Report;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evidence {

    @JsonProperty("Step")
    private String step;

    @JsonProperty("StepStatus")
    private String stepStatus;

    @JsonProperty("Evidence")
    private String evidence;

    @JsonProperty("Value")
    private String value;

    public Evidence() {
    }

    public Evidence(String status, String value, String evidence) {
        this.stepStatus = status;
        this.value = value;
        this.evidence = evidence;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvicende(String evidence) {
        this.evidence = evidence;
    }

    @Override
    public String toString() {
        if (stepStatus == "PASSED WITH FAILURES" || stepStatus == "FAILED" || stepStatus == "PASSED") {
            return "{\"Evidence\":\"" + evidence + "\",\"StepStatus\":\"" + stepStatus
                    + "\",\"Value\":\"" + value + "\"}";
        } else {
            return "";
        }
    }

}