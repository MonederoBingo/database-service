package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;

public interface DatabaseService
{
    ServiceResult<String> select(SelectQuery query) throws Exception;

    ServiceResult<String> selectList(SelectQuery query) throws Exception;

    ServiceResult<Long> insert(InsertQuery query) throws Exception;

    ServiceResult<Integer> update(UpdateQuery query) throws Exception;
}
