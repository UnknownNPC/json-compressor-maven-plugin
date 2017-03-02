package com.github.unknownnpc.plugins.executor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.unknownnpc.plugins.exception.JsonToStringExecutorException;

import java.io.IOException;

/**
 * Created by unknownnpc on 2/27/17.
 */
public class JsonToStringExecutorImpl implements JsonToStringExecutor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String execute(String jsonAsText) throws JsonToStringExecutorException {
        try {
            JsonNode jsonNode = objectMapper.readValue(jsonAsText, JsonNode.class);
            return jsonNode.toString();
        } catch (IOException e) {
            throw new JsonToStringExecutorException(e.getCause());
        }
    }

}
