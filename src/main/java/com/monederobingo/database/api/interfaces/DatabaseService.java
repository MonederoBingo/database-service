package com.monederobingo.database.api.interfaces;

import xyz.greatapp.libs.service.ServiceResult;
import xyz.greatapp.libs.service.database.requests.DeleteQueryRQ;
import xyz.greatapp.libs.service.database.requests.InsertQueryRQ;
import xyz.greatapp.libs.service.database.requests.SelectQueryRQ;
import xyz.greatapp.libs.service.database.requests.UpdateQueryRQ;

public interface DatabaseService {
    ServiceResult select(SelectQueryRQ query) throws Exception;

    ServiceResult selectList(SelectQueryRQ query) throws Exception;

    ServiceResult insert(InsertQueryRQ query) throws Exception;

    ServiceResult update(UpdateQueryRQ query) throws Exception;

    ServiceResult delete(DeleteQueryRQ query) throws Exception;
}
