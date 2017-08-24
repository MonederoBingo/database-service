package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.oldAssertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_UpdateTest
{
    private DatabaseController controller;
    private UpdateQuery updateQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger serviceLogger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, serviceLogger);
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldReturnErrorResponseForUpdateWhenExceptionIsCaught() throws Exception
    {
        // given
        given(service.update(updateQuery)).willThrow(new Exception());

        // when
        ResponseEntity<ServiceResult<Integer>> response = controller.update(updateQuery);

        // then
        oldAssertFailedResponse(response, serviceLogger);
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

    @Test
    public void shouldCallDatabaseServiceForUpdate() throws Exception
    {
        // when
        controller.update(updateQuery);

        // then
        verify(service).update(updateQuery);
    }
}
