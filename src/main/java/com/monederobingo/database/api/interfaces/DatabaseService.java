package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;

public interface DatabaseService
{
    ServiceResult select(SelectQuery query) throws Exception;

    ServiceResult selectList(SelectQuery query) throws Exception;

    ServiceResult insert(InsertQuery query) throws Exception;

    ServiceResult update(UpdateQuery query) throws Exception;
}
