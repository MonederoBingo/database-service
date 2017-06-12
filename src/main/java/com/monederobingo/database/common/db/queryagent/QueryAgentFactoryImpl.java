package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.libs.common.environments.Environment;
import com.monederobingo.database.common.db.datasources.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryAgentFactoryImpl implements QueryAgentFactory {

    private final DataSourceFactory _dataSourceFactory;

    @Autowired
    public QueryAgentFactoryImpl(DataSourceFactory dataSourceFactory) {
        _dataSourceFactory = dataSourceFactory;
    }

    @Override
    public QueryAgent getQueryAgent(Environment environment) throws InterruptedException
    {
        return new QueryAgent(_dataSourceFactory.getDataSource(environment));
    }
}
