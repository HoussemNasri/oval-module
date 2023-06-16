package com.suse.db;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("JpaModelReferenceInspection")
@Entity
public class Definition_CVE {

    @EmbeddedId
    private Definition_CVE.Id id;
    @MapsId("cveId")
    @ManyToOne
    @JoinColumn(name = "cve_id")
    private CVE cve;

    @MapsId("definitionId")
    @ManyToOne
    @JoinColumn(name = "definition_id")
    private Definition definition;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public CVE getCve() {
        return cve;
    }

    public void setCve(CVE cve) {
        this.cve = cve;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "definition_id")
        private String definitionId;

        @Column(name = "cve_id")
        private String cveId;
    }


}
