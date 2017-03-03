package com.github.unknownnpc.plugins.util;

import com.github.unknownnpc.plugins.TestResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class FileIOUtilTest extends TestResourceReader {

    private static final String UTF8_ENCODING = "UTF-8";
    private static final String READ_FILE_SAMPLE_TXT = "read-file-sample.txt";
    private static final String WRITE_FILE_SAMPLE_TXT = "write-file-sample.txt";
    private static final String TXT_FILES_WILDCARD = "*.txt";

    @Test
    public void fileUtilShouldReadFileContent() throws IOException {
        String readFileSamplePath = getTestFilePath(READ_FILE_SAMPLE_TXT);
        String readFileContent = FileIOUtil.readFileContent(readFileSamplePath, UTF8_ENCODING);
        String expectedContent = "Text sample";
        Assert.assertEquals(expectedContent, readFileContent);
    }

    @Test
    public void fileUtilShouldWriteFileContent() throws IOException {
        String writeFileSamplePath = getTestFilePath(WRITE_FILE_SAMPLE_TXT);
        String writeFileContentBefore = FileIOUtil.readFileContent(writeFileSamplePath, UTF8_ENCODING);
        Assert.assertTrue(writeFileContentBefore.isEmpty());
        String writeContent = "Text sample";
        FileIOUtil.writeFileContent(writeFileSamplePath, writeContent, UTF8_ENCODING);
        String writeFileContentAfter = FileIOUtil.readFileContent(writeFileSamplePath, UTF8_ENCODING);
        Assert.assertEquals(writeContent, writeFileContentAfter);
    }

    @Test
    public void fileUtilShouldFindFilesByWildcard() throws IOException {
        String txtFilesWildcardPath = getTestFilePath(TXT_FILES_WILDCARD);
        Set<String> filesPath = FileIOUtil.selectAbsoluteFilesPathByWildcardPath(txtFilesWildcardPath);
        final int expectedNumberOfTxtFiles = 2;
        Assert.assertEquals(expectedNumberOfTxtFiles, filesPath.size());
    }

    @Test(expected = IOException.class)
    public void fileUtilThrowsExceptionIfWildcardPathInvalid() throws IOException {
        FileIOUtil.selectAbsoluteFilesPathByWildcardPath("fake-read-path.txt");
    }

    @Test(expected = IOException.class)
    public void fileUtilThrowsExceptionIfReadFileDoesntExist() throws IOException {
        FileIOUtil.readFileContent("fake-read-path.txt", UTF8_ENCODING);
    }

    @Test(expected = IOException.class)
    public void fileUtilThrowsExceptionIfWriteFileDoesntExist() throws IOException {
        FileIOUtil.writeFileContent("fake-write-path.txt", "", UTF8_ENCODING);
    }

}
