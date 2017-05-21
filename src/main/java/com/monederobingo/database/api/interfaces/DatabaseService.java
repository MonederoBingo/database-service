package com.monederobingo.database.api.interfaces;

import com.monederobingo.database.model.ServiceResult;

public interface DatabaseService
{
    ServiceResult select(String query) throws Exception;
}
