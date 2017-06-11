package com.monederobingo.database.api.interfaces;

import java.sql.SQLException;

public interface TransactionService
{
    void beginTransaction() throws SQLException;

    void rollbackTransaction() throws SQLException;
}
