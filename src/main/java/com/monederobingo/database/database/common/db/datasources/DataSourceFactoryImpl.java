package com.monederobingo.database.database.common.db.datasources;

import javax.sql.DataSource;

import com.monederobingo.database.database.common.db.util.DBUtil;
import com.monederobingo.database.database.common.db.util.concurrent.Computable;
import com.monederobingo.database.database.common.db.util.concurrent.Memoizer;
import com.monederobingo.database.database.common.environments.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactoryImpl implements DataSourceFactory {

    private final DBUtil dbUtil;

    @Autowired
    public DataSourceFactoryImpl(DBUtil dbUtil)
    {
        this.dbUtil = dbUtil;
    }

    private final Computable<Environment, DataSource> _computable = new Computable<Environment, DataSource>() {
        @Override
        public DataSource compute(Environment arg) throws InterruptedException {
            return dbUtil.createDataSource(arg);
        }
    };

    private final Computable<Environment, DataSource> _dataSources = new Memoizer<>(_computable);

    @Override
    public DataSource getDataSource(Environment environment) {
        try {
            final DataSource dataSource = _dataSources.compute(environment);
            if (dataSource == null) {
                throw new RuntimeException("DataSource cannot be null!");
            }
            return dataSource;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
