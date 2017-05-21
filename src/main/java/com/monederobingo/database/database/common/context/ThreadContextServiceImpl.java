package com.monederobingo.database.database.common.context;

import com.monederobingo.database.database.common.db.queryagent.QueryAgent;
import com.monederobingo.database.database.common.db.queryagent.QueryAgentFactory;
import com.monederobingo.database.database.common.environments.Environment;
import com.monederobingo.database.database.common.i18n.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class ThreadContextServiceImpl implements ThreadContextService {
    private static final ThreadLocal<ThreadContext> THREAD_CONTEXT = new ThreadLocal<>();
    private final QueryAgentFactory _queryAgentFactory;

    @Autowired
    public ThreadContextServiceImpl(QueryAgentFactory queryAgentFactory) {
        _queryAgentFactory = queryAgentFactory;
    }

    @Override
    public void initializeContext(Environment environment, String language) {
        ThreadContext threadContext = new ThreadContext();
        final QueryAgent queryAgent = _queryAgentFactory.getQueryAgent(environment);
        threadContext.setClientQueryAgent(queryAgent);
        threadContext.setLanguage(Language.getByLangId(language));
        threadContext.setEnvironment(environment);
        setThreadContextOnThread(threadContext);
    }

    @Override
    public ThreadContext getThreadContext() {
        return THREAD_CONTEXT.get();
    }

    @Override
    public QueryAgent getQueryAgent() {
        return getThreadContext().getClientQueryAgent();
    }

    @Override
    public void setThreadContextOnThread(ThreadContext threadContext) {
        THREAD_CONTEXT.set(threadContext);
    }
}
