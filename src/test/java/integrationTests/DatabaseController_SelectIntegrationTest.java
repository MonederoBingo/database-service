package integrationTests;

import com.monederobingo.database.api.DatabaseController;
import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactoryImpl;
import com.monederobingo.database.common.db.datasources.DataSourceFactory;
import com.monederobingo.database.common.db.datasources.DriverManagerDataSourceFactory;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.services.DatabaseServiceImpl;
import com.monederobingo.libs.common.context.ThreadContextServiceImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_SelectIntegrationTest
{
    private DatabaseController databaseController;

    @Before
    public void setUp() throws Exception
    {
         databaseController = new DatabaseController(getDatabaseService(), logger);
    }

    private DatabaseServiceImpl getDatabaseService()
    {
        return new DatabaseServiceImpl(
                new ThreadContextServiceImpl(), new DatabaseAdapterFactoryImpl(
                        new DataSourceFactory(
                                new DriverManagerDataSourceFactory())));
    }

    private ServiceLogger logger = new ServiceLogger();

}
