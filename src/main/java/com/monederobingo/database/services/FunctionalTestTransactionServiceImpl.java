package com.monederobingo.database.services;

import com.monederobingo.database.api.interfaces.FunctionalTestTransactionService;
import com.monederobingo.database.common.context.ThreadContextService;
import com.monederobingo.database.common.db.queryagent.QueryAgentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FunctionalTestTransactionServiceImpl extends BaseServiceImpl implements FunctionalTestTransactionService
{

    @Autowired
    public FunctionalTestTransactionServiceImpl(ThreadContextService threadContextService, QueryAgentFactory queryAgentFactory) {
        super(threadContextService, queryAgentFactory);
    }

    @Override
    public void beginTransaction() {
        getQueryAgent().beginTransactionForFunctionalTest();
    }

    @Override
    public void rollbackTransaction() {
        getQueryAgent().rollbackTransactionForFunctionalTest();
    }
}
