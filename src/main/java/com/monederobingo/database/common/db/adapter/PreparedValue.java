package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class PreparedValue<T>
{
    protected final T value;
    protected final PreparedStatement statement;
    protected final int position;

    PreparedValue(T value, PreparedStatement statement, int position)
    {
        this.value = value;
        this.statement = statement;
        this.position = position;
    }

    abstract void prepare() throws SQLException;
}
