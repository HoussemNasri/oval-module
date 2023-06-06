package com.suse;

import com.suse.manager.OvalTestManager;

import java.util.Objects;

public class TestEvaluator {
    private final OvalTestManager ovalTestManager;

    public TestEvaluator(OvalTestManager ovalTestManager) {
        Objects.requireNonNull(ovalTestManager);
        this.ovalTestManager = ovalTestManager;
    }

    public boolean evaluate(String testId) {
        return false;
    }
}
