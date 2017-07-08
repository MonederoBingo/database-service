package integrationTests;

import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactoryImpl;
import com.monederobingo.database.common.db.datasources.DataSourceFactory;
import com.monederobingo.database.common.db.datasources.DriverManagerDataSourceFactory;
import com.monederobingo.database.services.DatabaseServiceImpl;
import com.monederobingo.libs.common.context.ThreadContextServiceImpl;
import com.monederobingo.libs.common.environments.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class IntegrationTest
{

    private String DRIVER_CLASS = "org.h2.Driver";
    private String DATABASE_PATH = "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1";
    private String USERNAME = "sa";
    private String PASSWORD = "sa";

    DatabaseServiceImpl getDatabaseService()
    {
        ThreadContextServiceImpl threadContextService = new ThreadContextServiceImpl();
        threadContextService.initializeContext(createEnv());
        return new DatabaseServiceImpl(threadContextService, getDatabaseAdapterFactory());
    }

    private DatabaseAdapterFactoryImpl getDatabaseAdapterFactory()
    {
        return new DatabaseAdapterFactoryImpl(
                new DataSourceFactory(
                        new DriverManagerDataSourceFactory()));
    }

    private Environment createEnv()
    {
        return new Environment()
        {
            @Override
            public String getDatabasePath()
            {
                return DATABASE_PATH;
            }

            @Override
            public String getDatabaseDriverClass()
            {
                return DRIVER_CLASS;
            }

            @Override
            public String getDatabaseUsername()
            {
                return USERNAME;
            }

            @Override
            public String getDatabasePassword()
            {
                return PASSWORD;
            }

            @Override
            public String getImageDir()
            {
                return null;
            }

            @Override
            public String getClientUrl()
            {
                return null;
            }
        };
    }

    private Connection getConnection() throws SQLException
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS);
        dataSource.setUrl(DATABASE_PATH);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource.getConnection();
    }

    void executeQuery(String query) throws SQLException
    {
        getConnection().prepareStatement(query).execute();
    }

    void givenThisExecutedQuery(String query) throws SQLException
    {
        executeQuery(query);
    }
}
