package com.monederobingo.database.services;

import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl extends BaseServiceImpl implements TransactionService
{
    @Autowired
    public TransactionServiceImpl(ThreadContextService threadContextService, DatabaseAdapterFactory queryAgentFactory) {
        super(threadContextService, queryAgentFactory);
    }

    @Override
    public void beginTransaction() throws Exception
    {
        getQueryAgent().beginTransactionForFunctionalTest();
    }

    @Override
    public void rollbackTransaction() throws Exception
    {
        getQueryAgent().rollbackTransactionForFunctionalTest();
    }
}
