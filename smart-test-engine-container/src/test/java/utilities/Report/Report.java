package utilities.Report;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {
    @JsonProperty("TestSuitesInvolved")
    private String testSuitesInvolved;

    @JsonProperty("TestsExecuted")
    private String testsExecuted;

    // ... otros campos ...

    @JsonProperty("TestSuites")
    private List<TestSuite> testSuites;

    public String getTestSuitesInvolved() {
        return testSuitesInvolved;
    }

    public void setTestSuitesInvolved(String testSuitesInvolved) {
        this.testSuitesInvolved = testSuitesInvolved;
    }

    public String getTestsExecuted() {
        return testsExecuted;
    }

    public void setTestsExecuted(String testsExecuted) {
        this.testsExecuted = testsExecuted;
    }

    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    public void setTestSuites(List<TestSuite> testSuites) {
        this.testSuites = testSuites;
    }
}