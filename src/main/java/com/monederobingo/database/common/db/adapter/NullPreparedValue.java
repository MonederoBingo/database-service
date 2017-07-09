package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class NullPreparedValue extends PreparedValue<Object>
{

    NullPreparedValue(PreparedStatement statement, int position)
    {
        super(null, statement, position);
    }

    @Override
    public void prepare() throws SQLException
    {
        statement.setNull(position, Types.NULL);
    }
}
