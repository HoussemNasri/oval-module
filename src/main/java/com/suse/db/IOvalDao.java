package com.suse.db;

import com.suse.ovaltypes.DefinitionType;
import com.suse.ovaltypes.ObjectType;
import com.suse.ovaltypes.StateType;
import com.suse.ovaltypes.TestType;

import java.util.List;
import java.util.Optional;

public interface IOvalDao {
    void insertObjects(List<ObjectType> objects);

    void insertStates(List<StateType> states);

    void insertTests(List<TestType> tests);

    void insertDefinitions(List<DefinitionType> definitions);

    List<String> getAffectedProducts(String cve);

    List<Definition> getPatchDefinitions(String cve);

    Optional<Definition> getVulnerabilityDefinition(String cve);

}
