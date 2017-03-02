package com.github.unknownnpc.plugins;

import java.net.URL;

public class TestResourceReader {

    private static final String RESOURCES_TARGET_PATH = "../test-classes/";

    protected String getTestFilePath(String fileName) {
        URL testClassesUrl = this.getClass().getClassLoader().getResource(RESOURCES_TARGET_PATH);
        if (testClassesUrl != null) {
            return testClassesUrl.getPath() + fileName;
        } else {
            return fileName;
        }
    }
}
