package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestEvaluator {
    private final OvalTestManager ovalTestManager;
    private final OvalObjectManager ovalObjectManager;
    private final OvalStateManager ovalStateManager;
    private final List<UyuniAPI.CVEPatchStatus> systemCvePatchStatusList;

    public TestEvaluator(OvalTestManager ovalTestManager, OvalObjectManager ovalObjectManager, OvalStateManager ovalStateManager, List<UyuniAPI.CVEPatchStatus> systemCvePatchStatusList) {
        Objects.requireNonNull(ovalTestManager);

        this.ovalStateManager = ovalStateManager;
        this.ovalObjectManager = ovalObjectManager;
        this.systemCvePatchStatusList = systemCvePatchStatusList == null ? new ArrayList<>() : systemCvePatchStatusList;
        this.ovalTestManager = ovalTestManager;
    }

    public boolean evaluate(String testId) {
        TestType test = ovalTestManager.get(testId);
        if (test.getObject() == null) {
            throw new IllegalStateException();
        }

        ObjectType object = ovalObjectManager.get(test.getObject().getObjectRef());
        List<UyuniAPI.CVEPatchStatus> packageVersionsOnSystem = listPackageVersionsInstalledOnSystem(object.getPackageName());
        long packageVersionsCount = packageVersionsOnSystem.size();

        ExistenceEnum checkExistence = test.getCheckExistence();
        switch (checkExistence) {
            case NONE_EXIST:
                if (packageVersionsCount != 0) {
                    return false;
                }
                break;
            case ONLY_ONE_EXISTS:
                if (packageVersionsCount != 1) {
                    return false;
                }
                break;
            // We have only one component under consideration that is the package name,
            // thus 'all_exist'and 'at_least_one_exists' are logically equivalent.
            case ALL_EXIST:
            case AT_LEAST_ONE_EXISTS:
                if (packageVersionsCount < 1) {
                    return false;
                }
                break;
        }

        List<StateRefType> ovalStates = test.getStates();
        if (ovalStates.isEmpty()) {
            return true;
        }

        System.out.println(ovalStates.get(0).getStateRef());
        System.out.println("Operation: " + ovalStateManager.get(ovalStates.get(0).getStateRef()).getEvr().getOperation());
        System.out.println("Datatype: " + ovalStateManager.get(ovalStates.get(0).getStateRef()).getEvr().getDatatype());
        System.out.println("Value: " + ovalStateManager.get(ovalStates.get(0).getStateRef()).getEvr().getValue());

        List<Boolean> stateEvaluations = ovalStates.stream()
                .map(StateRefType::getStateRef)
                .map(ovalStateManager::get)
                .map(state -> evaluatePackageState(packageVersionsOnSystem, state)).collect(Collectors.toList());

        return combineStateEvaluations(test.getStateOperator(), stateEvaluations);
    }

    private boolean evaluatePackageState(List<UyuniAPI.CVEPatchStatus> packageVersionsOnSystem, StateType expectedState) {
        return packageVersionsOnSystem.stream()
                .map(UyuniAPI.CVEPatchStatus::getPackageEvr)
                .anyMatch(evrOptional -> {
                    if (evrOptional.isEmpty()) {
                        return false;
                    }

                    EVRType evrType = expectedState.getEvr();
                    OperationEnumeration operation = evrType.getOperation();
                    UyuniAPI.PackageEvr packageOnSystemEVR = evrOptional.get();
                    UyuniAPI.PackageEvr packageOnOvalEVR =
                            UyuniAPI.PackageEvr.parsePackageEvr(toPackageType(evrType.getDatatype()), evrType.getValue());

                    int comparison = packageOnSystemEVR.compareTo(packageOnOvalEVR);

                    System.out.println(packageOnSystemEVR);
                    System.out.println(comparison);
                    System.out.println("------");

                    return (comparison == 0 && operation == OperationEnumeration.EQUALS) ||
                            (comparison != 0 && operation == OperationEnumeration.NOT_EQUAL) ||
                            (comparison > 0 && operation == OperationEnumeration.GREATER_THAN) ||
                            (comparison >= 0 && operation == OperationEnumeration.GREATER_THAN_OR_EQUAL) ||
                            (comparison < 0 && operation == OperationEnumeration.LESS_THAN) ||
                            (comparison <= 0 && operation == OperationEnumeration.LESS_THAN_OR_EQUAL);
                });
    }

    private boolean evaluatePackageEVR() {
        return false;
    }

    private boolean evaluatePackageArch() {
        return false;
    }

    private boolean combineStateEvaluations(LogicOperatorType stateOperator, List<Boolean> stateEvaluations) {
        switch (stateOperator) {
            case AND:
                return stateEvaluations.stream().allMatch(Boolean::booleanValue);
            case OR:
                return stateEvaluations.stream().anyMatch(Boolean::booleanValue);
            case XOR:
                return stateEvaluations.stream().reduce((a, b) -> a ^ b).orElse(false);
            case ONE:
                return stateEvaluations.stream().filter(Boolean::booleanValue).count() == 1L;
        }
        return false;
    }

    private List<UyuniAPI.CVEPatchStatus> listPackageVersionsInstalledOnSystem(String packageName) {
        return systemCvePatchStatusList.stream()
                .filter(cvePatchStatus -> Optional.ofNullable(packageName).equals(cvePatchStatus.getPackageName()))
                .collect(Collectors.toList());
    }

    private UyuniAPI.PackageType toPackageType(EVRDataTypeEnum evrDataTypeEnum) {
        Objects.requireNonNull(evrDataTypeEnum);
        return evrDataTypeEnum == EVRDataTypeEnum.DEBIAN_EVR ? UyuniAPI.PackageType.DEB : UyuniAPI.PackageType.RPM;
    }
}
