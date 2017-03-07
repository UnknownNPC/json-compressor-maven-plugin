package com.github.unknownnpc.plugins.executor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.unknownnpc.plugins.exception.JsonCompressorExecutorException;

import java.io.IOException;

public class JsonCompressorExecutorImpl implements JsonCompressorExecutor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String execute(String jsonAsText) throws JsonCompressorExecutorException {
        try {
            JsonNode jsonNode = objectMapper.readValue(jsonAsText, JsonNode.class);
            return jsonNode.toString();
        } catch (IOException e) {
            throw new JsonCompressorExecutorException(e.getCause());
        }
    }

}
