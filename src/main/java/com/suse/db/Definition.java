package com.suse.db;

import com.suse.ovaltypes.DefinitionClassEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "oval_definition")
public class Definition {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "class")
    private DefinitionClassEnum defClass;
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
    private Set<Reference> references;

    @ManyToMany
    @JoinTable(
            name = "definition_cve",
            joinColumns = {@JoinColumn(name = "definition_id")},
            inverseJoinColumns = {@JoinColumn(name = "cve_id")}
    )
    private Set<Reference> cves;

    @OneToMany(mappedBy = "definition")
    private Set<AffectedProduct> affectedProducts;


    public Definition() {

    }

    public Definition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DefinitionClassEnum getDefClass() {
        return defClass;
    }

    public void setDefClass(DefinitionClassEnum defClass) {
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

    public Set<Reference> getReferences() {
        return references;
    }

    public Set<AffectedProduct> getAffectedProducts() {
        return affectedProducts;
    }

    public Set<Reference> getCves() {
        return cves;
    }
}
