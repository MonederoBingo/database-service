package com.monederobingo.database.services;

import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.common.db.adapter.DataBaseAdapter;
import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactory;
import com.monederobingo.database.common.db.util.DbBuilder;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.greatapp.libs.database.queries.Select;
import xyz.greatapp.libs.service.Environment;
import xyz.greatapp.libs.service.requests.database.SelectQueryRQ;

@Component
public class DatabaseServiceImpl implements DatabaseService
{
    private final ThreadContextService oldThreadContextService;
    private final DatabaseAdapterFactory oldDatabaseAdapterFactory;
    private final xyz.greatapp.libs.service.context.ThreadContextService threadContextService;
    private final xyz.greatapp.libs.database.adapter.DatabaseAdapterFactory databaseAdapterFactory;

    private final static Map<Environment, String> schemasMap = new HashMap<>();

    static {
        schemasMap.put(Environment.DEV, "greatappxyz");
        schemasMap.put(Environment.PROD, "greatappxyz");
        schemasMap.put(Environment.AUTOMATION_TEST, "greatappxyz_test");
        schemasMap.put(Environment.UAT, "greatappxyz_test");
        schemasMap.put(Environment.INTEGRATION_TEST, "public");
    }

    private String getSchema() {
        return schemasMap.getOrDefault(oldThreadContextService.getEnvironment(), "greatappxyz_test") + ".";
    }

    @Autowired
    public DatabaseServiceImpl(ThreadContextService oldThreadContextService, DatabaseAdapterFactory oldDatabaseAdapterFactory,
                               xyz.greatapp.libs.service.context.ThreadContextService threadContextService,
                               xyz.greatapp.libs.database.adapter.DatabaseAdapterFactory databaseAdapterFactory)
    {
        this.oldThreadContextService = oldThreadContextService;
        this.oldDatabaseAdapterFactory = oldDatabaseAdapterFactory;
        this.threadContextService = threadContextService;
        this.databaseAdapterFactory = databaseAdapterFactory;
    }

    @Override
    public xyz.greatapp.libs.service.ServiceResult select(SelectQueryRQ query) throws Exception {
        return new Select(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }

    xyz.greatapp.libs.database.adapter.DataBaseAdapter getDatabaseAdapter() throws Exception
    {
        return databaseAdapterFactory.getDatabaseAdapter(threadContextService.getEnvironment());
    }

    private void buildObject(ResultSet resultSet, int columnCount, JSONObject jsonObject) throws Exception
    {
        for (int i = 1; i <= columnCount; i++)
        {
            String columnName = resultSet.getMetaData().getColumnName(i);
            Object object = resultSet.getObject(columnName);
            if(object == null)
                object = "";
            jsonObject.put(columnName.toLowerCase(), object);
        }
    }

    @Override
    public ServiceResult<String> selectList(SelectQuery query) throws Exception
    {
        JSONArray object = getOldDatabaseAdapter().selectList(new DbBuilder()
        {
            @Override
            public String sql() throws SQLException
            {
                return query.getQuery();
            }

            @Override
            public Object[] values()
            {
                return new Object[0];
            }

            @Override
            public JSONObject build(ResultSet resultSet) throws Exception
            {
                int columnCount = resultSet.getMetaData().getColumnCount();
                JSONObject jsonObject = new JSONObject();
                buildObject(resultSet, columnCount, jsonObject);
                return jsonObject;
            }
        });

        return new ServiceResult<>(true, "", object.toString());
    }

    private DataBaseAdapter getOldDatabaseAdapter() throws Exception
    {
        return oldDatabaseAdapterFactory.getDatabaseAdapter(oldThreadContextService.getEnvironment());
    }

    @Override public ServiceResult<Long> insert(InsertQuery query) throws Exception
    {
        long newId = getOldDatabaseAdapter().executeInsert(query.getQuery(), query.getIdColumnName());
        return new ServiceResult<>(true, "", newId);
    }

    @Override public ServiceResult<Integer> update(UpdateQuery query) throws Exception
    {
        int updatedRows = getOldDatabaseAdapter().executeUpdate(query.getQuery());
        return new ServiceResult<>(true, "", updatedRows);
    }
}
