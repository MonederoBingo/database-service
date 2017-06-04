package com.monederobingo.database.common.context;

import com.monederobingo.database.common.environments.Environment;

public interface ThreadContextService {

    void initializeContext(Environment env, String language);

    ThreadContext getThreadContext();

    Environment getEnvironment();

    void setThreadContextOnThread(ThreadContext threadContext);
}
