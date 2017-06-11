package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.mockito.BDDMockito.given;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;

import java.util.List;

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
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, serviceLogger);
        selectQuery = new SelectQuery();
        insertQuery = new InsertQuery();
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldReturnErrorResponseForSelectWhenExceptionIsCaught() throws Exception
    {
        // given
        given(service.select(selectQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult<String>> response = controller.select(selectQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnErrorResponseForSelectListWhenExceptionIsCaught() throws Exception
    {
        // given
        given(service.selectList(selectQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult<List<String>>> response = controller.selectList(selectQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnErrorResponseForInsertWhenExceptionIsCaught() throws Exception
    {
        // given
        given(service.insert(insertQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult<Long>> response = controller.insert(insertQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnErrorResponseForUpdateWhenExceptionIsCaught() throws Exception
    {
        // given
        given(service.update(updateQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult<Integer>> response = controller.update(updateQuery);

        // then
        assertFailedResponse(response, serviceLogger);
    }
}
