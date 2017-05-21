package com.monederobingo.database.database.common.db.queryagent;

import com.monederobingo.database.database.common.db.datasources.DataSourceFactory;
import com.monederobingo.database.database.common.environments.Environment;
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
    public QueryAgent getQueryAgent(Environment environment) {
        return new QueryAgent(_dataSourceFactory.getDataSource(environment));
    }
}
