package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;
import xyz.greatapp.libs.service.requests.database.InsertQueryRQ;
import xyz.greatapp.libs.service.requests.database.SelectQueryRQ;

public interface DatabaseService {
    xyz.greatapp.libs.service.ServiceResult select(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult selectList(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult insert(InsertQueryRQ query) throws Exception;

    ServiceResult<Integer> update(UpdateQuery query) throws Exception;
}
