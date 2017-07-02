package com.monederobingo.database.common.db.adapter;

import com.monederobingo.libs.common.environments.Environment;

public interface DatabaseAdapterFactory
{
    DataBaseAdapter getQueryAgent(Environment environment) throws InterruptedException;
}
