package com.monederobingo.database.api.interfaces;

public interface TransactionService
{
    void beginTransaction();

    void rollbackTransaction();
}
