//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2023.06.01 at 01:02:18 PM CET
//


package com.suse.ovaltypes;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "oval_definitions", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class OvalRootType {

    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5", required = true)
    protected GeneratorType generator;
    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected DefinitionsType definitions;
    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected TestsType tests;
    @XmlElement(name = "objects", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected ObjectsType objects;
    @XmlElement(namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
    protected StatesType states;


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

    /**
     * Gets the value of the tests property.
     */
    public TestsType getTests() {
        return tests;
    }

    /**
     * Sets the value of the tests property.
     */
    public void setTests(TestsType value) {
        this.tests = value;
    }

    /**
     * Gets the value of the objects property.
     */
    public ObjectsType getObjects() {
        return objects;
    }

    /**
     * Sets the value of the objects property.
     */
    public void setObjects(ObjectsType value) {
        this.objects = value;
    }

    /**
     * Gets the value of the states property.
     */
    public StatesType getStates() {
        return states;
    }

    /**
     * Sets the value of the states property.
     */
    public void setStates(StatesType value) {
        this.states = value;
    }
}