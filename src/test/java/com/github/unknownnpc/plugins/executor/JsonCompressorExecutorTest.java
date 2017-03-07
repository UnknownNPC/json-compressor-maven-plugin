package com.github.unknownnpc.plugins.executor;

import com.github.unknownnpc.plugins.exception.JsonCompressorExecutorException;
import org.junit.Assert;
import org.junit.Test;

public class JsonCompressorExecutorTest {

    private static final JsonCompressorExecutor JSON_COMPRESSOR_EXECUTOR = new JsonCompressorExecutorImpl();

    @Test
    public void executorShouldRemoveNewLines() throws JsonCompressorExecutorException {
        String jsonSampleWithNewLines = "{\"v1\"\n\n:\"v2\"\n}";
        String validJsonSample = "{\"v1\":\"v2\"}";
        String executionResult = JSON_COMPRESSOR_EXECUTOR.execute(jsonSampleWithNewLines);
        Assert.assertEquals(validJsonSample, executionResult);
    }

    @Test
    public void executorShouldRemoveWhitespaces() throws JsonCompressorExecutorException {
        String notValidJsonSample = "{\"v1\"      :        \"v2\"    }";
        String validJsonSample = "{\"v1\":\"v2\"}";
        String executionResult = JSON_COMPRESSOR_EXECUTOR.execute(notValidJsonSample);
        Assert.assertEquals(validJsonSample, executionResult);
    }

    @Test(expected = JsonCompressorExecutorException.class)
    public void executorShouldThrowExceptionWhenStringNotValid() throws JsonCompressorExecutorException {
        String notValidJsonSample = "text-sample";
        JSON_COMPRESSOR_EXECUTOR.execute(notValidJsonSample);
    }

}
