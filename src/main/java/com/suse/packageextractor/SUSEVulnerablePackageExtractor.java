package com.suse.packageextractor;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.ovaltypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SUSEVulnerablePackageExtractor extends AbstractVulnerablePackagesExtractor {

    public SUSEVulnerablePackageExtractor(DefinitionType vulnerabilityDefinition, OvalObjectManager ovalObjectManager, OvalTestManager ovalTestManager, OvalStateManager ovalStateManager) {
        super(vulnerabilityDefinition, ovalObjectManager, ovalTestManager, ovalStateManager);
    }

    @Override
    protected List<ProductVulnerablePackages> extractItem(CriteriaType criteriaType) {
        BaseCriteria productCriteriaRootNode = criteriaType.getChildren().get(0);
        BaseCriteria packageCriteriaRootNode = criteriaType.getChildren().get(1);

        List<CriterionType> productCriterions = collectCriterions(productCriteriaRootNode);

        List<String> products = new ArrayList<>();
        for (CriterionType productCriterion : productCriterions) {
            String comment = productCriterion.getComment();
            String product = comment.replace(" is installed", "");
            products.add(product);
        }

        List<CriterionType> filteredPackageCriterions = collectCriterions(packageCriteriaRootNode, 1)
                .stream()
                .filter(c ->
                        c.getComment().endsWith("is installed") ||
                        c.getComment().endsWith("is affected") ||
                        c.getComment().endsWith("is not affected"))
                .collect(Collectors.toList());

        List<VulnerablePackage> vulnerablePackages = new ArrayList<>();
        for (CriterionType packageCriterion : filteredPackageCriterions) {
            String comment = packageCriterion.getComment();
            String testId = packageCriterion.getTestRef();

            TestType testType = ovalTestManager.get(testId);
            Optional<StateType> stateType = testType.getStateRef().map(ovalStateManager::get);
            ObjectType objectType = ovalObjectManager.get(testType.getObject().getObjectRef());

            String packageName = objectType.getPackageName();

            VulnerablePackage vulnerablePackage = new VulnerablePackage();
            vulnerablePackage.setName(packageName);

            if (stateType.isEmpty()) {
                throw new IllegalStateException("Found an empty state");
            }

            if (comment.endsWith("is installed")) {
                String evr = stateType.get().getPackageEVR().getValue();
                vulnerablePackage.setFixVersion(evr);
            } else if (comment.endsWith("is affected")) {
                // Affected packages don't have a fix version yet.
                vulnerablePackage.setFixVersion(null);
            } else if (comment.endsWith("is not affected")) {
                // Package 'is not affected' implies that the vulnerability is too old that all supported products
                // have the fixed package version or that a fix was backported to vulnerable products. Hence, the fix version
                // is 0.
                vulnerablePackage.setFixVersion("0:0-0");
            }

            vulnerablePackages.add(vulnerablePackage);
        }

        List<ProductVulnerablePackages> result = new ArrayList<>();
        for (String product : products) {
            ProductVulnerablePackages vulnerableProduct = new ProductVulnerablePackages();
            // TODO: This might not always be the case
            vulnerableProduct.setCve(vulnerabilityDefinition.getMetadata().getTitle());
            vulnerableProduct.setProduct(product);
            vulnerableProduct.setVulnerablePackages(vulnerablePackages);

            result.add(vulnerableProduct);
        }

        return result;
    }

    @Override
    protected boolean test(CriteriaType criteria) {
        boolean hasTwoChildren = criteria.getChildren().size() == 2;
        boolean hasOperatorAND = criteria.getOperator() == LogicOperatorType.AND;

        if (!(hasOperatorAND && hasTwoChildren)) {
            return false;
        }

        BaseCriteria productCriteriaRootNode = criteria.getChildren().get(0);
        List<CriterionType> productCriterions = collectCriterions(productCriteriaRootNode);

        if (productCriterions.isEmpty()) {
            return false;
        }

        boolean allProductsAffected = true;
        for (CriterionType productCriterion : productCriterions) {
            String comment = productCriterion.getComment();
            String product = comment.replace(" is installed", "");

            if (vulnerabilityDefinition.getMetadata().getAffected().get(0).getPlatforms().stream().noneMatch(product::equals)) {
                allProductsAffected = false;
                break;
            }
        }

        return allProductsAffected;
    }
}
