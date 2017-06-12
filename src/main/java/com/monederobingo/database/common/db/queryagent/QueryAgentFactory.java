package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.libs.common.environments.Environment;

public interface QueryAgentFactory
{
    QueryAgent getQueryAgent(Environment environment) throws InterruptedException;
}
