//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package com.suse.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "CheckEnumeration", namespace = "http://oval.mitre.org/XMLSchema/oval-common-5")
@XmlEnum
public enum CheckEnum {


    /**
     * A value of 'all' means that a final result of true is given if all the individual results under consideration are true.
     * 
     */
    @XmlEnumValue("all")
    ALL("all"),

    /**
     * A value of 'at least one' means that a final result of true is given if at least one of the individual results under consideration is true.
     * 
     */
    @XmlEnumValue("at least one")
    AT_LEAST_ONE("at least one"),

    /**
     * A value of 'none exists' means that a test evaluates to true if no matching object exists that satisfy the data requirements.
     * 
     */
    @XmlEnumValue("none exist")
    NONE_EXIST("none exist"),

    /**
     * A value of 'none satisfy' means that a final result of true is given if none the individual results under consideration are true.
     * 
     */
    @XmlEnumValue("none satisfy")
    NONE_SATISFY("none satisfy"),

    /**
     * A value of 'only one' means that a final result of true is given if one and only one of the individual results under consideration are true.
     * 
     */
    @XmlEnumValue("only one")
    ONLY_ONE("only one");
    private final String value;

    CheckEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CheckEnum fromValue(String v) {
        for (CheckEnum c: CheckEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}