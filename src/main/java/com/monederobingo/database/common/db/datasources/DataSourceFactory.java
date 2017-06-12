package com.monederobingo.database.common.db.datasources;

import com.monederobingo.database.common.db.util.concurrent.Computable;
import com.monederobingo.database.common.db.util.concurrent.Memoizer;
import com.monederobingo.libs.common.environments.Environment;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory
{
    private final Computable<Environment, DataSource> dataSources;

    @Autowired
    public DataSourceFactory(DriverManagerDataSourceFactory factory)
    {
        dataSources = new Memoizer<>(env -> getDataSource(factory, env));
    }

    private DataSource getDataSource(DriverManagerDataSourceFactory factory, Environment environment)
    {
        DriverManagerDataSource dataSource = factory.createDriverManagerDataSource();
        dataSource.setDriverClassName(environment.getDatabaseDriverClass());
        dataSource.setUrl(environment.getDatabasePath());
        dataSource.setUsername(environment.getDatabaseUsername());
        dataSource.setPassword(environment.getDatabasePassword());
        return dataSource;
    }

    public DataSource getDataSource(Environment environment) throws InterruptedException
    {
        final DataSource dataSource = dataSources.compute(environment);
        if (dataSource == null)
            throw new RuntimeException("DataSource cannot be null!");
        return dataSource;
    }
}
