package com.github.unknownnpc.jsontostring;

import com.github.unknownnpc.jsontostring.exception.JsonToStringExecutorException;
import com.github.unknownnpc.jsontostring.executor.JsonToStringExecutor;
import com.github.unknownnpc.jsontostring.executor.JsonToStringExecutorImpl;
import com.github.unknownnpc.jsontostring.util.FileIOUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mojo(name = "minify", defaultPhase = LifecyclePhase.TEST_COMPILE)
public class JsonToStringMojo extends AbstractMojo {

    @Parameter(required = true)
    private List<String> includes = new ArrayList<>();

    @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}", required = true)
    private String encoding;

    private static final JsonToStringExecutor JSON_TO_STRING_EXECUTOR = new JsonToStringExecutorImpl();

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            for (String filePathExpression : includes) {
                Map<String, String> absolutePathAndContentMap = FileIOUtil.readFilesContent(filePathExpression, encoding);
                for (Map.Entry<String, String> absolutePathAndContent : absolutePathAndContentMap.entrySet()) {
                    String minifyResult = JSON_TO_STRING_EXECUTOR.execute(absolutePathAndContent.getValue());
                    absolutePathAndContent.setValue(minifyResult);
                }
                FileIOUtil.writeFilesContent(absolutePathAndContentMap, encoding);
            }
        } catch (IOException | JsonToStringExecutorException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

}
