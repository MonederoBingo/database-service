package com.monederobingo.database.common.db.datasources;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DriverManagerDataSourceFactory
{
    DriverManagerDataSource createDriverManagerDataSource() {
        return new DriverManagerDataSource();
    }
}
