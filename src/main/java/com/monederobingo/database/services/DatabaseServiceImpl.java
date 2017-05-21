package com.monederobingo.database.services;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.common.context.ThreadContextService;
import com.monederobingo.database.common.db.util.DbBuilder;
import com.monederobingo.database.model.ServiceResult;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseServiceImpl implements DatabaseService
{
    private final ThreadContextService threadContextService;

    @Autowired
    public DatabaseServiceImpl(ThreadContextService threadContextService)
    {
        this.threadContextService = threadContextService;
    }

    @Override
    public ServiceResult select(String query) throws Exception
    {
        threadContextService.getQueryAgent().selectObject(new DbBuilder<String>()
        {
            @Override
            public String sql() throws SQLException
            {
                return query;
            }

            @Override
            public Object[] values()
            {
                return new Object[0];
            }

            @Override
            public String build(ResultSet resultSet) throws SQLException
            {
                return null;
            }
        });

        return new ServiceResult(true, "");
    }
}
