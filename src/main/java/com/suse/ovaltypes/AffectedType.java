//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package com.suse.ovaltypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Please note that the AffectedType will change in future versions of OVAL in order to support the Common Platform Enumeration (CPE).
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AffectedType", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class AffectedType {
    @XmlElement(name = "platform", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected List<String> platforms;
    @XmlElement(name = "product", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected List<String> products;
    @XmlAttribute(name = "family", required = true)
    protected FamilyEnum family;

    /**
     * Gets the value of the list of affected platforms
     */
    public List<String> getPlatforms() {
        if (platforms == null) {
            platforms = new ArrayList<>();
        }
        return this.platforms;
    }

    /**
     * Gets the value of the list of affected products
     */
    public List<String> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return this.products;
    }

    /**
     * Gets the value of the family property.
     */
    public FamilyEnum getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     */
    public void setFamily(FamilyEnum value) {
        this.family = value;
    }

}
