//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.01 at 01:02:18 PM CET 
//


package com.suse.model;

import com.suse.model.linux.DpkginfoState;
import com.suse.model.linux.DpkginfoTest;
import com.suse.model.linux.RpminfoState;
import com.suse.model.linux.RpminfoTest;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The TestsType complex type is a container for one or more test child elements.
 * <p>
 * Each test element describes a single OVAL Test. Please refer to the description of the TestType for more information
 * about an individual test.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestsType", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5")
public class TestsType {

    @XmlElements({
            @XmlElement(name = "rpminfo_test", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5#linux", type = RpminfoTest.class),
            @XmlElement(name = "dpkginfo_test", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5#linux", type = DpkginfoTest.class),
            @XmlElement(name = "test", namespace = "http://oval.mitre.org/XMLSchema/oval-definitions-5", type = TestType.class)
    })
    protected List<TestType> tests;

    /**
     * Gets the value of the contained tests.
     */
    public List<TestType> getTests() {
        if (tests == null) {
            tests = new ArrayList<>();
        }
        return this.tests;
    }

}
