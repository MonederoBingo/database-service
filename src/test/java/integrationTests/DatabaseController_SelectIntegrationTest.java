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

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import xyz.greatapp.libs.service.requests.database.ColumnValue;
import xyz.greatapp.libs.service.requests.database.SelectQueryRQ;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_SelectIntegrationTest extends IntegrationTest
{
    private DatabaseController databaseController;

    @Before
    public void setUp() throws Exception
    {
        databaseController = new DatabaseController(getDatabaseService(), new ServiceLogger());
    }

    @After
    public void tearDown() throws Exception
    {
        executeQuery("DROP ALL OBJECTS;");
    }

    @Test
    public void shouldReturnErrorWhenDatabaseIsEmpty() throws SQLException
    {
        //given empty database

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> responseEntity = databaseController.select(new SelectQueryRQ("dummy", new ColumnValue[0]));

        //then
        assertEquals(INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
    }

    @Test
    public void shouldReturnSuccessfulResponseIfSelectRetrievesInformation() throws SQLException
    {
        //given
        givenThisExecutedQuery("CREATE TABLE dummy (id INTEGER);");

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> responseEntity = databaseController.select(new SelectQueryRQ("dummy", new ColumnValue[0]));

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    public void shouldReturnJsonResultFromTheTable() throws SQLException, JSONException
    {
        //given
        givenThisExecutedQuery("CREATE TABLE dummy (id INTEGER, name VARCHAR);");
        givenThisExecutedQuery("INSERT INTO dummy VALUES (1, 'abc')");

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> responseEntity = databaseController.select(new SelectQueryRQ("dummy", new ColumnValue[0]));

        //then
        JSONObject jsonObject = new JSONObject(responseEntity.getBody().getObject());
        assertEquals(1, jsonObject.getInt("id"));
        assertEquals("abc", jsonObject.getString("name"));
    }

    @Test
    public void shouldNotResultIfQueryDoesNotFindRows() throws SQLException, JSONException
    {
        //given
        givenThisExecutedQuery("CREATE TABLE dummy (id INTEGER, name VARCHAR);");
        givenThisExecutedQuery("INSERT INTO dummy VALUES (1, 'abc')");

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> responseEntity = databaseController.select(new SelectQueryRQ("dummy", new ColumnValue[] {
                new ColumnValue("id", "2")
        }));

        //then
        JSONObject jsonObject = new JSONObject(responseEntity.getBody().getObject());
        assertFalse(jsonObject.has("id"));
        assertFalse(jsonObject.has("name"));
    }
}
