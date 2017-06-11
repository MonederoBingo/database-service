package com.monederobingo.database.api;

import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

class ControllerAssertions
{
    static <T> void assertFailedResponse(ResponseEntity<ServiceResult<T>> response, ServiceLogger logger)
    {
        assertFalse(response.getBody().isSuccess());
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).error(any(), any(Exception.class));
    }

    static <T> void assertSuccessfulResponse(ServiceResult<T> serviceResult,
            ResponseEntity<ServiceResult<T>> response)
    {
        assertEquals(serviceResult, response.getBody());
        assertEquals(OK, response.getStatusCode());
    }
}
