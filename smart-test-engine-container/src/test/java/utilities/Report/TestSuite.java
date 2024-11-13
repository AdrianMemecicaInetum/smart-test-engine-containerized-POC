package utilities.Report;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestSuite {

    @JsonProperty("TestSuiteName")
    private String testSuiteName;

    @JsonProperty("TestsExecuted")
    private String testsExecuted;

    @JsonProperty("Tests")
    private List<Test> tests;

    @JsonProperty("tsNumTests")
    private Integer tsNumTests = 0;
    @JsonProperty("tsNumPassed")
    private Integer tsNumPassed = 0;
    @JsonProperty("tsNumFailed")
    private Integer tsNumFailed = 0;
    @JsonProperty("tsNumPassedWithFailures")
    private Integer tsNumPassedWithFailures = 0;
    @JsonProperty("tsNumBlocked")
    private Integer tsNumBlocked = 0;
    @JsonProperty("tsNumAborted")
    private Integer tsNumAborted = 0;

    public TestSuite() {
    }

    public TestSuite(String testSuiteName) {
        this.testSuiteName = testSuiteName;
        tests = new ArrayList<Test>();
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public void setTestSuiteName(String testSuiteName) {
        this.testSuiteName = testSuiteName;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public String getTestsExecuted() {
        return testsExecuted;
    }

    public void setTestsExecuted(String testsExecuted) {
        this.testsExecuted = testsExecuted;
    }

    public Integer getTsNumTests() {
        return tsNumTests;
    }

    public void setTsNumTests(Integer tsNumTests) {
        this.tsNumTests = tsNumTests;
    }

    public Integer getTsNumPassed() {
        return tsNumPassed;
    }

    public void setTsNumPassed(Integer tsNumPassed) {
        this.tsNumPassed = tsNumPassed;
    }

    public Integer getTsNumFailed() {
        return tsNumFailed;
    }

    public void setTsNumFailed(Integer tsNumFailed) {
        this.tsNumFailed = tsNumFailed;
    }

    public Integer getTsNumPassedWithFailures() {
        return tsNumPassedWithFailures;
    }

    public void setTsNumPassedWithFailures(Integer tsNumPassedWithFailures) {
        this.tsNumPassedWithFailures = tsNumPassedWithFailures;
    }

    public Integer getTsNumBlocked() {
        return tsNumBlocked;
    }

    public void setTsNumBlocked(Integer tsNumBlocked) {
        this.tsNumBlocked = tsNumBlocked;
    }

    public Integer getTsNumAborted() {
        return tsNumAborted;
    }

    public void setTestsNumAborted(Integer tsNumAborted) {
        this.tsNumAborted = tsNumAborted;
    }

    @Override
    public String toString() {
        tsNumTests += getTests().size();
        String testsString = "";
        if (getTests().size() > 0) {
            for (Test test : getTests()) {
                if (test.getStatus() == "PASSED") {
                    tsNumPassed++;
                } else if (test.getStatus() == "FAILED") {
                    tsNumFailed++;
                } else if (test.getStatus() == "PASSED WITH FAILURES") {
                    tsNumPassedWithFailures++;
                } else if (test.getStatus() == "BLOCKED") {
                    tsNumBlocked++;
                } else if (test.getStatus() == "ABORTED") {
                    tsNumAborted++;
                }
                testsString += test.toString() + ",";
            }
            testsString = testsString.substring(0, testsString.length() - 1);
        }

        return "{\"TestSuiteName\":\"" + testSuiteName + "\"," +
                "\"TestsExecuted\":\"" + tsNumTests.toString() + "\"," +
                "\"TestsPASSED\":\"" + tsNumPassed.toString() + "\"," +
                "\"TestsFAILED\":\"" + tsNumFailed.toString() + "\"," +
                "\"TestsPASSEDWITHFAILURES\":\"" + tsNumPassedWithFailures.toString() + "\"," +
                "\"TestsBLOCKED\":\"" + tsNumBlocked.toString() + "\"," +
                "\"TestsABORTED\":\"" + tsNumAborted.toString() + "\"," +
                "\"Tests\": [" + testsString + "]}";
    }
}
