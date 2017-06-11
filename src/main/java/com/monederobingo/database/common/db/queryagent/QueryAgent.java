package com.monederobingo.database.common.db.queryagent;

import com.monederobingo.database.common.db.jdbc.SavepointProxyConnection;
import com.monederobingo.database.common.db.util.DbBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class QueryAgent
{

    private final transient DataSource _dataSource;
    private transient Connection _connection = null;
    private boolean _isInTransaction;

    QueryAgent(DataSource dataSource)
    {
        _dataSource = dataSource;
    }

    /**
     * Creates a new connection to a postgres database
     *
     * @return The established connection.
     */
    Connection getConnection()
    {
        if (_connection == null)
        {
            try
            {
                _connection = _dataSource.getConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return _connection;
    }

    /**
     * Executes an insert statement.
     *
     * @param sql Insert sql query to be executed.
     * @param id  Column name of the generated key to be returned from the query execution.
     * @return The generated key of the id column returned from the query execution.
     */
    public long executeInsert(String sql, String id) throws Exception
    {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement())
        {
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(id);
        }
        finally
        {
            releaseConnectionIfPossible();
        }
    }

    /**
     * Executes and update statement.
     *
     * @param sql Update statement to be executed.
     * @return The number of rows returned from the query.
     */
    public int executeUpdate(String sql) throws Exception
    {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement())
        {
            return statement.executeUpdate(sql);
        }
        finally
        {
            releaseConnectionIfPossible();
        }
    }

    /**
     * Executes a Select statement in the database and returns multiple rows.
     *
     * @param builder Helper object that contains placeholders and build method
     * @param <T>     Type of object to be returned as list and built by DbBuilder
     * @return The list of type T built from the select statement execution
     */
    public synchronized <T> List<T> selectList(DbBuilder<T> builder) throws Exception
    {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(builder.sql()))
        {
            setValues(statement, builder.values());

            try (ResultSet resultSet = statement.executeQuery())
            {
                List<T> results = new ArrayList<>();
                while (resultSet.next())
                {
                    results.add(builder.build(resultSet));
                }
                return results;
            }
        }
        finally
        {
            releaseConnectionIfPossible();
        }
    }

    /**
     * Executes a Select statement in the database and returns only one object
     *
     * @param builder Helper object that contains placeholders and build method
     * @param <T>     Type of object to be returned and built by DbBuilder
     * @return The type T built from the select statement execution
     */
    public synchronized <T> T selectObject(DbBuilder<T> builder) throws Exception
    {
        try (PreparedStatement statement = getConnection().prepareStatement(builder.sql()))
        {
            setValues(statement, builder.values());
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (!resultSet.next())
                {
                    return null;
                }
                return builder.build(resultSet);
            }
        }
        finally
        {
            releaseConnectionIfPossible();
        }
    }

    synchronized void beginTransaction() throws Exception
    {
        getConnection().setAutoCommit(false);
        _isInTransaction = true;
    }

    synchronized void rollbackTransaction() throws Exception
    {
        if (_connection != null)
        {
            try
            {
                getConnection().rollback();
                getConnection().setAutoCommit(true);
                _isInTransaction = false;
            }
            finally
            {
                releaseConnectionIfPossible();
            }
        }
    }

    boolean isInTransaction()
    {
        return _isInTransaction;
    }

    private synchronized void releaseConnectionIfPossible()
    {
        if (!isInTransaction() && _connection != null)
        {
            try
            {
                _connection.close();
            }
            catch (SQLException e)
            {
                throw new RuntimeException("Error releasing connection");
            }
            _connection = null;
        }
    }

    private void setValues(PreparedStatement statement, Object... values) throws SQLException
    {
        if (values == null)
        {
            return;
        }
        for (int i = 0; i < values.length; i++)
        {
            Object obj = values[i];
            if (obj == null)
            {
                statement.setNull(i + 1, Types.NULL);
            }
            else if (obj instanceof String)
            {
                statement.setString(i + 1, (String) obj);
            }
            else if (obj instanceof Integer)
            {
                statement.setInt(i + 1, (Integer) obj);
            }
            else if (obj instanceof Boolean)
            {
                statement.setBoolean(i + 1, (Boolean) obj);
            }
            else if (obj instanceof Double)
            {
                statement.setDouble(i + 1, (Double) obj);
            }
            else if (obj instanceof Long)
            {
                statement.setLong(i + 1, (Long) obj);
            }
            else if (obj instanceof String[])
            {
                statement.setArray(i + 1, statement.getConnection().createArrayOf("varchar", (String[]) obj));
            }
            else
            {
                throw new RuntimeException("Unsupported SQL type for object : " + obj);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (_connection != null)
        {
            _connection.close();
            _connection = null;
        }
        super.finalize();
    }

    public synchronized void beginTransactionForFunctionalTest() throws SQLException
    {
        SavepointProxyConnection connection = (SavepointProxyConnection) getConnection();
        connection.beginTransactionForAutomationTest();
        _connection = connection;
    }

    public synchronized void rollbackTransactionForFunctionalTest() throws SQLException
    {
        SavepointProxyConnection connection = (SavepointProxyConnection) getConnection();
        connection.rollbackTransactionForAutomationTest();
        _connection = null;
    }
}
