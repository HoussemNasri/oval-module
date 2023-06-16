package com.suse.db;

import com.suse.ovaltypes.FamilyEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "AffectedProduct.getAffectedProductsByCVE",
                query = "SELECT ap " +
                        "FROM AffectedProduct ap " +
                        "JOIN ap.definition d " +
                        "JOIN ap.product " +
                        "JOIN d.cves cve_definition " +
                        "WHERE cve_definition.cve.cveId = 'CVE-2022-38751' "
        )
})
@Entity
@Table(name = "affected_products")
public class AffectedProduct {
    @EmbeddedId
    private Id id;
    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @MapsId("definitionId")
    @ManyToOne
    @JoinColumn(name = "definition_id")
    private Definition definition;

    private FamilyEnum family;

    public Id getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Definition getDefinition() {
        return definition;
    }

    public FamilyEnum getFamily() {
        return family;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setFamily(FamilyEnum family) {
        this.family = family;
    }

    public static class Id implements Serializable {
        @Column(name = "definition_id")
        private String definitionId;
        @Column(name = "product_id")
        private String productId;

        public String getDefinitionId() {
            return definitionId;
        }

        public String getProductId() {
            return productId;
        }

        public void setDefinitionId(String definitionId) {
            this.definitionId = definitionId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id = (Id) o;
            return Objects.equals(definitionId, id.definitionId) && Objects.equals(productId, id.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(definitionId, productId);
        }
    }
}
