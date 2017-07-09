package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringPreparedValue extends PreparedValue<String>
{

    StringPreparedValue(String obj, PreparedStatement statement, int position)
    {

        super(obj, statement, position);
    }

    @Override
    public void prepare() throws SQLException
    {
        statement.setString(position, value);
    }
}
