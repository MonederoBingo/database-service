package com.monederobingo.database.api.interfaces;

import xyz.greatapp.libs.service.requests.database.InsertQueryRQ;
import xyz.greatapp.libs.service.requests.database.SelectQueryRQ;
import xyz.greatapp.libs.service.requests.database.UpdateQueryRQ;

public interface DatabaseService {
    xyz.greatapp.libs.service.ServiceResult select(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult selectList(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult insert(InsertQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult update(UpdateQueryRQ query) throws Exception;
}
