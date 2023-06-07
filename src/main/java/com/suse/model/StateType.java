//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package com.suse.model;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * When evaluating a particular state against an object, one should evaluate each individual entity separately.
 * The individual results are then combined by the operator to produce an overall result.
 * <p>
 * This process holds true even when there are multiple instances of the same entity. Evaluate each instance separately,
 * taking the entity check attribute into account, and then combine everything using the operator.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateType", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class StateType {

    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "version", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger version;
    @XmlAttribute(name = "operator")
    protected LogicOperatorType operator;
    @XmlAttribute(name = "comment")
    protected String comment;
    @XmlAttribute(name = "deprecated")
    protected Boolean deprecated;
    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5#linux")
    protected EVRType evr;

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5#linux")
    protected String name;

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5#linux")
    protected String arch;


    /**
     * Gets the value of the id property.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

    /**
     * Gets the value of the operator property.
     */
    public LogicOperatorType getOperator() {
        if (operator == null) {
            return LogicOperatorType.AND;
        } else {
            return operator;
        }
    }

    /**
     * Sets the value of the operator property.
     */
    public void setOperator(LogicOperatorType value) {
        this.operator = value;
    }

    /**
     * Gets the value of the comment property.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the deprecated property.
     */
    public boolean isDeprecated() {
        if (deprecated == null) {
            return false;
        } else {
            return deprecated;
        }
    }

    /**
     * Sets the value of the deprecated property.
     */
    public void setDeprecated(Boolean value) {
        this.deprecated = value;
    }

    public EVRType getEvr() {
        return evr;
    }

    public void setEvr(EVRType evr) {
        this.evr = evr;
    }
}
