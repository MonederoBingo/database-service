package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.database.common.db.datasources.DataSourceFactory;
import com.monederobingo.database.common.db.datasources.DriverManagerDataSourceFactory;
import com.monederobingo.libs.common.environments.UnitTestEnvironment;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;

public class BaseRepositoryIntegrationTest
{

    private static DataBaseAdapter _queryAgent;

    static {
        try
        {
            loadQueryAgent();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void loadQueryAgent() throws InterruptedException
    {
        String dbDriver = "jdbc:postgresql://";
        String dbDriverClass = "org.postgresql.Driver";
        String dbUrl = "localhost:5432/lealpoints_unit_test";
        String dbUser = "postgres";
        String dbPassword = "root";

        UnitTestEnvironment unitTestEnvironment = new UnitTestEnvironment(dbDriver, dbDriverClass, dbUrl, dbUser, dbPassword);
        DataSourceFactory dataSourceFactory = new DataSourceFactory(new DriverManagerDataSourceFactory());
        _queryAgent = new QueryAgentFactoryImpl(dataSourceFactory).getQueryAgent(unitTestEnvironment);
    }

    @Before
    public void setUpBase() throws Exception {
        _queryAgent.beginTransaction();
    }

    @After
    public void tearDownBase() throws Exception {
        _queryAgent.rollbackTransaction();
    }

    DataBaseAdapter getQueryAgent() {
        return _queryAgent;
    }

    void executeFixture(String sql) throws Exception {
        if (sql != null && sql.length() > 0)
            executeSql(sql, _queryAgent.getConnection());
        else
            throw new IllegalArgumentException();
    }

    private void executeSql(String sql, Connection conn) throws Exception {
        Statement st;
        st = conn.createStatement();
        st.execute(sql);
        st.close();
    }
}
