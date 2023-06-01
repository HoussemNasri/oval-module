package com.suse;

import com.suse.model.DefinitionType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(DefinitionType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DefinitionType definitionType =
                    unmarshaller.unmarshal(
                            new StreamSource(Main.class.getClassLoader().getResource("com/suse/definition_1.xml").openStream()), DefinitionType.class).getValue();
            System.out.println(definitionType.getMetadata().getTitle());

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        JAXBException A;
        System.out.println("Hello world!");
    }
}