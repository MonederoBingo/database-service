package com.monederobingo.database.services;

import com.monederobingo.database.common.context.ThreadContext;
import com.monederobingo.database.common.context.ThreadContextService;
import com.monederobingo.database.common.db.queryagent.QueryAgent;
import com.monederobingo.database.common.db.queryagent.QueryAgentFactory;
import com.monederobingo.database.common.environments.DevEnvironment;
import com.monederobingo.database.common.environments.Environment;
import com.monederobingo.database.common.environments.FunctionalTestEnvironment;
import com.monederobingo.database.common.environments.ProdEnvironment;
import com.monederobingo.database.common.environments.UATEnvironment;

public class BaseServiceImpl
{

    private final ThreadContextService _threadContextService;
    private final QueryAgentFactory queryAgentFactory;

    public BaseServiceImpl(ThreadContextService threadContextService, QueryAgentFactory queryAgentFactory)
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

    public QueryAgent getQueryAgent()
    {
        return queryAgentFactory.getQueryAgent(_threadContextService.getEnvironment());
    }
}
