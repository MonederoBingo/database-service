package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import xyz.greatapp.libs.service.database.requests.fields.ColumnValue;
import xyz.greatapp.libs.service.database.requests.SelectQueryRQ;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_SelectTest {
    private DatabaseController controller;
    private SelectQueryRQ selectQueryRQ;
    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception {
        controller = new DatabaseController(service, serviceLogger);
        selectQueryRQ = new SelectQueryRQ("", new ColumnValue[0]);
    }

    @Test
    public void shouldReturnErrorResponseForSelectWhenExceptionIsCaught() throws Exception {
        // given
        given(service.select(selectQueryRQ)).willThrow(new Exception());

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.select(selectQueryRQ);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnSameServiceResultFromSelectService() throws Exception {
        // given
        xyz.greatapp.libs.service.ServiceResult serviceResult = new xyz.greatapp.libs.service.ServiceResult(true, "");
        given(service.select(selectQueryRQ)).willReturn(serviceResult);

        // when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response = controller.select(selectQueryRQ);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldCallDatabaseServiceForSelect() throws Exception {
        // when
        controller.select(selectQueryRQ);

        // then
        verify(service).select(selectQueryRQ);
    }

    @Test
    public void shouldReturnBadRequestErrorIfParamIsNull() {
        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> select = controller.select(null);

        //then
        assertEquals(BAD_REQUEST, select.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestErrorIfSelectQueryHasNullQuery() {
        //given
        SelectQueryRQ query = new SelectQueryRQ(null, new ColumnValue[0]);

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> select = controller.select(query);

        //then
        assertEquals(BAD_REQUEST, select.getStatusCode());
    }

    @Test
    public void shouldReturnErrorMessageIfSelectQueryHasNullQuery() {
        //given
        SelectQueryRQ query = new SelectQueryRQ(null, new ColumnValue[0]);

        //when
        ResponseEntity<xyz.greatapp.libs.service.ServiceResult> select = controller.select(query);

        //then
        assertEquals("query.must.not.be.null", select.getBody().getMessage());
    }
}
