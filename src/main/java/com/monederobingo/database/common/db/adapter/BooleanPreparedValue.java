package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooleanPreparedValue extends PreparedValue<Boolean>
{

    BooleanPreparedValue(Boolean obj, PreparedStatement statement, int position)
    {
        super(obj, statement, position);
    }

    @Override
    public void prepare() throws SQLException
    {
        statement.setBoolean(position, value);
    }
}
