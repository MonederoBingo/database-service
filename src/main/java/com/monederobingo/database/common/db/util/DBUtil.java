package com.monederobingo.database.common.db.util;

import javax.sql.DataSource;

import com.monederobingo.libs.common.environments.DevEnvironment;
import com.monederobingo.libs.common.environments.Environment;
import com.monederobingo.libs.common.environments.UnitTestEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DBUtil
{

    private final UnitTestEnvironment unitTestEnvironment;
    private final DevEnvironment devEnvironment;

    @Value("${db_url}")
    private String dbUrl;

    @Autowired
    public DBUtil(UnitTestEnvironment unitTestEnvironment, DevEnvironment devEnvironment)
    {
        this.unitTestEnvironment = unitTestEnvironment;
        this.devEnvironment = devEnvironment;
    }

    public String getUnitTestDatabasePath() {
        return unitTestEnvironment.getDatabasePath();
    }

    public String getDevDatabasePath() {
        return devEnvironment.getDbUrl();
    }

    public DataSource createDataSource(Environment environment) {
        return createDataSourceSpecifyingUrl(environment, environment.getDatabasePath());
    }

    public DataSource createDataSourceWithoutDatabaseName(Environment environment) {
        return createDataSourceSpecifyingUrl(environment, dbUrl);
    }

    private static DataSource createDataSourceSpecifyingUrl(Environment environment, String url) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getDatabaseDriverClass());
        dataSource.setUrl(url);
        dataSource.setUsername(environment.getDatabaseUsername());
        dataSource.setPassword(environment.getDatabasePassword());
        return dataSource;
    }
}
