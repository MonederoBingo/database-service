package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class TransactionController_OnExceptionTest
{
    private TransactionController transactionController;
    @Mock
    private TransactionService transactionService;
    @Mock
    private ThreadContextService threadContextService;
    @Mock
    private ServiceLogger serviceLogger;
    @Mock
    private FunctionalTestEnvironment functionalTestEnvironment;

    @Before
    public void setUp() throws Exception
    {
        transactionController = new TransactionController(transactionService, threadContextService, serviceLogger);
        given(threadContextService.getEnvironment()).willReturn(functionalTestEnvironment);
    }

    @Test
    public void shouldReturnErrorResponseForBeginWhenExceptionIsCaught() throws Exception
    {
        //given
        doThrow(new RuntimeException()).when(transactionService).beginTransaction();

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.begin();

        //then
        assertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnErrorResponseForRollbackWhenExceptionIsCaught() throws Exception
    {
        //given
        doThrow(new RuntimeException()).when(transactionService).rollbackTransaction();

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.rollback();

        //then
        assertFailedResponse(response, serviceLogger);
    }
}
