package com.github.unknownnpc.plugins.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class FileIOUtil {

    private static final String FILES_FOLDER_EXCEPTION = "Files folder does not exist: [%s]";
    private static final String FILE_EXCEPTION = "File does not exist: [%s]. Unable to complete [%s] operation";
    private static final String READ_OPERATION = "read";
    private static final String WRITE_OPERATION = "write";


    public static Set<String> selectAbsoluteFilesPathByWildcardPath(String wildcardPath) throws IOException {
        String filesWildcard = FilenameUtils.getName(wildcardPath);
        String filesFolderPath = FilenameUtils.getFullPath(wildcardPath);
        if (isDirectoryExist(filesFolderPath)) {
            FileFilter filter = new WildcardFileFilter(filesWildcard);
            File filesDirectory = new File(filesFolderPath);
            File[] filteredFiles = filesDirectory.listFiles(filter);
            return filesArrayToFilesAbsolutePath(filteredFiles);
        } else {
            throw new IOException(String.format(FILES_FOLDER_EXCEPTION, filesFolderPath));
        }
    }

    public static String readFileContent(String absoluteFilePath, final String encoding) throws IOException {
        if (isFileExist(absoluteFilePath)) {
            File f = new File(absoluteFilePath);
            return FileUtils.readFileToString(f, Charset.forName(encoding));
        } else {
            throw new IOException(String.format(FILE_EXCEPTION, absoluteFilePath, READ_OPERATION));
        }
    }

    public static void writeFileContent(String absoluteFilePath, String content, final String encoding) throws IOException {
        if (isFileExist(absoluteFilePath)) {
            File f = new File(absoluteFilePath);
            FileUtils.writeStringToFile(f, content, Charset.forName(encoding));
        } else {
            throw new IOException(String.format(FILE_EXCEPTION, absoluteFilePath, WRITE_OPERATION));
        }
    }

    private static Set<String> filesArrayToFilesAbsolutePath(File[] files) {
        if (files != null) {
            Set<String> filesAbsolutePath = new HashSet<>();
            for (File file : Arrays.asList(files)) {
                filesAbsolutePath.add(file.getAbsolutePath());
            }
            return filesAbsolutePath;
        } else {
            return Collections.emptySet();
        }
    }

    private static boolean isFileExist(String absolutePath) {
        File f = new File(absolutePath);
        return f.exists() && !f.isDirectory();
    }

    private static boolean isDirectoryExist(String absolutePath) {
        File f = new File(absolutePath);
        return f.exists() && f.isDirectory();
    }

}
