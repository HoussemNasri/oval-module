package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        for (StateRefType stateRef : test.getState()) {
            StateType state = ovalStateManager.get(stateRef.getStateRef());
            System.out.println(state.getId());
            System.out.println("Priting evr: " + state.getEvr().getValue());

            //TODO: update!
            return checkPackageAgainstState(object, state);
        }
        return false;
    }

    /*
    * 	<rpminfo_test id="oval:org.opensuse.security:tst:2009685834" version="1" comment="libsoftokn3-hmac-32bit is &lt;3.68.3-150400.1.7" check="at least one" xmlns="http://oval.mitre.org/XMLSchema/oval-definitions-5#linux">
		    <object object_ref="oval:org.opensuse.security:obj:2009038248"/>
		    <state state_ref="oval:org.opensuse.security:ste:2009161445"/>
	    </rpminfo_test>

	    <rpminfo_object id="oval:org.opensuse.security:obj:2009038248" version="1" xmlns="http://oval.mitre.org/XMLSchema/oval-definitions-5#linux">
		    <name>libsoftokn3-hmac-32bit</name>
	    </rpminfo_object>

        <rpminfo_state id="oval:org.opensuse.security:ste:2009161445" version="1" xmlns="http://oval.mitre.org/XMLSchema/oval-definitions-5#linux">
            <evr datatype="evr_string" operation="less than">0:3.68.3-150400.1.7</evr>
        </rpminfo_state>

    * */

    private boolean checkPackageAgainstState(ObjectType packageObject, StateType expectedState) {
        String packageName = packageObject.getName();

        return systemCvePatchStatusList.stream()
                .filter(cvePatchStatus -> Optional.ofNullable(packageName).equals(cvePatchStatus.getPackageName()))
                .map(UyuniAPI.CVEPatchStatus::getPackageEvr)
                .anyMatch(evrOpt -> {
                    System.out.println("Found package!");
                    if (evrOpt.isPresent()) {
                        EVRType evrType = expectedState.getEvr();
                        UyuniAPI.PackageEvr packageOnSystemEVR = evrOpt.get();
                        UyuniAPI.PackageEvr packageOnOvalEVR = UyuniAPI.PackageEvr.parsePackageEvr(toPackageType(evrType.getDatatype()), evrType.getValue());

                        return packageOnSystemEVR.compareTo(packageOnOvalEVR) < 0;
                    }
                    return false;
                });
    }

    private UyuniAPI.PackageType toPackageType(EVRDataTypeEnum evrDataTypeEnum) {
        Objects.requireNonNull(evrDataTypeEnum);
        return evrDataTypeEnum == EVRDataTypeEnum.DEBIAN_EVR ? UyuniAPI.PackageType.DEB : UyuniAPI.PackageType.RPM;
    }
}
