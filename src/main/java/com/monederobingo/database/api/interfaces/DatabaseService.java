package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;

public interface DatabaseService
{
    ServiceResult select(SelectQuery query) throws Exception;

    ServiceResult insert(InsertQuery query) throws Exception;
}
