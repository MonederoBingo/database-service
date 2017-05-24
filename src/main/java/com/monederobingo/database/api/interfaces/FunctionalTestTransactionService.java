package com.monederobingo.database.api.interfaces;

public interface FunctionalTestTransactionService
{
    void beginTransaction();

    void rollbackTransaction();
}
