package com.github.unknownnpc.plugins;

import com.github.unknownnpc.plugins.exception.JsonToStringExecutorException;
import com.github.unknownnpc.plugins.executor.JsonToStringExecutor;
import com.github.unknownnpc.plugins.executor.JsonToStringExecutorImpl;
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
public class JsonToStringMojo extends AbstractMojo {

    @Parameter(required = true)
    private List<String> includes = new ArrayList<>();

    @Parameter
    private List<String> excludes = new ArrayList<>();

    @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}", required = true)
    private String encoding;

    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    private static final JsonToStringExecutor JSON_TO_STRING_EXECUTOR = new JsonToStringExecutorImpl();

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping");
            return;
        }
        try {
            Set<String> targetFilesPath = findTargetFilesPath();
            for (String filePathExpression : targetFilesPath) {
                String targetContent = FileIOUtil.readFileContent(filePathExpression, encoding);
                String minifyResult = JSON_TO_STRING_EXECUTOR.execute(targetContent);
                FileIOUtil.writeFileContent(filePathExpression, minifyResult, encoding);
                SummaryUtil.add(targetContent, encoding, getLog());
            }
        } catch (IOException | JsonToStringExecutorException e) {
            throw new MojoExecutionException(e.getMessage());
        } finally {
            SummaryUtil.print(getLog());
        }
    }

    private Set<String> findTargetFilesPath() throws IOException {
        Set<String> includesPaths = findAbsoluteFilesPathByWildcards(includes);
        Set<String> excludesPaths = findAbsoluteFilesPathByWildcards(excludes);
        return mergeIncludeWithExcludeFilesPath(includesPaths, excludesPaths);
    }

    private Set<String> findAbsoluteFilesPathByWildcards(List<String> wildcards) throws IOException {
        Set<String> filesPath = new HashSet<>();
        for (String wildcard : wildcards) {
            Set<String> wildcardFilesPath = FileIOUtil.selectAbsoluteFilesPathByWildcardPath(wildcard);
            filesPath.addAll(wildcardFilesPath);
        }
        return filesPath;
    }

    private Set<String> mergeIncludeWithExcludeFilesPath(Set<String> includesPaths, Set<String> excludesPaths) {
        includesPaths.removeAll(excludesPaths);
        return includesPaths;
    }

}
