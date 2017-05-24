package com.monederobingo.database.services;

import com.monederobingo.database.api.interfaces.FunctionalTestTransactionService;
import com.monederobingo.database.common.context.ThreadContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FunctionalTestTransactionServiceImpl extends BaseServiceImpl implements FunctionalTestTransactionService
{

    @Autowired
    public FunctionalTestTransactionServiceImpl(ThreadContextService threadContextService) {
        super(threadContextService);
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
