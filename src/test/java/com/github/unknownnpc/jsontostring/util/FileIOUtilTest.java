package com.github.unknownnpc.jsontostring.util;

import com.github.unknownnpc.jsontostring.TestResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileIOUtilTest extends TestResourceReader {

    private static final String UTF8_ENCODING = "UTF-8";
    private static final String READ_FILE_SAMPLE_TXT = "read-file-sample.txt";
    private static final String WRITE_FILE_SAMPLE_TXT = "write-file-sample.txt";

    @Test
    public void fileUtilShouldReadFileContent() throws IOException {
        String readFileSamplePath = getTestFilePath(READ_FILE_SAMPLE_TXT);
        Map<String, String> readFilePathAndContent = FileIOUtil.readFilesContent(readFileSamplePath, UTF8_ENCODING);
        String readFileContent = readFilePathAndContent.values().iterator().next();
        String expectedContent = "Text sample";
        Assert.assertEquals(expectedContent, readFileContent);
    }

    @Test
    public void fileUtilShouldWriteFileContent() throws IOException {
        String writeFileSamplePath = getTestFilePath(WRITE_FILE_SAMPLE_TXT);
        String writeContent = "Text sample";
        Map<String, String> writeFilePathAndContent = new HashMap<String, String>() {{
            put(writeFileSamplePath, writeContent);
        }};
        FileIOUtil.writeFilesContent(writeFilePathAndContent, UTF8_ENCODING);
        Map<String, String> writeFilePathAndContentResult = FileIOUtil.readFilesContent(writeFileSamplePath, UTF8_ENCODING);
        String writeFileContent = writeFilePathAndContentResult.values().iterator().next();
        Assert.assertEquals(writeContent, writeFileContent);
    }

    @Test(expected = IOException.class)
    public void fileUtilThrowsExceptionIfReadFileDoesntExist() throws IOException {
        FileIOUtil.readFilesContent("fake-read-path.txt", UTF8_ENCODING);
    }

    @Test(expected = IOException.class)
    public void fileUtilThrowsExceptionIfWriteFileDoesntExist() throws IOException {
        Map<String, String> fakeFilePathAndContent = new HashMap<String, String>() {{
            put("fake-write-path.txt", "");
        }};

        FileIOUtil.writeFilesContent(fakeFilePathAndContent, UTF8_ENCODING);
    }

}
