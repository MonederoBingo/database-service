package com.monederobingo.database.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

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
public class DatabaseController_OnSuccessfulResponseTest
{
    private DatabaseController controller;
    private SelectQuery selectQuery;
    private ServiceResult serviceResult;
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
        serviceResult = new ServiceResult(true, "");
        insertQuery = new InsertQuery();
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldReturnSameServiceResultFromSelectService() throws Exception
    {
        // given
        given(service.select(selectQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult> response = controller.select(selectQuery);

        // then
        assertSuccessfulResponse(response);
    }

    private void assertSuccessfulResponse(ResponseEntity<ServiceResult> response)
    {
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnSameServiceResultFromSelectListService() throws Exception
    {
        // given
        given(service.selectList(selectQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult> response = controller.selectList(selectQuery);

        // then
        assertSuccessfulResponse(response);
    }

    @Test
    public void shouldReturnSameServiceResultFromInsertService() throws Exception
    {
        // given
        given(service.insert(insertQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult> response = controller.insert(insertQuery);

        // then
        assertSuccessfulResponse(response);
    }

    @Test
    public void shouldReturnSameServiceResultFromUpdateService() throws Exception
    {
        // given
        given(service.update(updateQuery)).willReturn(serviceResult);

        // when
        ResponseEntity<ServiceResult> response = controller.update(updateQuery);

        // then
        assertSuccessfulResponse(response);
    }
}
