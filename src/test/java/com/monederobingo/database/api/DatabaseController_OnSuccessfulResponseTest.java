package com.monederobingo.database.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

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
public class DatabaseController_OnSuccessfulResponseTest
{
    private DatabaseController controller;
    private SelectQuery selectQuery;
    private InsertQuery insertQuery;
    private UpdateQuery updateQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger logger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, logger);
        selectQuery = new SelectQuery();
        insertQuery = new InsertQuery();
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldReturnSameServiceResultFromSelectService() throws Exception
    {
        // given
        ServiceResult<String> serviceResult = new ServiceResult<>(true, "");
        given(service.select(selectQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult<String>> response = controller.select(selectQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnSameServiceResultFromSelectListService() throws Exception
    {
        // given
        ServiceResult<List<String>> serviceResult = new ServiceResult<>(true, "");
        given(service.selectList(selectQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult<List<String>>> response = controller.selectList(selectQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnSameServiceResultFromInsertService() throws Exception
    {
        // given
        ServiceResult<Long> serviceResult = new ServiceResult<>(true, "");
        given(service.insert(insertQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult<Long>> response = controller.insert(insertQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnSameServiceResultFromUpdateService() throws Exception
    {
        // given
        ServiceResult<Integer> serviceResult = new ServiceResult<>(true, "");
        given(service.update(updateQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult<Integer>> response = controller.update(updateQuery);

        // then
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }
}
