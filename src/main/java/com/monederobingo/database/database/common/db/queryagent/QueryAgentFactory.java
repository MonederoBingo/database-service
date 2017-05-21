package com.monederobingo.database.database.common.db.queryagent;

import com.monederobingo.database.database.common.environments.Environment;

public interface QueryAgentFactory {
    QueryAgent getQueryAgent(Environment environment);
}
