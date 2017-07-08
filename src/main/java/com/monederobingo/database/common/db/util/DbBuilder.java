package com.monederobingo.database.common.db.util;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbBuilder
{
    public abstract String sql() throws SQLException;

    public abstract Object[] values();

    public abstract JSONObject build(ResultSet resultSet) throws Exception;
}
