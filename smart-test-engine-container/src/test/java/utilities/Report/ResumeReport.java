package utilities.Report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import utilities.Utils.MainUtils;

public class ResumeReport {

    private List<TestSuite> testSuites;
    private String dmahms;
    private Integer numTestSuites = 0;
    private Integer numTests = 0;
    private Integer numPassed = 0;
    private Integer numFailed = 0;
    private Integer numPassedWithFailures = 0;
    private Integer numBlocked = 0;
    private Integer numAborted = 0;
    private long totalTimeExecution = 0;
    private String hms;

    public ResumeReport(Collection<TestSuite> collection) {
        this.testSuites = new ArrayList<TestSuite>(collection);
    }

    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    public void setTestSuites(List<TestSuite> testSuites) {
        this.testSuites = testSuites;
    }

    public String getDayOfExecution() {
        DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        return formatter.format(new Date());
    }

    public String getDayOfExecutionReport() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    public String getDmahms() {
        return dmahms;
    }

    public void setDmahms(String dmahms) {
        this.dmahms = dmahms;
    }

    public String getHms() {
        return hms;
    }

    public Integer getNumTestSuites() {
        return numTestSuites;
    }

    public Integer getNumTest() {
        return numTests;
    }

    public Integer getNumPassed() {
        return numPassed;
    }

    public Integer getNumFailed() {
        return numFailed;
    }

    public Integer getNumPassedWithFailures() {
        return numPassedWithFailures;
    }

    public Integer getBlocked() {
        return numBlocked;
    }

    public Integer getAborted() {
        return numAborted;
    }

    public Long getTotalTimeExecution() {
        return totalTimeExecution;
    }

    @Override
    public String toString() {
        String testSuitesString = "";
        numTestSuites += testSuites.size();
        if (testSuites.size() > 0) {
            for (TestSuite testSuite : testSuites) {
                testSuitesString += testSuite.toString() + ",";
                numTests += testSuite.getTests().size();
                if (testSuite.getTests().size() > 0) {
                    for (Test test : testSuite.getTests()) {
                        if (test.getStatus() == "PASSED") {
                            numPassed++;
                        } else if (test.getStatus() == "FAILED") {
                            numFailed++;
                        } else if (test.getStatus() == "PASSED WITH FAILURES") {
                            numPassedWithFailures++;
                        } else if (test.getStatus() == "BLOCKED") {
                            numBlocked++;
                        } else if (test.getStatus() == "ABORTED") {
                            numAborted++;
                        }
                    }
                }
            }
            testSuitesString = testSuitesString.substring(0, testSuitesString.length() - 1);
        }

        long endTotalExecutionTime = System.currentTimeMillis();
        totalTimeExecution = (endTotalExecutionTime - MainUtils.startTotalExecutionTime);
        hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(totalTimeExecution),
                TimeUnit.MILLISECONDS.toMinutes(totalTimeExecution) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTimeExecution)),
                TimeUnit.MILLISECONDS.toSeconds(totalTimeExecution) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimeExecution)));
        return "{\"TestSuitesInvolved\":\"" + numTestSuites.toString() + "\"," +
                "\"TestsExecuted\":\"" + numTests.toString() + "\"," +
                "\"TestsPASSED\":\"" + numPassed.toString() + "\"," +
                "\"TestsFAILED\":\"" + numFailed.toString() + "\"," +
                "\"TestsPASSEDWITHFAILURES\":\"" + numPassedWithFailures.toString() + "\"," +
                "\"TestsBLOCKED\":\"" + numBlocked.toString() + "\"," +
                "\"TestsABORTED\":\"" + numAborted.toString() + "\"," +
                "\"TotalExecutionTime\":\"" + hms + "\"," +
                "\"DayOfExecution\":\"" + getDayOfExecution() + "\"," +
                "\"TestSuites\": [" + testSuitesString + "]}";
    }

}
