package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.assertFailedResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import com.monederobingo.libs.common.environments.ProdEnvironment;
import com.monederobingo.libs.common.environments.UATEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class TransactionController_BeginTest
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
    @Mock
    private ProdEnvironment prodEnvironment;
    @Mock
    private UATEnvironment uatEnvironment;

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
    public void shouldReturnSuccessfulResponseOnBegin() {

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.begin();

        //then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldCallBeginServiceIfFunctionalTestEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(functionalTestEnvironment);

        //when
        transactionController.begin();

        //then
        verify(transactionService).beginTransaction();
    }

    @Test
    public void shouldNotCallBeginServiceIfProdEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(prodEnvironment);

        //when
        transactionController.begin();

        //then
        verify(transactionService, never()).beginTransaction();
    }

    @Test
    public void shouldNotCallBeginServiceIfUATEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(uatEnvironment);

        //when
        transactionController.begin();

        //then
        verify(transactionService, never()).beginTransaction();
    }

    @Test
    public void shouldNotCallBeginServiceIfDevEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(prodEnvironment);

        //when
        transactionController.begin();

        //then
        verify(transactionService, never()).beginTransaction();
    }

}
