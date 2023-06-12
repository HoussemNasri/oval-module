package com.suse.db;

import com.suse.ovaltypes.OperationEnumeration;

import javax.persistence.*;

@Entity
@Table(name = "package_arch_state_entity")
public class PackageArchStateEntity {
    @Id
    private Integer id;
    private String arch;
    @Enumerated(EnumType.STRING)
    private OperationEnumeration operation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public OperationEnumeration getOperation() {
        return operation;
    }

    public void setOperation(OperationEnumeration operation) {
        this.operation = operation;
    }
}
