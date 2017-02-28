package com.github.unknownnpc.jsontostring.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by unknownnpc on 2/27/17.
 */
public final class FileIOUtil {

    public static Map<String, String> readFilesContent(String filePathExpression, final String encoding) throws IOException {
        String filesWildcard = FilenameUtils.getName(filePathExpression);
        String filesFolderPath = FilenameUtils.getFullPath(filePathExpression);
        final Map<String, String> absolutePathAndContentMap = new HashMap<>();
        List<File> filesByWildcard = findFilesByWildcardAndFolder(filesWildcard, filesFolderPath);
        for (File filteredFile : filesByWildcard) {
            String fileAbsolutePath = filteredFile.getAbsolutePath();
            String fileContent = readFileContent(fileAbsolutePath, encoding);
            absolutePathAndContentMap.put(fileAbsolutePath, fileContent);
        }
        return absolutePathAndContentMap;
    }

    public static void writeFilesContent(Map<String, String> absolutePathAndContentMap, String encoding) throws IOException {
        for (Map.Entry<String, String> pathContentEntry : absolutePathAndContentMap.entrySet()) {
            String targetAbsolutePath = pathContentEntry.getKey();
            String targetContent = pathContentEntry.getValue();
            writeFileContent(targetAbsolutePath, targetContent, encoding);
        }
    }

    private static String readFileContent(String absoluteFilePath, final String encoding) throws IOException {
        if (isFileExist(absoluteFilePath)) {
            File f = new File(absoluteFilePath);
            return FileUtils.readFileToString(f, Charset.forName(encoding));
        } else {
            throw new IOException("File does not exist: [" + absoluteFilePath + "]. Unable to complete `read` operation.");
        }
    }

    private static void writeFileContent(String absoluteFilePath, String content, final String encoding) throws IOException {
        if (isFileExist(absoluteFilePath)) {
            File f = new File(absoluteFilePath);
            FileUtils.writeStringToFile(f, content, Charset.forName(encoding));
        } else {
            throw new IOException("File does not exist: [" + absoluteFilePath + "]. Unable to complete `write` operation.");
        }
    }

    private static List<File> findFilesByWildcardAndFolder(String filesWildcard, String filesDirectoryPath) throws IOException {
        File filesDirectory = new File(filesDirectoryPath);
        if (isDirectoryExist(filesDirectoryPath)) {
            FileFilter filter = new WildcardFileFilter(filesWildcard);
            File[] filteredFiles = filesDirectory.listFiles(filter);
            if (filteredFiles != null) {
                return Arrays.asList(filteredFiles);
            } else {
                return Collections.emptyList();
            }
        } else {
            throw new IOException("Files folder does not exist: [" + filesDirectoryPath + "]");
        }
    }

    private static boolean isFileExist(String relativePath) {
        File f = new File(relativePath);
        return f.exists() && !f.isDirectory();
    }

    private static boolean isDirectoryExist(String relativePath) {
        File f = new File(relativePath);
        return f.exists() && f.isDirectory();
    }

}
