package com.suse;

import com.suse.model.DefinitionsRootType;
import com.suse.model.OvalParserException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * The Oval Parser is responsible for parsing OVAL(Open Vulnerability and Assessment Language) documents
 */
public class OvalParser {

    public DefinitionsRootType parse(File ovalFile) throws OvalParserException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DefinitionsRootType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (DefinitionsRootType) unmarshaller.unmarshal(ovalFile);
        } catch (JAXBException e) {
            throw new OvalParserException("Failed to parse the given OVAL file at: " + ovalFile.getAbsolutePath(), e);
        }
    }

}
