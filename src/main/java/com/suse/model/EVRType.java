package com.suse.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntityStateAnySimpleType", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class EVRType {
    @XmlValue
    private String value;
    @XmlAttribute(name = "datatype", namespace = "http://oval.mitre.org/XMLSchema/oval-common-5")
    private EVRDataTypeEnum datatype;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EVRDataTypeEnum getDatatype() {
        return datatype;
    }

    public void setDatatype(EVRDataTypeEnum datatype) {
        this.datatype = datatype;
    }
}
