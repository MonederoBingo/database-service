package com.monederobingo.database.common.db.adapter;

import com.monederobingo.database.common.db.util.DbBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseAdapterTest
{

    private DataBaseAdapter databaseAdapter;
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private DbBuilder<String> builder;
    @Mock
    private PreparedStatement preparedStatement;

    @Before
    public void setUp() throws Exception
    {
        databaseAdapter = new DataBaseAdapter(dataSource);
        given(dataSource.getConnection()).willReturn(connection);
        given(connection.createStatement()).willReturn(statement);
        given(statement.getGeneratedKeys()).willReturn(resultSet);
        given(connection.prepareStatement(any())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);
    }

    @Test
    public void shouldGetConnectionFromDatasource() throws SQLException
    {
        //when
        databaseAdapter.getConnection();

        //then
        verify(dataSource).getConnection();
    }

    @Test
    public void shouldGetConnectionTwiceFromDatasource() throws SQLException
    {
        //given
        databaseAdapter.getConnection();

        //when
        databaseAdapter.getConnection();

        //then
        verify(dataSource, times(1)).getConnection();
    }

    @Test
    public void executeInsertShouldCreateStatementOnConnection() throws Exception
    {
        //when
        databaseAdapter.executeInsert("", "");

        //then
        verify(connection).createStatement();
    }

    @Test
    public void executeInsertShouldExecuteOnStatement() throws Exception
    {
        //when
        databaseAdapter.executeInsert("query", "");

        //then
        verify(statement).execute("query", RETURN_GENERATED_KEYS);
    }

    @Test
    public void executeInsertShouldKeyOnResultSet() throws Exception
    {
        //when
        databaseAdapter.executeInsert("query", "id");

        //then
        verify(resultSet).next();
        verify(resultSet).getLong("id");
    }

    @Test
    public void executeInsertShouldReturnGeneratedKeysOnStatement() throws Exception
    {
        //when
        databaseAdapter.executeInsert("query", "");

        //then
        verify(statement).getGeneratedKeys();
    }

    @Test
    public void executeInsertShouldCloseConnection() throws Exception
    {
        //when
        databaseAdapter.executeInsert("query", "");

        //then
        verify(connection).close();
    }

    @Test
    public void executeUpdateShouldCreateStatementOnConnection() throws Exception
    {
        //when
        databaseAdapter.executeUpdate("");

        //then
        verify(connection).createStatement();
    }

    @Test
    public void executeUpdateShouldExecuteUpdateOnStatement() throws Exception
    {
        //when
        databaseAdapter.executeUpdate("query");

        //then
        verify(statement).executeUpdate("query");
    }

    @Test
    public void executeUpdateShouldCloseConnection() throws Exception
    {
        //when
        databaseAdapter.executeUpdate("query");

        //then
        verify(connection).close();
    }

    @Test
    public void selectListShouldCallPrepareStatementOnConnection() throws Exception
    {
        //given
        given(builder.sql()).willReturn("query");

        //when
        databaseAdapter.selectList(builder);

        //then
        verify(connection).prepareStatement("query");
    }

    @Test
    public void selectListShouldExecuteQueryOnPreparedStatement() throws Exception
    {
        //given
        given(builder.sql()).willReturn("query");

        //when
        databaseAdapter.selectList(builder);

        //then
        verify(connection).prepareStatement("query");
    }
}
