package com.suse;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        OvalParser ovalParser = new OvalParser();
        ovalParser.parse(new File(Main.class.getResource("suse-affected.xml").getFile()));

        long end = System.currentTimeMillis();

        System.out.println("Parser took " + (end - start) / 1000 + "s to complete");

        System.out.println("Hello world!");
    }
}