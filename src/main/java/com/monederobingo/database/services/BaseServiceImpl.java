package com.monederobingo.database.services;

import com.monederobingo.libs.common.context.ThreadContext;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.environments.DevEnvironment;
import com.monederobingo.libs.common.environments.Environment;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import com.monederobingo.libs.common.environments.ProdEnvironment;
import com.monederobingo.libs.common.environments.UATEnvironment;
import com.monederobingo.database.common.db.adapter.DataBaseAdapter;
import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactory;

public class BaseServiceImpl
{

    private final ThreadContextService _threadContextService;
    private final DatabaseAdapterFactory queryAgentFactory;

    public BaseServiceImpl(ThreadContextService threadContextService, DatabaseAdapterFactory queryAgentFactory)
    {
        _threadContextService = threadContextService;
        this.queryAgentFactory = queryAgentFactory;
    }

    public boolean isProdEnvironment()
    {
        return _threadContextService.getThreadContext().getEnvironment() instanceof ProdEnvironment;
    }

    public boolean isUATEnvironment()
    {
        return _threadContextService.getThreadContext().getEnvironment() instanceof UATEnvironment;
    }

    public boolean isDevEnvironment()
    {
        return _threadContextService.getThreadContext().getEnvironment() instanceof DevEnvironment;
    }

    public boolean isFunctionalTestEnvironment()
    {
        return _threadContextService.getThreadContext().getEnvironment() instanceof FunctionalTestEnvironment;
    }

    public Environment getEnvironment()
    {
        return _threadContextService.getThreadContext().getEnvironment();
    }

    public ThreadContextService getThreadContextService()
    {
        return _threadContextService;
    }

    public ThreadContext getThreadContext()
    {
        return _threadContextService.getThreadContext();
    }

    DataBaseAdapter getQueryAgent() throws InterruptedException
    {
        return queryAgentFactory.getQueryAgent(_threadContextService.getEnvironment());
    }
}
