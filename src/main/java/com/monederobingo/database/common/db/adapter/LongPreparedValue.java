package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongPreparedValue extends PreparedValue<Long>
{

    LongPreparedValue(Long obj, PreparedStatement statement, int position)
    {
        super(obj, statement, position);
    }

    @Override
    public void prepare() throws SQLException
    {
        statement.setLong(position, value);
    }
}
