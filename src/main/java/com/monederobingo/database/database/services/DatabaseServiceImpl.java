package com.monederobingo.database.database.services;

import com.monederobingo.database.database.api.interfaces.DatabaseService;
import com.monederobingo.database.database.common.context.ThreadContextService;
import com.monederobingo.database.database.common.db.util.DbBuilder;
import com.monederobingo.database.database.model.SelectRequest;
import com.monederobingo.database.database.model.ServiceResult;
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

            @Override public Object[] values()
            {
                return new Object[0];
            }

            @Override public String build(ResultSet resultSet) throws SQLException
            {
                return null;
            }
        });

        return new ServiceResult(true, "");
    }
}
