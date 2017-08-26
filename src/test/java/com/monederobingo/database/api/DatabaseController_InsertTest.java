package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import xyz.greatapp.libs.service.requests.database.ColumnValue;
import xyz.greatapp.libs.service.requests.database.InsertQueryRQ;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_InsertTest {
    private DatabaseController controller;
    private InsertQueryRQ insertQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception {
        controller = new DatabaseController(service, serviceLogger);
        insertQuery = new InsertQueryRQ("", new ColumnValue[0], "");
    }

    @Test
    public void shouldReturnErrorResponseForInsertWhenExceptionIsCaught() throws Exception {
        // given
        given(service.insert(insertQuery)).willThrow(new Exception());

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.insert(insertQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnSameServiceResultFromInsertService() throws Exception {
        // given
        xyz.greatapp.libs.service.ServiceResult serviceResult = new xyz.greatapp.libs.service.ServiceResult(true, "");
        given(service.insert(insertQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.insert(insertQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldCallDatabaseServiceForInsert() throws Exception {
        // when
        controller.insert(insertQuery);

        // then
        verify(service).insert((insertQuery));
    }
}
