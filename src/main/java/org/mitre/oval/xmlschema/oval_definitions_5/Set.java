//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package org.mitre.oval.xmlschema.oval_definitions_5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;sequence&gt;
 *           &lt;element ref="{http://oval.mitre.org/XMLSchema/oval-definitions-5}set" maxOccurs="2"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="object_reference" type="{http://oval.mitre.org/XMLSchema/oval-common-5}ObjectIDPattern" maxOccurs="2"/&gt;
 *           &lt;element ref="{http://oval.mitre.org/XMLSchema/oval-definitions-5}filter" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="set_operator" type="{http://oval.mitre.org/XMLSchema/oval-definitions-5}SetOperatorEnumeration" default="UNION" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "set",
    "objectReference",
    "filter"
})
@XmlRootElement(name = "set", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class Set {

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected List<Set> set;
    @XmlElement(name = "object_reference", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected List<String> objectReference;
    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected List<Filter> filter;
    @XmlAttribute(name = "set_operator")
    protected SetOperatorEnumeration setOperator;

    /**
     * Gets the value of the set property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the set property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Set }
     * 
     * 
     */
    public List<Set> getSet() {
        if (set == null) {
            set = new ArrayList<Set>();
        }
        return this.set;
    }

    /**
     * Gets the value of the objectReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getObjectReference() {
        if (objectReference == null) {
            objectReference = new ArrayList<String>();
        }
        return this.objectReference;
    }

    /**
     * Gets the value of the filter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Filter }
     * 
     * 
     */
    public List<Filter> getFilter() {
        if (filter == null) {
            filter = new ArrayList<Filter>();
        }
        return this.filter;
    }

    /**
     * Gets the value of the setOperator property.
     * 
     * @return
     *     possible object is
     *     {@link SetOperatorEnumeration }
     *     
     */
    public SetOperatorEnumeration getSetOperator() {
        if (setOperator == null) {
            return SetOperatorEnumeration.UNION;
        } else {
            return setOperator;
        }
    }

    /**
     * Sets the value of the setOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetOperatorEnumeration }
     *     
     */
    public void setSetOperator(SetOperatorEnumeration value) {
        this.setOperator = value;
    }

}