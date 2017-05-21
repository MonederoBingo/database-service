package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.database.common.environments.Environment;

public interface QueryAgentFactory {
    QueryAgent getQueryAgent(Environment environment);
}
