package com.suse.db;

import com.suse.ovaltypes.FamilyEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "affected_products")
public class AffectedProductEntity {
    @EmbeddedId
    private Id id;
    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "definition_id")
    private DefinitionEntity definition;

    private FamilyEnum family;

    public Id getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public DefinitionEntity getDefinition() {
        return definition;
    }

    public FamilyEnum getFamily() {
        return family;
    }

    public static class Id implements Serializable {
        @Column(name = "definition_id")
        private String definitionId;
        @Column(name = "product_id")
        private Long productId;

        public String getDefinitionId() {
            return definitionId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setDefinitionId(String definitionId) {
            this.definitionId = definitionId;
        }

        public void setProductId(Long productId) {
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
