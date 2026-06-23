package com.solvd.hospital.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Observes every TestNG test method across both suites and logs:
 * - one line per test as it starts/passes/fails/is skipped
 * - one pass/fail/skip summary line when each <test> block finishes
 * Registered at the suite level in:
 * src/test/resources/testng-suite-people.xml
 * src/test/resources/testng-suite-clinical-ops.xml
 * via <listeners><listener class-name="com.solvd.hospital.listener.TestNGListener"/></listeners>
 */
public class TestNGListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("STARTING: {}.{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("PASSED:   {}.{} ({} ms)",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String reason = result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "unknown error";

        LOGGER.error("FAILED:   {}.{} -> {}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                reason);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("SKIPPED:  {}.{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("===== Starting test block: {} =====", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        LOGGER.info("===== Finished test block: {} -> {} passed, {} failed, {} skipped =====",
                context.getName(), passed, failed, skipped);
    }

}
