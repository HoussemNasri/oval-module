package com.suse.db;

import javax.persistence.*;

@Entity
@Table(name = "cve")
public class CVE {
    @Id
    private String cveId;

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }
}
