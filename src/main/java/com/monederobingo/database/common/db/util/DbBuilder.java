package com.monederobingo.database.common.db.util;

import com.monederobingo.database.model.DbValue;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbBuilder<T> {
    public abstract String sql() throws SQLException;

    public abstract DbValue[] values();

    public abstract T build(ResultSet resultSet) throws SQLException;
}
