/* Copyright 2017 Sabre Holdings */
package com.monederobingo.database.model;

public class InsertQuery
{
    private String query;
    private String idColumnName;

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public String getIdColumnName()
    {
        return idColumnName;
    }

    public void setIdColumnName(String idColumnName)
    {
        this.idColumnName = idColumnName;
    }
}
