package integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import com.monederobingo.database.api.DatabaseController;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_SelectIntegrationTest extends IntegrationTest
{
    private DatabaseController databaseController;

    @Before
    public void setUp() throws Exception
    {
         databaseController = new DatabaseController(getDatabaseService(), new ServiceLogger());
    }

    @Test
    public void shouldReturnErrorWhenDatabaseIsEmpty() throws SQLException
    {
        //given empty database

        //when
        ResponseEntity<ServiceResult<String>> responseEntity = databaseController.select(new SelectQuery("SELECT * from dummy;"));

        //then
        assertEquals(INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
    }

    @Test
    public void test() throws SQLException
    {
        //given
        givenThisExecutedQuery("CREATE TABLE dummy (id INTEGER);");

        //when
        ResponseEntity<ServiceResult<String>> responseEntity = databaseController.select(new SelectQuery("SELECT * from dummy;"));

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
    }
}
