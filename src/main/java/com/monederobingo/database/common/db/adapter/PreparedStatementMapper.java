package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.monederobingo.database.common.db.adapter.PreparedValueFactory.createPreparedValueFor;

class PreparedStatementMapper {
    void map(PreparedStatement statement, Object[] values) throws SQLException
    {
        if (values != null)
        {
            int position = 1;
            for (Object value : values) {
                createPreparedValueFor(value, statement, position++)
                        .prepare();

            }
        }
    }
}
