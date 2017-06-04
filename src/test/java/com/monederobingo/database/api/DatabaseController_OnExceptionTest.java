package com.monederobingo.database.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.MonederoLogger;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_OnExceptionTest
{
    private DatabaseController controller;
    private SelectQuery selectQuery;
    private InsertQuery insertQuery;
    private UpdateQuery updateQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private MonederoLogger logger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, logger);
        selectQuery = new SelectQuery();
        insertQuery = new InsertQuery();
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldReturnErrorResponseForSelectWhenExceptionIsThrown() throws Exception
    {
        // given
        given(service.select(selectQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult> response = controller.select(selectQuery);

        // then
        assertFailedResponse(response);
    }

    private void assertFailedResponse(ResponseEntity<ServiceResult> response)
    {
        assertFalse(response.getBody().isSuccess());
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).error(any(), any(Exception.class));
    }

    @Test
    public void shouldReturnErrorResponseForSelectListWhenExceptionIsThrown() throws Exception
    {
        // given
        given(service.selectList(selectQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult> response = controller.selectList(selectQuery);

        // then
        assertFailedResponse(response);
    }

    @Test
    public void shouldReturnErrorResponseForInsertWhenExceptionIsThrown() throws Exception
    {
        // given
        given(service.insert(insertQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult> response = controller.insert(insertQuery);

        // then
        assertFailedResponse(response);
    }

    @Test
    public void shouldReturnErrorResponseForUpdateWhenExceptionIsThrown() throws Exception
    {
        // given
        given(service.update(updateQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult> response = controller.update(updateQuery);

        // then
        assertFailedResponse(response);
    }
}
