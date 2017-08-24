package com.monederobingo.database.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import org.springframework.http.ResponseEntity;

class ControllerAssertions
{
    static <T> void oldAssertFailedResponse(ResponseEntity<ServiceResult<T>> response, ServiceLogger logger)
    {
        assertFalse(response.getBody().isSuccess());
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).error(any(), any(Exception.class));
    }

    static <T> void assertFailedResponse(ResponseEntity<xyz.greatapp.libs.service.ServiceResult> response, ServiceLogger logger)
    {
        assertFalse(response.getBody().isSuccess());
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).error(any(), any(Exception.class));
    }

}
