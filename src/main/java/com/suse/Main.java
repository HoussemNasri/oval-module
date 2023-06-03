package com.suse;

import com.suse.db.HibernateUtil;
import com.suse.db.OvalFileEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        OvalParser ovalParser = new OvalParser();
        ovalParser.parse(new File(Main.class.getResource("suse-affected.xml").getFile()));

        long end = System.currentTimeMillis();

        System.out.println("Parser took " + (end - start) / 1000 + "s to complete");

        System.out.println("Hello world!");

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        OvalFileEntity ovalFile = new OvalFileEntity();
        ovalFile.setGenerationTimestamp(Instant.now());
        ovalFile.setSource("aa");
        ovalFile.setGeneratorName("bb");
        ovalFile.setSchemaVersion(5);
        session.save(ovalFile);
        session.flush();

        session.close();
        HibernateUtil.close();



    }
}