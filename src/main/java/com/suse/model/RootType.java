//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2023.06.01 at 01:02:18 PM CET
//


package com.suse.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "oval_definitions", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class RootType {

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5", required = true)
    protected GeneratorType generator;

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected DefinitionsType definitions;

    /**
     * Gets the value of the generator property.
     */
    public GeneratorType getGenerator() {
        return generator;
    }

    /**
     * Sets the value of the generator property.
     */
    public void setGenerator(GeneratorType value) {
        this.generator = value;
    }

    /**
     * Gets the value of the definitions property.
     */
    public DefinitionsType getDefinitions() {
        return definitions;
    }

    /**
     * Sets the value of the definitions' property.
     */
    public void setDefinitions(DefinitionsType value) {
        this.definitions = value;
    }
}
