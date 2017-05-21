package com.monederobingo.database.services;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.common.context.ThreadContextService;
import com.monederobingo.database.common.db.util.DbBuilder;
import com.monederobingo.database.model.DbValue;
import com.monederobingo.database.model.SelectRequest;
import com.monederobingo.database.model.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public ServiceResult select(SelectRequest selectRequest) throws Exception
    {
        threadContextService.getQueryAgent().selectObject(new DbBuilder<String>()
        {
            @Override public String sql() throws SQLException
            {
                return selectRequest.getSqlQuery();
            }

            @Override public DbValue[] values()
            {
                return new DbValue[0];
            }

            @Override public String build(ResultSet resultSet) throws SQLException
            {
                return null;
            }
        });

        return new ServiceResult(true, "");
    }
}
