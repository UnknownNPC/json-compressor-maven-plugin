package com.github.unknownnpc.plugins.executor;

import com.github.unknownnpc.plugins.exception.JsonCompressorExecutorException;

/**
 * Created by unknownnpc on 2/27/17.
 */
public interface JsonCompressorExecutor {

    String execute(String jsonAsText) throws JsonCompressorExecutorException;

}
