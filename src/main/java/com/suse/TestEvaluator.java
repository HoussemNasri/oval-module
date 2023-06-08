package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.ovaltypes.*;

import java.util.*;
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
                .map(state -> evaluatePackageState(packageVersionsOnSystem, state))
                .collect(Collectors.toList());

        return combineBooleans(test.getStateOperator(), stateEvaluations);
    }

    private boolean evaluatePackageState(List<UyuniAPI.CVEPatchStatus> packageVersionsOnSystem, StateType expectedState) {
        return packageVersionsOnSystem.stream()
                .anyMatch(cvePatchStatus -> {
                    Optional<UyuniAPI.PackageEvr> evrOptional = cvePatchStatus.getPackageEvr();

                    if (evrOptional.isEmpty()) {
                        return false;
                    }

                    List<Boolean> stateEntitesEvaluations = new ArrayList<>();

                    EVRType expectedEvr = expectedState.getEvr();
                    if (expectedEvr != null) {
                        UyuniAPI.PackageEvr packageOnSystemEVR = evrOptional.get();
                        UyuniAPI.PackageEvr packageOnOvalEVR =
                                UyuniAPI.PackageEvr.parsePackageEvr(toPackageType(expectedEvr.getDatatype()), expectedEvr.getValue());

                        int evrComparison = packageOnSystemEVR.compareTo(packageOnOvalEVR);

                        stateEntitesEvaluations.add(checkPackageEVR(evrComparison, expectedEvr.getOperation()));

                        System.out.println("Comparison between " + packageOnSystemEVR + " and " + packageOnOvalEVR + " yielded value " + evrComparison);
                        System.out.println("------");
                    }


                    ArchType expectedArch = expectedState.getArch();
                    if (expectedArch != null) {
                        stateEntitesEvaluations.add(
                                checkPackageArch(cvePatchStatus.getPackageArch().orElse(""), expectedArch.getValue(),
                                        expectedArch.getOperation()));
                    }

                    return combineBooleans(expectedState.getOperator(), stateEntitesEvaluations);
                });
    }

    private boolean checkPackageEVR(int evrComparisonResult, OperationEnumeration operation) {
        return (evrComparisonResult == 0 && operation == OperationEnumeration.EQUALS) ||
                (evrComparisonResult != 0 && operation == OperationEnumeration.NOT_EQUAL) ||
                (evrComparisonResult > 0 && operation == OperationEnumeration.GREATER_THAN) ||
                (evrComparisonResult >= 0 && operation == OperationEnumeration.GREATER_THAN_OR_EQUAL) ||
                (evrComparisonResult < 0 && operation == OperationEnumeration.LESS_THAN) ||
                (evrComparisonResult <= 0 && operation == OperationEnumeration.LESS_THAN_OR_EQUAL);
    }

    private boolean checkPackageArch(String systemPackageArch, String expectedArch, OperationEnumeration operation) {
        switch (operation) {
            case PATTERN_MATCH:
                return systemPackageArch.matches(expectedArch);
            case EQUALS:
                return systemPackageArch.equals(expectedArch);
            case NOT_EQUAL:
                return !systemPackageArch.equals(expectedArch);
            default:
                throw new IllegalArgumentException("The specified operation is not supported");
        }
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

    private boolean combineBooleans(LogicOperatorType operator, List<Boolean> booleans) {
        switch (operator) {
            case AND:
                return booleans.stream().allMatch(Boolean::booleanValue);
            case OR:
                return booleans.stream().anyMatch(Boolean::booleanValue);
            case XOR:
                return booleans.stream().reduce((a, b) -> a ^ b).orElse(false);
            case ONE:
                return booleans.stream().filter(Boolean::booleanValue).count() == 1L;
        }
        return false;
    }

    private boolean combineBooleans(LogicOperatorType operator, Boolean... evaluations) {
        return combineBooleans(operator, Arrays.asList(evaluations));
    }
}
