package com.monederobingo.database.api.interfaces;

import xyz.greatapp.libs.service.database.requests.InsertQueryRQ;
import xyz.greatapp.libs.service.database.requests.SelectQueryRQ;
import xyz.greatapp.libs.service.database.requests.UpdateQueryRQ;

public interface DatabaseService {
    xyz.greatapp.libs.service.ServiceResult select(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult selectList(SelectQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult insert(InsertQueryRQ query) throws Exception;

    xyz.greatapp.libs.service.ServiceResult update(UpdateQueryRQ query) throws Exception;
}
