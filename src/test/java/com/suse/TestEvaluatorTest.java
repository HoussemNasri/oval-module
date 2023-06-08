package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEvaluatorTest {
    TestEvaluator testEvaluator;
    OvalObjectManager ovalObjectManager = new OvalObjectManager(Collections.emptyList());
    OvalStateManager ovalStateManager = new OvalStateManager(Collections.emptyList());
    OvalTestManager ovalTestManager = new OvalTestManager(Collections.emptyList());

    TestType t1;
    TestType t2;
    TestType t3;
    TestType t4;
    TestType t5;
    TestType t6;

    @BeforeEach
    void setUp() {
        ovalObjectManager = new OvalObjectManager(Collections.emptyList());
        ovalStateManager = new OvalStateManager(Collections.emptyList());
        ovalTestManager = new OvalTestManager(Collections.emptyList());

        List<UyuniAPI.CVEPatchStatus> systemCvePatchStatusList = List.of(
                new UyuniAPI.CVEPatchStatus(1, Optional.of("libsoftokn3-hmac-32bit"),
                        Optional.of(UyuniAPI.PackageEvr.parseRpm("0:3.68.3-150400.1.7")), true),
                new UyuniAPI.CVEPatchStatus(1, Optional.of("libsha1detectcoll1"),
                        Optional.of(UyuniAPI.PackageEvr.parseRpm("0:3.68.2-150400.1.7")), true),
                new UyuniAPI.CVEPatchStatus(1, Optional.of("libsha1detectcoll1"),
                        Optional.of(UyuniAPI.PackageEvr.parseRpm("0:3.68.3-150400.1.7")), true),
                new UyuniAPI.CVEPatchStatus(1, Optional.of("libsha1detectcoll1"),
                        Optional.of(UyuniAPI.PackageEvr.parseRpm("0:3.68.4-150400.1.7")), true)
        );

        ObjectType o1 = newObjectType("obj:1", "libsoftokn3-hmac-32bit");
        ObjectType o2 = newObjectType("obj:2", "libsha1detectcoll1");

        StateType s1 = newStateType("ste:1", "0:3.68.3-150400.1.7", OperationEnumeration.LESS_THAN);
        StateType s2 = newStateType("ste:2", "0:3.68.3-150400.1.7", OperationEnumeration.GREATER_THAN);
        StateType s3 = newStateType("ste:3", "0:3.68.3-150400.1.7", OperationEnumeration.EQUALS);

        StateType s4 = newStateType("ste:4", "0:3.68.3-150400.1.7", OperationEnumeration.NOT_EQUAL);

        t1 = newTestType("tst:1", o1, s1);
        t2 = newTestType("tst:2", o1, s2);
        t3 = newTestType("tst:3", o1, s3);
        t4 = newTestType("tst:4", o2, s1);
        t5 = newTestType("tst:5", o2, s2);
        t6 = newTestType("tst:6", o2, s3);

        testEvaluator = new TestEvaluator(ovalTestManager, ovalObjectManager, ovalStateManager, systemCvePatchStatusList);
    }

    /**
     * Test T1 ensures that if the evr state operation is LESS_THAN and the system has a package with an evr less than the
     * state evr, then the evaluation should return 'true'
     */
    @Test
    void testT1() {
        assertFalse(testEvaluator.evaluate(t1.getId()));
    }

    @Test
    void testT2() {
        assertFalse(testEvaluator.evaluate(t2.getId()));
    }

    @Test
    void testT3() {
        assertTrue(testEvaluator.evaluate(t3.getId()));
    }

    @Test
    void testT4() {
        assertTrue(testEvaluator.evaluate(t4.getId()));
    }

    @Test
    void testT5() {
        assertTrue(testEvaluator.evaluate(t5.getId()));
    }

    @Test
    void testT6() {
        assertTrue(testEvaluator.evaluate(t6.getId()));
    }

    TestType newTestType(String id, ObjectType object, List<StateType> states) {
        ObjectRefType objectRefType = new ObjectRefType();
        objectRefType.setObjectRef(object.getId());

        List<StateRefType> stateRefs = states.stream().map(state -> {
            StateRefType stateRefType = new StateRefType();
            stateRefType.setStateRef(state.getId());
            return stateRefType;
        }).collect(Collectors.toList());

        TestType testType = new TestType();
        testType.setId(id);
        testType.setObject(objectRefType);
        testType.setStates(stateRefs);

        ovalTestManager.add(testType);

        return testType;
    }

    TestType newTestType(String id, ObjectType object, StateType state) {
        return newTestType(id, object, List.of(state));
    }

    ObjectType newObjectType(String id, String packageName) {
        ObjectRefType objectRefType = new ObjectRefType();
        objectRefType.setObjectRef(id);

        ObjectType object = new ObjectType();
        object.setId(objectRefType.getObjectRef());
        object.setPackageName(packageName);

        ovalObjectManager.add(object);

        return object;
    }

    StateType newStateType(String id, String evr, OperationEnumeration evrOperation, EVRDataTypeEnum evrDatatype) {
        StateRefType stateRefType = new StateRefType();
        stateRefType.setStateRef(id);

        EVRType evrType = new EVRType();
        evrType.setDatatype(EVRDataTypeEnum.RPM_EVR);
        evrType.setOperation(evrOperation);
        evrType.setValue(evr);

        StateType state = new StateType();
        state.setId(stateRefType.getStateRef());
        state.setEvr(evrType);

        ovalStateManager.add(state);

        return state;
    }

    StateType newStateType(String id, String evr, OperationEnumeration evrOperation) {
        return newStateType(id, evr, evrOperation, EVRDataTypeEnum.RPM_EVR);
    }
}
