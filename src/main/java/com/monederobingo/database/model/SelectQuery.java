package com.monederobingo.database.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectQuery
{
    private final String query;

    @JsonCreator
    public SelectQuery(@JsonProperty("query") String query)
    {
        this.query = query;
    }

    public String getQuery()
    {
        return query;
    }
}
