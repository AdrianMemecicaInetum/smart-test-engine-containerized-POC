
package utilities.Report;

import java.util.ArrayList;
import java.util.List;
import utilities.Utils.MainUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {

    @JsonProperty("TestId")
    private String testId;
    @JsonProperty("TestDescription")
    private String testDescription;

    @JsonProperty("TestType")
    private String testType;
    @JsonProperty("Status")
    private String status;

    @JsonProperty("ActionsRetries")
    private Integer actionRetries;
    @JsonProperty("ActionsExecuted")
    private Integer actionExecuted;
    @JsonProperty("ConditionsRetries")
    private Integer conditionRetries;
    @JsonProperty("ConditionsExecuted")
    private Integer conditionExecuted;
    @JsonProperty("ConditionsKO")
    private Integer conditionsKO;
    @JsonProperty("stepsExecuted")
    private Integer stepsExecuted;

    @JsonProperty("ExecutionTime")
    private String executionTime;
    @JsonProperty("Steps")
    private List<Steps> steps;
    @JsonProperty("PreconditionSteps")
    private List<PreconditionSteps> preconditionSteps;

    public Test() {
    }

    public Test(String testId) {
        this.testId = testId;
        this.testType = MainUtils.testType;
        steps = new ArrayList<Steps>();
        preconditionSteps = new ArrayList<PreconditionSteps>();
        actionRetries = 0;
        actionExecuted = 0;
        conditionRetries = 0;
        conditionExecuted = 0;
        conditionsKO = 0;
        stepsExecuted = 0;
        status = "";
    }

    public Test(String testId, String testDescription) {
        this.testId = testId;
        this.testDescription = testDescription;
        this.testType = MainUtils.testType;
        steps = new ArrayList<Steps>();
        preconditionSteps = new ArrayList<PreconditionSteps>();
        actionRetries = 0;
        actionExecuted = 0;
        conditionRetries = 0;
        conditionExecuted = 0;
        conditionsKO = 0;
        stepsExecuted = 0;
        status = "";
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getActionRetries() {
        return actionRetries;
    }

    public void setActionRetries(Integer actionRetries) {
        this.actionRetries = actionRetries;
    }

    public Integer getActionExecuted() {
        return actionExecuted;
    }

    public void setActionExecuted(Integer actionExecuted) {
        this.actionExecuted = actionExecuted;
    }

    public Integer getConditionRetries() {
        return conditionRetries;
    }

    public void setConditionRetries(Integer conditionRetries) {
        this.conditionRetries = conditionRetries;
    }

    public Integer getConditionExecuted() {
        return conditionExecuted;
    }

    public void setConditionExecuted(Integer conditionExecuted) {
        this.conditionExecuted = conditionExecuted;
    }

    public Integer getConditionsKO() {
        return conditionsKO;
    }

    public void setConditionsKO(Integer conditionsKO) {
        this.conditionsKO = conditionsKO;
    }

    public Integer getStepsExecuted() {
        return stepsExecuted;
    }

    public void setStepsExecuted(Integer stepsExecuted) {
        this.stepsExecuted = stepsExecuted;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setPreconditionSteps(List<PreconditionSteps> preconditionSteps) {
        this.preconditionSteps = preconditionSteps;
    }

    public List<PreconditionSteps> getPreconditionSteps() {

        return preconditionSteps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        String stepsString = "";
        if (steps.size() > 0) {
            for (Steps ts : steps) {
                stepsString += ts.toString() + ",";
            }
            stepsString = stepsString.substring(0, stepsString.length() - 1);
        }
        String preoconditionStepsString = "";
        if (preconditionSteps.size() > 0) {
            for (PreconditionSteps ts : preconditionSteps) {
                preoconditionStepsString += ts.toString() + ",";
            }
            preoconditionStepsString = preoconditionStepsString.substring(0, preoconditionStepsString.length() - 1);
        }

        return "{\"TestId\":\"" + testId + "\"," +
                "\"TestDescription\":\"" + testDescription + "\"," +
                "\"TestType\":\"" + testType + "\"," +
                "\"Status\":\"" + status + "\"," +
                "\"ActionsRetries\":\"" + actionRetries + "\"," +
                "\"ActionsExecuted\":\"" + actionExecuted + "\"," +
                "\"ConditionsRetries\":\"" + conditionRetries + "\"," +
                "\"ConditionsExecuted\":\"" + conditionExecuted + "\"," +
                "\"ConditionsKO\":\"" + conditionsKO + "\"," +
                "\"StepsExecuted\":\"" + stepsExecuted + "\"," +
                "\"ExecutionTime\":\"" + executionTime + "\"," +
                // "\"Preconditions\": [{" + "\"PreconditionSteps\": [" +
                // preoconditionStepsString + "]" + "}]," +
                "\"PreconditionSteps\": [" + preoconditionStepsString + "]," +
                "\"Steps\": [" + stepsString + "]}";
    }

}
