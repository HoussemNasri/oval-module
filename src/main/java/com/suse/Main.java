package com.suse;

import com.suse.manager.OvalObjectManager;
import com.suse.manager.OvalStateManager;
import com.suse.manager.OvalTestManager;
import com.suse.model.OvalRootType;

import java.io.File;
import java.util.stream.Collectors;

public class Main {

    private void run() throws InterruptedException {
        long start = System.currentTimeMillis();

        OvalParser ovalParser = new OvalParser();
        OvalRootType ovalRootType = ovalParser.parse(new File(Main.class.getResource("suse-affected.xml").getFile()));
        System.out.println(ovalRootType.getDefinitions().getDefinitions().get(0).getCriteria().getComment());

        long end = System.currentTimeMillis();

        System.out.println("Parser took " + (end - start) / 1000 + "s to complete");

        System.out.println("Hello world!");


        OvalObjectManager ovalObjectManager = new OvalObjectManager(ovalRootType.getObjects().getObjects());
        OvalTestManager ovalTestManager = new OvalTestManager(ovalRootType.getTests().getTests());
        OvalStateManager ovalStateMAnager = new OvalStateManager(ovalRootType.getStates().getStates());

        TestEvaluator testEvaluator = new TestEvaluator(
                ovalTestManager, ovalObjectManager, ovalStateMAnager, UyuniAPI.listSystemsByPatchStatus(UyuniAPI.User.INSTANCE, "0").collect(Collectors.toList())
        );

        System.out.println("Evaluation#1 Result: " + testEvaluator.evaluate("oval:org.opensuse.security:tst:2009685834"));
        System.out.println("Evaluation#2 Result: " + testEvaluator.evaluate("oval:org.opensuse.security:tst:2009480715"));

        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").getPackageName());
        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").isDpkg());
        System.out.println(ovalObjectManager.get("oval:org.opensuse.security:obj:2009042550").isRpm());

        System.out.println(ovalStateMAnager.get("oval:org.opensuse.security:ste:2009174462").getArch().getValue());
        System.out.println(ovalStateMAnager.get("oval:org.opensuse.security:ste:2009174462").getArch().getOperation());
    }

    public static void main(String[] args) throws InterruptedException {
        new Main().run();

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