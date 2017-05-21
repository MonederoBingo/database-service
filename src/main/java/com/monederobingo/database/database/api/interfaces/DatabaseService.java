package com.monederobingo.database.database.api.interfaces;

import com.monederobingo.database.database.model.SelectRequest;
import com.monederobingo.database.database.model.ServiceResult;

public interface DatabaseService
{
    ServiceResult select(SelectRequest selectRequest) throws Exception;
}
