package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.MealServiceTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeWatchRule extends Stopwatch {
    private static final Map<String, Long> resultMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info(String.format("Test %s %s, spent %d milliseconds",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
        resultMap.put(testName, TimeUnit.NANOSECONDS.toMillis(nanos));

    }

    public TimeWatchRule() {
        super();
    }

    @Override
    public long runtime(TimeUnit unit) {
        return super.runtime(unit);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    public static void getFinalResult() {
        StringBuilder sb = new StringBuilder("All tests result:\n");
        for (Map.Entry<String, Long> temp : resultMap.entrySet()) {
            sb.append(String.format("Method %s spent  %d ms \n", temp.getKey(), temp.getValue()));
        }
        log.info(sb.toString());
    }

}
