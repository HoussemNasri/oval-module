package com.suse.db;

import com.suse.ovaltypes.DefinitionClassEnum;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.List;
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
    @Column(length = 10_000)
    private String description;
    private Integer version;

    @ManyToMany
    @JoinTable(
            name = "definition_references",
            joinColumns = {@JoinColumn(name = "definition_id")},
            inverseJoinColumns = {@JoinColumn(name = "reference_id")}
    )
    private List<Reference> references;

    @ManyToMany
    @JoinTable(
            name = "definition_cve",
            joinColumns = {@JoinColumn(name = "definition_id")},
            inverseJoinColumns = {@JoinColumn(name = "cve_id")}
    )
    private List<CVE> cves;

    @OneToMany(mappedBy = "definition")
    private List<AffectedProduct> affectedProducts;


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

    public List<Reference> getReferences() {
        return references;
    }

    public List<AffectedProduct> getAffectedProducts() {
        return affectedProducts;
    }

    public List<CVE> getCves() {
        return cves;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public void setCves(List<CVE> cves) {
        this.cves = cves;
    }

    public void setAffectedProducts(List<AffectedProduct> affectedProducts) {
        this.affectedProducts = affectedProducts;
    }
}
