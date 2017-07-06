package com.monederobingo.database.common.db.adapter;

import com.monederobingo.libs.common.environments.Environment;

public interface DatabaseAdapterFactory
{
    DataBaseAdapter getDatabaseAdapter(Environment environment) throws InterruptedException;
}
