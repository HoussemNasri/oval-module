package com.suse.db;

import javax.persistence.*;
import java.io.Serializable;

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

    public Id getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public DefinitionEntity getDefinition() {
        return definition;
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
    }
}
