package com.suse.db;

import com.suse.ovaltypes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Override
    public List<String> getAffectedProducts(String cve) {
        List<AffectedProduct> affectedProducts = session
                .createNamedQuery("AffectedProduct.getAffectedProductsByCVE", AffectedProduct.class)
                .getResultList();

        return affectedProducts.stream().map(ap -> ap.getProduct().getName()).collect(Collectors.toList());
    }

    @Override
    public List<Definition> getPatchDefinitions(String cve) {
        return session.createNamedQuery("Definition.getPatchDefinitions", Definition.class)
                .setParameter("cve", cve)
                .getResultList();
    }

    @Override
    public Optional<Definition> getVulnerabilityDefinition(String cve) {
        return Optional.empty();
    }

    private void saveDefinition(DefinitionType definitionType) {
        Definition definition = new Definition();
        definition.setId(definitionType.getId());
        definition.setTitle(definitionType.getMetadata().getTitle());
        definition.setVersion(definitionType.getVersion().intValue());
        definition.setDescription(definitionType.getMetadata().getDescription());
        definition.setDefClass(definitionType.getDefinitionClass());

        List<Reference> references = saveReferences(definitionType.getMetadata().getReference());
        List<CVE> cves = saveCves(definitionType, definition);
        definition.setCves(cves);

        definition.setReferences(references);

        session.merge(definition);

        saveAffectedProducts(definition, definitionType.getMetadata().getAffected().get(0).getPlatforms());
    }

    private List<AffectedProduct> saveAffectedProducts(Definition definition, List<String> affectedPlatforms) {
        List<AffectedProduct> affectedProducts =
                affectedPlatforms.stream().map(productName -> {
                    Product product = saveProduct(productName);

                    AffectedProduct affectedProduct = new AffectedProduct();
                    affectedProduct.setDefinition(definition);
                    affectedProduct.setProduct(product);
                    affectedProduct.setId(new AffectedProduct.Id());

                    return affectedProduct;
                }).collect(Collectors.toList());

        affectedProducts.forEach(session::merge);

        return affectedProducts;
    }

    private Product saveProduct(String productName) {
        Product product = new Product();
        product.setName(productName);
        session.merge(product);
        return product;
    }

    private List<CVE> saveCves(DefinitionType definitionType, Definition definition) {
        List<CVE> cves = new ArrayList<>();

        if (definition.getDefClass() == DefinitionClassEnum.VULNERABILITY) {
            cves.add(extractCveFromVulnerabilityDefinition(definitionType));
        } else if (definition.getDefClass() == DefinitionClassEnum.PATCH) {
            cves.addAll(extractCvesFromPatchDefinition(definitionType));
        } else {
            throw new IllegalStateException("The '" + definition.getDefClass() + "' definition class in not supported");
        }

        cves.forEach(session::merge);

        return cves;
    }

    private List<Reference> saveReferences(List<ReferenceType> referenceTypes) {
        List<Reference> references = referenceTypes.stream()
                .map(this::mapReferenceTypeToReference)
                .collect(Collectors.toList());

        references.forEach(session::merge);

        return references;
    }

    private Reference mapReferenceTypeToReference(ReferenceType referenceType) {
        Reference reference = new Reference();
        reference.setId(referenceType.getRefId());
        reference.setUrl(referenceType.getRefUrl().orElse(""));
        reference.setSource(referenceType.getSource());

        return reference;
    }

    public CVE extractCveFromVulnerabilityDefinition(DefinitionType definitionType) {
        assert definitionType.getDefinitionClass() == DefinitionClassEnum.VULNERABILITY;

        CVE cve = new CVE();
        cve.setCveId(definitionType.getMetadata().getTitle());

        return cve;
    }

    public List<CVE> extractCvesFromPatchDefinition(DefinitionType definitionType) {
        assert definitionType.getDefinitionClass() == DefinitionClassEnum.PATCH;

        return definitionType.getMetadata().getReference().stream()
                .filter(ref -> "CVE".equals(ref.getSource()))
                .map(ReferenceType::getRefId)
                .map(cveId -> {
                    CVE cve = new CVE();
                    cve.setCveId(cveId);
                    return cve;
                })
                .collect(Collectors.toList());
    }

}
