package com.github.unknownnpc.plugins.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class FileIOUtil {

    private static final String FILES_FOLDER_EXCEPTION = "Files folder does not exist: [%s]";
    private static final String FILE_EXCEPTION = "File does not exist: [%s]. Unable to complete [%s] operation";
    private static final String READ_OPERATION = "read";
    private static final String WRITE_OPERATION = "write";
    private static final String[] SCAN_FILE_EXTENSIONS = {"json"};

    public static Set<String> selectAbsoluteFilesPathByPath(String path) throws IOException {
        File pathAsFile = new File(path);
        if (pathAsFile.isDirectory()) {
            return selectAbsoluteFilesPathByFolderPath(pathAsFile);
        }
        return selectAbsoluteFilesPathByWildcardPattern(path);
    }

    private static Set<String> selectAbsoluteFilesPathByWildcardPattern(String path) throws IOException {
        String wildcardPattern = FilenameUtils.getName(path);
        String filesFolderPath = FilenameUtils.getFullPath(path);
        if (isDirectoryExist(filesFolderPath)) {
            FileFilter filter = WildcardFileFilter.builder().setWildcards(wildcardPattern).get();
            File filesDirectory = new File(filesFolderPath);
            File[] filteredFiles = filesDirectory.listFiles(filter);
            return filesArrayToFilesAbsolutePath(filteredFiles);
        } else {
            throw new IOException(String.format(FILES_FOLDER_EXCEPTION, filesFolderPath));
        }
    }

    private static Set<String> selectAbsoluteFilesPathByFolderPath(File folder) {
        Collection<File> jsonFiles = FileUtils.listFiles(folder, SCAN_FILE_EXTENSIONS, true);
        return jsonFiles.stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toSet());
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
        return Optional.ofNullable(files).stream()
                .flatMap(Arrays::stream)
                .map(File::getAbsolutePath)
                .collect(Collectors.toSet());
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
