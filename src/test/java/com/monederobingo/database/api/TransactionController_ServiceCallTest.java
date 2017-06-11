package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.TransactionService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionController_ServiceCallTest
{
    private TransactionController transactionController;
    @Mock
    private TransactionService transactionService;
    @Mock
    private ThreadContextService threadContextService;
    @Mock
    private FunctionalTestEnvironment functionalTestEnvironment;
    @Mock
    private ProdEnvironment prodEnvironment;
    @Mock
    private UATEnvironment uatEnvironment;
    @Mock
    private DevEnvironment devEnvironment;

    @Before
    public void setUp() throws Exception
    {
        transactionController = new TransactionController(transactionService, threadContextService);
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
        given(threadContextService.getEnvironment()).willReturn(devEnvironment);

        //when
        transactionController.begin();

        //then
        verify(transactionService, never()).beginTransaction();
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
