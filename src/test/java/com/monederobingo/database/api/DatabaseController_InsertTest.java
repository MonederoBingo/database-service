package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.ServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_InsertTest
{
    private DatabaseController controller;
    private InsertQuery insertQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, serviceLogger);
        insertQuery = new InsertQuery();
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
    public void shouldCallDatabaseServiceForInsert() throws Exception
    {
        // when
        controller.insert(insertQuery);

        // then
        verify(service).insert((insertQuery));
    }
}
