package com.suse.db;

import com.suse.ovaltypes.OperationEnumeration;

import javax.persistence.*;

@Entity
@Table(name = "package_evr_state_entity")
public class PackageVersionStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String version;
    @Enumerated(EnumType.STRING)
    private OperationEnumeration operation;

    public Integer getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OperationEnumeration getOperation() {
        return operation;
    }

    public void setOperation(OperationEnumeration operation) {
        this.operation = operation;
    }
}
