package com.suse.db;

import com.suse.ovaltypes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OvalDaoImpl implements IOvalDao {
    private final Session session;

    public OvalDaoImpl(Session session) {
        this.session = session;
    }

    private void saveObject(ObjectType objectType) {
        PackageObject packageObject = new PackageObject();
        packageObject.setId(objectType.getId());
        packageObject.setComment(objectType.getComment());
        packageObject.setPackageName(objectType.getPackageName());
        packageObject.setRpm(objectType.isRpm());

        session.saveOrUpdate(packageObject);
    }

    public void insertObjects(List<ObjectType> objects) {
        Transaction transaction = session.beginTransaction();

        objects.forEach(this::saveObject);

        transaction.commit();
    }

    public void saveState(StateType stateType) {
        PackageState packageState = new PackageState();
        packageState.setId(stateType.getId());
        packageState.setComment(stateType.getComment());
        // TODO: fix!
        packageState.setRpm(true);
        packageState.setOperator(stateType.getOperator());

        if (stateType.getPackageEVR() != null) {
            PackageEvrStateEntity packageEvrState = saveEvrStateEntity(stateType.getPackageEVR());
            packageState.setPackageEvrState(packageEvrState);
        }

        if (stateType.getPackageArch() != null) {
            PackageArchStateEntity packageArchState = saveArchStateEntity(stateType.getPackageArch());
            packageState.setPackageArchState(packageArchState);
        }

        if (stateType.getPackageVersion() != null) {
            PackageVersionStateEntity packageVersionState = saveVersionStateEntity(stateType.getPackageVersion());
            packageState.setPackageVersionState(packageVersionState);
        }

        session.saveOrUpdate(packageState);
    }

    public void insertStates(List<StateType> states) {
        Transaction transaction = session.beginTransaction();

        states.forEach(this::saveState);

        session.flush();
        transaction.commit();
    }

    private PackageEvrStateEntity saveEvrStateEntity(EVRType evrType) {
        Objects.requireNonNull(evrType);

        PackageEvrStateEntity packageEvrState = new PackageEvrStateEntity();
        packageEvrState.setDatatype(evrType.getDatatype());
        packageEvrState.setEvr(evrType.getValue());
        packageEvrState.setOperation(evrType.getOperation());

        int id = (int) session.save(packageEvrState);

        packageEvrState.setId(id);

        return packageEvrState;
    }

    private PackageVersionStateEntity saveVersionStateEntity(VersionType versionType) {
        Objects.requireNonNull(versionType);

        PackageVersionStateEntity packageVersionState = new PackageVersionStateEntity();
        packageVersionState.setVersion(versionType.getValue());
        packageVersionState.setOperation(versionType.getOperation());

        int id = (int) session.save(packageVersionState);

        packageVersionState.setId(id);

        return packageVersionState;
    }

    private PackageArchStateEntity saveArchStateEntity(ArchType archType) {
        Objects.requireNonNull(archType);

        PackageArchStateEntity packageArchState = new PackageArchStateEntity();
        packageArchState.setArch(archType.getValue());
        packageArchState.setOperation(archType.getOperation());

        int id = (int) session.save(packageArchState);

        packageArchState.setId(id);

        return packageArchState;
    }

    private void saveTest(TestType test) {
        PackageTest packageTest = new PackageTest();
        packageTest.setId(test.getId());
        packageTest.setComment(test.getComment());
        packageTest.setCheck(test.getCheck());
        packageTest.setCheckExistence(test.getCheckExistence());

        PackageObject packageObject = session.byId(PackageObject.class).load(test.getObject().getObjectRef());
        packageTest.setPackageObject(packageObject);

        List<PackageState> packageStates = new ArrayList<>();
        for (StateRefType stateRef : test.getStates()) {
            PackageState packageState = session.byId(PackageState.class).load(stateRef.getStateRef());
            packageStates.add(packageState);
        }
        packageTest.setPackageStates(packageStates);

        session.saveOrUpdate(packageTest);
    }

    public void insertTests(List<TestType> tests) {
        Transaction transaction = session.beginTransaction();

        tests.forEach(this::saveTest);

        transaction.commit();
    }

    @Override
    public void insertDefinitions(List<DefinitionType> definitions) {
        Transaction transaction = session.beginTransaction();

        definitions.forEach(this::saveDefinition);

        transaction.commit();
    }

    private void saveDefinition(DefinitionType definitionType) {
        Definition definition = new Definition();
        definition.setId(definitionType.getId());
        definition.setTitle(definitionType.getMetadata().getTitle());
        definition.setVersion(definitionType.getVersion().intValue());
        definition.setDescription(definitionType.getMetadata().getDescription());
        definition.setDefClass(definitionType.getDefinitionClass());

        List<Reference> references = definitionType.getMetadata().getReference().stream().map(referenceType -> {
            Reference reference = new Reference();
            reference.setId(referenceType.getRefId());
            reference.setUrl(referenceType.getRefUrl().orElse(""));
            reference.setSource(referenceType.getSource());

            return reference;
        }).collect(Collectors.toList());

        references.forEach(session::merge);

        definition.setReferences(references);

        session.saveOrUpdate(definition);
    }

}
