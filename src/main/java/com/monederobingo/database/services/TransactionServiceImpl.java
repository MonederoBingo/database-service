package com.monederobingo.database.services;

import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.common.db.queryagent.QueryAgentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class TransactionServiceImpl extends BaseServiceImpl implements TransactionService
{
    @Autowired
    public TransactionServiceImpl(ThreadContextService threadContextService, QueryAgentFactory queryAgentFactory) {
        super(threadContextService, queryAgentFactory);
    }

    @Override
    public void beginTransaction() throws SQLException
    {
        getQueryAgent().beginTransactionForFunctionalTest();
    }

    @Override
    public void rollbackTransaction() throws SQLException
    {
        getQueryAgent().rollbackTransactionForFunctionalTest();
    }
}
