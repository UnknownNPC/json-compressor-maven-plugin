package com.github.unknownnpc.plugins;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCompressorMojoTest extends TestResourceReader {

    @Rule
    public MojoRule rule = new MojoRule();

    private static final String PLUGIN_GOAL = "minify";
    private static final String JSON_SAMPLE_FILENAME_1 = "json-sample.json";
    private static final String JSON_SAMPLE_FILENAME_2 = "json-sample2.json";
    private static final String JSON_SAMPLE_FILENAME_3 = "json-sample3.json";
    private static final String TEST_PROJECT_POM_PATH = "target/test-classes/test-pom.xml";
    private static final String VALIDATION_REGEXP = "\\s(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)";
    private static final Pattern RESULT_REGEXP_PATTERN = Pattern.compile(VALIDATION_REGEXP);

    @Before
    public void pluginShouldExecuteMinifyTaskWithoutExceptions() throws Exception {
        JsonCompressorMojo jsonCompressorMojo = (JsonCompressorMojo) rule.lookupMojo(PLUGIN_GOAL, TEST_PROJECT_POM_PATH);
        Assert.assertNotNull(jsonCompressorMojo);
        jsonCompressorMojo.execute();
    }

    @Test
    public void jsonFileShouldNotBeEmpty() throws IOException {
        String jsonSample1 = readTestFileContentToString(JSON_SAMPLE_FILENAME_1);
        String jsonSample2 = readTestFileContentToString(JSON_SAMPLE_FILENAME_2);
        String jsonSample3 = readTestFileContentToString(JSON_SAMPLE_FILENAME_3);
        Assert.assertNotNull(jsonSample1);
        Assert.assertNotNull(jsonSample2);
        Assert.assertNotNull(jsonSample3);
        Assert.assertFalse(jsonSample1.isEmpty());
        Assert.assertFalse(jsonSample2.isEmpty());
        Assert.assertFalse(jsonSample3.isEmpty());
    }

    @Test
    public void jsonFileContentShouldBeInlineExceptExcludeFiles() throws IOException {
        String jsonSample1 = readTestFileContentToString(JSON_SAMPLE_FILENAME_1);
        String jsonSample2 = readTestFileContentToString(JSON_SAMPLE_FILENAME_2);
        String jsonSample3 = readTestFileContentToString(JSON_SAMPLE_FILENAME_3);
        Assert.assertFalse(patternMatchPluginResult(jsonSample1));
        Assert.assertFalse(patternMatchPluginResult(jsonSample2));
        Assert.assertTrue(patternMatchPluginResult(jsonSample3));
    }

    private boolean patternMatchPluginResult(String jsonAsText) {
        Matcher matcher = RESULT_REGEXP_PATTERN.matcher(jsonAsText);
        return matcher.find();
    }

    private String readTestFileContentToString(String filename) throws IOException {
        String testFilePath = getTestFilePath(filename);
        return FileUtils.readFileToString(new File(testFilePath), Charset.defaultCharset());
    }

}
