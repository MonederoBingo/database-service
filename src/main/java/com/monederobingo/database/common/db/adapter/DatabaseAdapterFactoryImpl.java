package com.monederobingo.database.common.db.adapter;

import com.monederobingo.libs.common.environments.Environment;
import com.monederobingo.database.common.db.datasources.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAdapterFactoryImpl implements DatabaseAdapterFactory
{

    private final DataSourceFactory _dataSourceFactory;

    @Autowired
    public DatabaseAdapterFactoryImpl(DataSourceFactory dataSourceFactory) {
        _dataSourceFactory = dataSourceFactory;
    }

    @Override
    public DataBaseAdapter getDatabaseAdapter(Environment environment) throws Exception
    {
        return new DataBaseAdapter(_dataSourceFactory.getDataSource(environment), new PreparedStatementMapper());
    }
}
