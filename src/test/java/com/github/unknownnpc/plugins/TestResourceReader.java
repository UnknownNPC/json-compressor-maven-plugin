package com.github.unknownnpc.plugins;

import java.net.URL;
import java.util.Optional;

public class TestResourceReader {

    private static final String RESOURCES_TARGET_PATH = "../test-classes/";

    protected String getTestFilePath(String fileName) {
        URL testClassesUrl = this.getClass().getClassLoader().getResource(RESOURCES_TARGET_PATH);
        return Optional.ofNullable(testClassesUrl)
                .map(url -> url.getPath() + fileName)
                .orElse(fileName);
    }
}
