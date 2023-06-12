package com.suse.db;


import com.suse.ovaltypes.EVRDataTypeEnum;
import com.suse.ovaltypes.OperationEnumeration;

import javax.persistence.*;

@Entity
@Table(name = "package_evr_state_entity")
public class PackageEvrStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String evr;
    @Enumerated(EnumType.STRING)
    EVRDataTypeEnum datatype;
    @Enumerated(EnumType.STRING)
    OperationEnumeration operation;

    public Integer getId() {
        return id;
    }

    public String getEvr() {
        return evr;
    }

    public void setEvr(String evr) {
        this.evr = evr;
    }

    public EVRDataTypeEnum getDatatype() {
        return datatype;
    }

    public void setDatatype(EVRDataTypeEnum datatype) {
        this.datatype = datatype;
    }

    public OperationEnumeration getOperation() {
        return operation;
    }

    public void setOperation(OperationEnumeration operation) {
        this.operation = operation;
    }
}
