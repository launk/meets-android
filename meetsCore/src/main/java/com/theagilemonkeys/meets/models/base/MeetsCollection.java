package com.theagilemonkeys.meets.models.base;

import com.theagilemonkeys.meets.ApiMethodModelHelperInterface;
import com.theagilemonkeys.meets.utils.MeetsSerializable;

import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface MeetsCollection<MODEL> extends ApiMethodModelHelperInterface<MeetsCollection<MODEL>>, MeetsSerializable {
    /**
     * Fetch a new bunch of models from the server, using page, pageSize and any extra filters
     * set with setFilters. It can trigger any of these events (and in that specific order):
     * - listener.onReset if the collection has been configured to reset on fetch. At this point,
     * the collection is empty.
     * - listener.onPush for each model added which was not already present in the collection.
     * - listener.onFetch when all models has been added, so the fetch operation has finished.
     * @return This object for chaining purposes
     */
    MeetsCollection<MODEL> fetch();

    /**
     * Add a model to the collection. If the model already exist (its id is the same as any other in
     * the collection) it WON'T be added. Triggers listener.onPush when the model is successfully added.
     * @param model The model to add.
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> insert(MODEL model);

    /**
     * Extract the model with the passed id. Triggers listener.onExtract when the model is successfully extracted.
     * @param modelId Id of the model
     * @return The extracted model
     */
    MODEL extract(int modelId);

    /**
     * Extract all the models. Triggers listener.onExtract for each extracted model and then listener.onReset
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> reset();

    /**
     * Returns the number of model in this collection
     * @return
     */
    int getSize();

    MODEL get(int index);
    /**
     * Increment the page. Useful before fetch a new page of models from the server.
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> nextPage();

    /**
     * Set a specific page. Useful before fetch a new page of models from the server.
     * @param page The number of page
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> setPage(int page);

    /**
     * Sets the number of models fetched from the server for each page.
     * @param pageSize The number of models per page
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> setPageSize(int pageSize);

    /**
     * If a true is passed, then each call to fetch will replace the entire collection with
     * the models fetched.
     * @param reset A boolean value.
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> setResetOnFetch(boolean reset);

    /**
     * Sets any filters that will be send to server in each call to fetch.
     * @param filters A Map with filters
     * @return this object for chaining purposes
     */
    MeetsCollection<MODEL> setFilters(Map<String, Object> filters);

    /**
     * Get the current page
     * @return int
     */
    int getPage();

    /**
     * Get the current page size
     * @return int
     */
    int getPageSize();

    /**
     * TODO
     * @param weakAttributes
     * @return
     */
    MeetsCollection<MODEL> include(String... weakAttributes);

    /**
     * Returns a list of maps representing the collection.
     * @param option Option to determine how to construct the Maps. By default is {@link com.theagilemonkeys.meets.models.base.MeetsModel.AsMapOption#PUBLIC_GETTERS}
     * @return The list of maps
     */
    List<Map<String, Object>> asMapList(MeetsModel.AsMapOption option);
    List<Map<String, Object>> asMapList();
}
