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
import xyz.greatapp.libs.service.requests.database.UpdateQueryRQ;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_UpdateTest {
    private DatabaseController controller;
    private UpdateQueryRQ updateQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception {
        controller = new DatabaseController(service, serviceLogger);
        updateQuery = new UpdateQueryRQ("", new ColumnValue[0], new ColumnValue[0]);
    }

    @Test
    public void shouldReturnErrorResponseForUpdateWhenExceptionIsCaught() throws Exception {
        // given
        given(service.update(updateQuery)).willThrow(new Exception());

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.update(updateQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnSameServiceResultFromUpdateService() throws Exception {
        // given
        xyz.greatapp.libs.service.ServiceResult serviceResult = new xyz.greatapp.libs.service.ServiceResult(true, "");
        given(service.update(updateQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.update(updateQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldCallDatabaseServiceForUpdate() throws Exception {
        // when
        controller.update(updateQuery);

        // then
        verify(service).update(updateQuery);
    }
}
