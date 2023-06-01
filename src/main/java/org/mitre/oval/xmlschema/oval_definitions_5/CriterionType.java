//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package org.mitre.oval.xmlschema.oval_definitions_5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * The required test_ref attribute is the actual id of the test being referenced. The optional negate attribute signifies that the result of an individual test should be negated during analysis. For example, consider a test that evaluates to TRUE if a specific patch is installed. By negating this test, it now evaluates to TRUE if the patch is NOT installed. The optional comment attribute provides a short description of the specified test and should mirror the comment attribute of the actual test.
 * 
 * <p>Java class for CriterionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CriterionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="test_ref" use="required" type="{http://oval.mitre.org/XMLSchema/oval-common-5}TestIDPattern" /&gt;
 *       &lt;attribute name="negate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="comment" type="{http://oval.mitre.org/XMLSchema/oval-common-5}NonEmptyStringType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CriterionType", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class CriterionType {

    @XmlAttribute(name = "test_ref", required = true)
    protected String testRef;
    @XmlAttribute(name = "negate")
    protected Boolean negate;
    @XmlAttribute(name = "comment")
    protected String comment;

    /**
     * Gets the value of the testRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestRef() {
        return testRef;
    }

    /**
     * Sets the value of the testRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestRef(String value) {
        this.testRef = value;
    }

    /**
     * Gets the value of the negate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNegate() {
        if (negate == null) {
            return false;
        } else {
            return negate;
        }
    }

    /**
     * Sets the value of the negate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegate(Boolean value) {
        this.negate = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

}
