package com.monederobingo.database.api;

import static com.monederobingo.database.api.ControllerAssertions.oldAssertFailedResponse;
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
import com.monederobingo.libs.common.environments.DevEnvironment;
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
public class TransactionController_RollbackTest
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
    private DevEnvironment devEnvironment;
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
    public void shouldReturnErrorResponseForRollbackWhenExceptionIsCaught() throws Exception
    {
        //given
        doThrow(new RuntimeException()).when(transactionService).rollbackTransaction();

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.rollback();

        //then
        oldAssertFailedResponse(response, serviceLogger);
    }

    @Test
    public void shouldReturnSuccessfulResponseOnRollback() {

        //when
        ResponseEntity<ServiceResult<Object>> response = transactionController.rollback();

        //then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldCallRollbackServiceIfFunctionalTestEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(functionalTestEnvironment);

        //when
        transactionController.rollback();

        //then
        verify(transactionService).rollbackTransaction();
    }

    @Test
    public void shouldNotCallRollbackServiceIfProdEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(prodEnvironment);

        //when
        transactionController.rollback();

        //then
        verify(transactionService, never()).rollbackTransaction();
    }

    @Test
    public void shouldNotCallRollbackServiceIfUATEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(uatEnvironment);

        //when
        transactionController.rollback();

        //then
        verify(transactionService, never()).rollbackTransaction();
    }

    @Test
    public void shouldNotCallRollbackServiceIfDevEnvironment() throws Exception
    {
        //given
        given(threadContextService.getEnvironment()).willReturn(devEnvironment);

        //when
        transactionController.rollback();

        //then
        verify(transactionService, never()).rollbackTransaction();
    }
}
