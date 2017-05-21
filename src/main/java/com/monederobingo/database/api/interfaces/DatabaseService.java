package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.SelectRequest;
import com.monederobingo.database.model.ServiceResult;

public interface DatabaseService
{
    ServiceResult select(SelectRequest selectRequest) throws Exception;
}
