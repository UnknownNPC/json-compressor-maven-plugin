package com.github.unknownnpc.plugins.executor;

import com.github.unknownnpc.plugins.exception.JsonToStringExecutorException;
import org.junit.Assert;
import org.junit.Test;

public class JsonToStringExecutorTest {

    private static final JsonToStringExecutor JSON_TO_STRING_EXECUTOR = new JsonToStringExecutorImpl();

    @Test
    public void executorShouldRemoveNewLines() throws JsonToStringExecutorException {
        String jsonSampleWithNewLines = "{\"v1\"\n\n:\"v2\"\n}";
        String validJsonSample = "{\"v1\":\"v2\"}";
        String executionResult = JSON_TO_STRING_EXECUTOR.execute(jsonSampleWithNewLines);
        Assert.assertEquals(validJsonSample, executionResult);
    }

    @Test
    public void executorShouldRemoveWhitespaces() throws JsonToStringExecutorException {
        String notValidJsonSample = "{\"v1\"      :        \"v2\"    }";
        String validJsonSample = "{\"v1\":\"v2\"}";
        String executionResult = JSON_TO_STRING_EXECUTOR.execute(notValidJsonSample);
        Assert.assertEquals(validJsonSample, executionResult);
    }

    @Test(expected = JsonToStringExecutorException.class)
    public void executorShouldThrowExceptionWhenStringNotValid() throws JsonToStringExecutorException {
        String notValidJsonSample = "text-sample";
        JSON_TO_STRING_EXECUTOR.execute(notValidJsonSample);
    }

}
