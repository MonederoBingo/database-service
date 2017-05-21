package com.monederobingo.database.database.common.db.datasources;

import com.monederobingo.database.database.common.environments.Environment;

import javax.sql.DataSource;

public interface DataSourceFactory {
    DataSource getDataSource(Environment environment);
}
