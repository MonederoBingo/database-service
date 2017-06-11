package com.monederobingo.database.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

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
public class TransactionController_OnSuccessfulResponseTest
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
        transactionController = new TransactionController(
                transactionService,
                threadContextService,
                serviceLogger);
        given(threadContextService.getEnvironment()).willReturn(functionalTestEnvironment);
    }

    @Test
    public void shouldReturnSuccessfulResponseOnBegin() {

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.begin();

        //then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnSuccessfulResponseOnRollback() {

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.rollback();

        //then
        assertEquals(OK, response.getStatusCode());
    }
}
