package com.github.unknownnpc.plugins;

import com.github.unknownnpc.plugins.exception.JsonCompressorExecutorException;
import com.github.unknownnpc.plugins.executor.JsonCompressorExecutor;
import com.github.unknownnpc.plugins.executor.JsonCompressorExecutorImpl;
import com.github.unknownnpc.plugins.util.FileIOUtil;
import com.github.unknownnpc.plugins.util.SummaryUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mojo(name = "minify", defaultPhase = LifecyclePhase.TEST_COMPILE)
public class JsonCompressorMojo extends AbstractMojo {

    @Parameter(required = true)
    private List<String> includes = new ArrayList<>();

    @Parameter
    private List<String> excludes = new ArrayList<>();

    @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}", required = true)
    private String encoding;

    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    private static final JsonCompressorExecutor JSON_TO_STRING_EXECUTOR = new JsonCompressorExecutorImpl();

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping");
            return;
        }
        try {
            Set<String> targetFilesAbsolutePath = findTargetFilesPath();
            for (String targetFileAbsolutePath : targetFilesAbsolutePath) {
                String targetContent = FileIOUtil.readFileContent(targetFileAbsolutePath, encoding);
                String targetContentMinifyResult = JSON_TO_STRING_EXECUTOR.execute(targetContent);
                FileIOUtil.writeFileContent(targetFileAbsolutePath, targetContentMinifyResult, encoding);
                SummaryUtil.add(targetContent, encoding, getLog());
            }
        } catch (IOException | JsonCompressorExecutorException e) {
            throw new MojoExecutionException(e.getMessage());
        } finally {
            SummaryUtil.print(getLog());
        }
    }

    private Set<String> findTargetFilesPath() throws IOException {
        Set<String> includesPaths = findAbsoluteFilesPathByPaths(includes);
        Set<String> excludesPaths = findAbsoluteFilesPathByPaths(excludes);
        return mergeIncludeWithExcludeFilesPath(includesPaths, excludesPaths);
    }

    private Set<String> findAbsoluteFilesPathByPaths(List<String> paths) throws IOException {
        Set<String> filesPath = new HashSet<>();
        for (String path : paths) {
            Set<String> resolvedFilesPath = FileIOUtil.selectAbsoluteFilesPathByPath(path);
            filesPath.addAll(resolvedFilesPath);
        }
        return filesPath;
    }

    private Set<String> mergeIncludeWithExcludeFilesPath(Set<String> includesPaths, Set<String> excludesPaths) {
        includesPaths.removeAll(excludesPaths);
        return includesPaths;
    }

}
