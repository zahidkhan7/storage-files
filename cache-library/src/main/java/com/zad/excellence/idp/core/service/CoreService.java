package com.zad.excellence.idp.core.service;


/**
 * Core service interface for retrieving data without any input.
 *
 * @param <O> Output type
 */
public interface CoreService<O> {

    /**
     * Fetches data from the underlying source.
     *
     * @return Output data
     */
    O getDataFromSource();
}

