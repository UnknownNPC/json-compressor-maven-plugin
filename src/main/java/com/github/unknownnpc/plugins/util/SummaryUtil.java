package com.github.unknownnpc.plugins.util;

import org.apache.maven.plugin.logging.Log;

public final class SummaryUtil {

    private static final String FILE_DEBUG_FORMAT = "Plugin run on [%s] with encoding [%s]";
    private static final String SUMMARY_FORMAT = "Plugin run on [%d] file%s.";
    private static final String DEFAULT_ENCODING = "(default)";

    private static int filesReplaced;

    public static void add(String inputFile, String encoding, Log log) {
        String encodingUsed = encoding == null ? DEFAULT_ENCODING : encoding;
        log.debug(String.format(FILE_DEBUG_FORMAT, inputFile, encodingUsed));
        filesReplaced++;
    }

    public static void print(Log log) {
        log.info(String.format(SUMMARY_FORMAT, filesReplaced, filesReplaced > 1 ? "s" : ""));
    }

}
