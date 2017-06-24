package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.libs.common.environments.Environment;

public interface QueryAgentFactory
{
    DataBaseAdapter getQueryAgent(Environment environment) throws InterruptedException;
}
