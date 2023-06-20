package com.suse.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_vulnerable_packages")
public class ProductVulnerablePackages {
    @EmbeddedId
    private Id id;
    @MapsId(value = "productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @MapsId("cve")
    @ManyToOne
    @JoinColumn(name = "cve")
    private CVE cve;
    @MapsId("vulnerablePackageId")
    @ManyToOne
    @JoinColumn(name = "vulnerable_package_id")
    private VulnerablePackage vulnerablePackage;


    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CVE getCve() {
        return cve;
    }

    public void setCve(CVE cve) {
        this.cve = cve;
    }

    public VulnerablePackage getVulnerablePackage() {
        return vulnerablePackage;
    }

    public void setVulnerablePackage(VulnerablePackage vulnerablePackage) {
        this.vulnerablePackage = vulnerablePackage;
    }

    @Embeddable
    public static class Id implements Serializable {
        private String productId;
        private String cve;
        private Long vulnerablePackageId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCve() {
            return cve;
        }

        public void setCve(String cve) {
            this.cve = cve;
        }

        public Long getVulnerablePackageId() {
            return vulnerablePackageId;
        }

        public void setVulnerablePackageId(Long vulnerablePackageId) {
            this.vulnerablePackageId = vulnerablePackageId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id = (Id) o;
            return Objects.equals(productId, id.productId) && Objects.equals(cve, id.cve) && Objects.equals(vulnerablePackageId, id.vulnerablePackageId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, cve, vulnerablePackageId);
        }
    }
}
