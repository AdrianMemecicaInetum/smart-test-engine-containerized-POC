
package utilities.Report;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Steps {

    @JsonProperty("StepNumber")
    private int stepNumber;
    @JsonProperty("Step")
    private String step;
    @JsonProperty("ActionDescription")
    private String actionDescription;
    @JsonProperty("ConditionDescription")
    private String conditionDescription;
    @JsonProperty("Actions")
    private List<Evidence> actions;
    @JsonProperty("Conditions")
    private List<Evidence> conditions;

    public Steps() {
    }

    public Steps(int stepNumber, String actionDescription, String conditionDescription) {
        this.stepNumber = stepNumber;
        step = new String();
        this.actionDescription = actionDescription;
        this.conditionDescription = conditionDescription;
        actions = new ArrayList<Evidence>();
        conditions = new ArrayList<Evidence>();

    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public List<Evidence> getActions() {
        return actions;
    }

    public void setActions(List<Evidence> actions) {
        this.actions = actions;
    }

    public List<Evidence> getConditions() {
        return conditions;
    }

    public void setConditions(List<Evidence> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        String actionsString = "";
        if (actions.size() > 0) {
            for (Evidence ts : actions) {
                actionsString += ts.toString() + ",";
            }
            actionsString = actionsString.substring(0, actionsString.length() - 1);
        }
        String conditionsString = "";
        if (conditions.size() > 0) {
            for (Evidence ts : conditions) {
                conditionsString += ts.toString() + ",";
            }
            conditionsString = conditionsString.substring(0, conditionsString.length() - 1);
        }

        return "{\"Step\":\"" + step + "\"," +
                "\"ActionDescription\":\"" + actionDescription + "\"," +
                "\"ConditionDescription\":\"" + conditionDescription + "\"," +
                "\"Actions\": [" + actionsString + "]," +
                "\"Conditions\": [" + conditionsString + "]}";
    }

}
