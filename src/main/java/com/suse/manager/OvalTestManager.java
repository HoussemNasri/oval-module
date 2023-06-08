package com.suse.manager;


import com.suse.model.TestType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OvalTestManager {
    private final Map<String, TestType> testsMap = new HashMap<>();

    public OvalTestManager(List<TestType> tests) {
        for (TestType test : tests) {
            testsMap.put(test.getId(), test);
        }
    }

    public TestType get(String testId) {
        TestType test = testsMap.get(testId);
        if (test == null) {
            throw new IllegalArgumentException("The test id is invalid: " + testId);
        }
        return test;
    }

    public boolean exists(String testId) {
        return testsMap.containsKey(testId);
    }

    public void add(TestType testType) {
        testsMap.put(testType.getId(), testType);
    }
}
