package com.suse.db;

import com.suse.model.DefinitionClass;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "oval_definition")
public class DefinitionEntity {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private DefinitionClass defClass;
    private String title;
    private String description;
    private Integer version;
    private Boolean deprecated;

    @ManyToMany
    @JoinTable(
            name = "definition_references",
            joinColumns = {@JoinColumn(name = "definition_id")},
            inverseJoinColumns = {@JoinColumn(name = "reference_id")}
    )
    private Set<ReferenceEntity> references;

    @OneToMany(mappedBy = "definition")
    private Set<AffectedProductEntity> affectedProducts;


    public DefinitionEntity() {

    }

    public DefinitionEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DefinitionClass getDefClass() {
        return defClass;
    }

    public void setDefClass(DefinitionClass defClass) {
        this.defClass = defClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }
}
