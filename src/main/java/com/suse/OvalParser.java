package com.suse;

import com.suse.model.RootType;

import java.io.File;

/**
 * The Oval Parser is responsible for parsing OVAL(Open Vulnerability and Assessment Language) documents
 */
public class OvalParser {
    private final File ovalFile;


    public OvalParser(File ovalFile) {
        this.ovalFile = ovalFile;
    }

    public RootType parse() {
        return null;
    }


}
