package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.model.OvalRootType;
import com.suse.model.linux.RpminfoObject;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        OvalParser ovalParser = new OvalParser();
        OvalRootType ovalRootType = ovalParser.parse(new File(Main.class.getResource("suse-affected.xml").getFile()));
        System.out.println(ovalRootType.getDefinitions().getDefinitions().get(0).getCriteria().getComment());

        long end = System.currentTimeMillis();

        System.out.println("Parser took " + (end - start) / 1000 + "s to complete");

        System.out.println("Hello world!");

        OvalObjectManager ovalObjectManager = new OvalObjectManager(ovalRootType.getObjects().getObjects());

        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").getName());
        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").isDpkg());
        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").isRpm());

/*        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        OvalFileEntity ovalFile = new OvalFileEntity();
        ovalFile.setGenerationTimestamp(Instant.now());
        ovalFile.setSource("aa");
        ovalFile.setGeneratorName("bb");
        ovalFile.setSchemaVersion(5);
        session.save(ovalFile);
        session.flush();

        session.close();
        HibernateUtil.close();*/



    }
}