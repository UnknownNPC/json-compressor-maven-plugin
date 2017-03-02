package com.github.unknownnpc.plugins.executor;

import com.github.unknownnpc.plugins.exception.JsonToStringExecutorException;

/**
 * Created by unknownnpc on 2/27/17.
 */
public interface JsonToStringExecutor {

    String execute(String jsonAsText) throws JsonToStringExecutorException;

}
