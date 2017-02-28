package com.github.unknownnpc.jsontostring.executor;

import com.github.unknownnpc.jsontostring.exception.JsonToStringExecutorException;

/**
 * Created by unknownnpc on 2/27/17.
 */
public interface JsonToStringExecutor {

    String execute(String jsonAsText) throws JsonToStringExecutorException;

}
