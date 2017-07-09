package com.monederobingo.database.common.db.adapter;

import java.sql.PreparedStatement;

class PreparedValueFactory
{
    static PreparedValue createPreparedValueFor(Object obj, PreparedStatement statement, int position) {

        if (obj == null)
            return new NullPreparedValue(statement, position);

        if (obj instanceof String)
            return new StringPreparedValue((String)obj, statement, position);

        if (obj instanceof Integer)
            return new IntegerPreparedValue((Integer)obj, statement, position);

        if (obj instanceof Boolean)
            return new BooleanPreparedValue((Boolean)obj, statement, position);

        if (obj instanceof Double)
            return new DoublePreparedValue((Double) obj, statement, position);

        if (obj instanceof Long)
            return new LongPreparedValue((Long) obj, statement, position);

        if (obj instanceof String[])
            return new StringArrayPreparedValue((String[]) obj, statement, position);

        throw new RuntimeException("Unsupported SQL type for object : " + obj);
    }
}
