package com.monederobingo.database.common.db.adapter;

import com.monederobingo.database.common.db.jdbc.SavepointProxyConnection;
import com.monederobingo.database.common.db.util.DbBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseAdapterTest
{
    private static final String ANY_SQL = "Any SQL";
    private static final Object[] VALUES = new Object[]{};

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
    private DbBuilder builder;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private JSONObject jsonObject;
    @Mock
    private JSONObject newJSONObject;
    @Mock
    private  PreparedStatementMapper preparedStatementMapper;
    @Mock
    private SavepointProxyConnection savepointProxyConnection;

    @Before
    public void setUp() throws Exception
    {
        databaseAdapter = new TestableDataBaseAdapter();
        given(dataSource.getConnection()).willReturn(connection);
        given(connection.createStatement()).willReturn(statement);
        given(statement.getGeneratedKeys()).willReturn(resultSet);
        given(connection.prepareStatement(any())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);
        given(builder.sql()).willReturn(ANY_SQL);
        given(builder.values()).willReturn(VALUES);
        given(connection.prepareStatement(ANY_SQL)).willReturn(preparedStatement);
        given(builder.build(resultSet)).willReturn(jsonObject);
    }

    @Test
    public void shouldGetConnectionFromDataSource() throws SQLException
    {
        //when
        databaseAdapter.getConnection();

        //then
        verify(dataSource).getConnection();
    }

    @Test
    public void shouldGetConnectionTwiceFromDataSource() throws SQLException
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
        verify(preparedStatementMapper).map(preparedStatement, VALUES);
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
        verify(preparedStatementMapper).map(preparedStatement, VALUES);
    }

    @Test
    public void selectList_whenResultSetHasNext_shouldPutBuiltResultInANewJSONArray() throws Exception
    {
        given(resultSet.next()).willReturn(true, true, false);

        JSONArray resultJSONArray = databaseAdapter.selectList(builder);

        assertThat(resultJSONArray.length(), is(2));
        assertThat(resultJSONArray.get(0), is(jsonObject));
        assertThat(resultJSONArray.get(1), is(jsonObject));
        verify(preparedStatementMapper).map(preparedStatement, VALUES);
    }

    @Test
    public void selectObject_whenExistsRecords_returnsBuiltResultSet() throws Exception
    {
        given(resultSet.next()).willReturn(true);

        JSONObject result = databaseAdapter.selectObject(builder);

        assertThat(result, is(jsonObject));
        verify(preparedStatementMapper).map(preparedStatement, VALUES);
    }

    @Test
    public void selectObject_whenDoNotExistRecords_returnANewJsonObject() throws Exception
    {
        given(resultSet.next()).willReturn(false);

        JSONObject result = databaseAdapter.selectObject(builder);

        assertThat(result, equalTo(newJSONObject));
        verify(preparedStatementMapper).map(preparedStatement, VALUES);
    }

    @Test
    public void beginTransaction_disableAutoCommitAndMarkAsInTransactionState() throws Exception
    {
        databaseAdapter.beginTransaction();

        verify(connection).setAutoCommit(false);
        assertThat(databaseAdapter.isInTransaction(), is(true));
    }

    @Test
    public void rollbackTransaction_whenExistsConnection_preparesConnectionForRollbackAndReleasesConnection() throws Exception
    {
        // used to create the connection
        databaseAdapter.getConnection();

        databaseAdapter.rollbackTransaction();

        verify(connection).rollback();
        verify(connection).setAutoCommit(true);
        assertThat(databaseAdapter.isInTransaction(), is(false));
        verify(connection).close();
    }

    @Test
    public void rollbackTransaction_whenDoesNotExistConnection_doesNothing() throws Exception
    {
        databaseAdapter.rollbackTransaction();

        verifyZeroInteractions(connection);
    }

    @Test
    public void beginTransactionForFunctionalTest_usesSavepointProxyConnectionAsConnection() throws Exception
    {
        databaseAdapter.beginTransactionForFunctionalTest();

        verify(savepointProxyConnection).beginTransactionForAutomationTest();

        assertThat(databaseAdapter.currentConnection(), is(savepointProxyConnection));
    }

    @Test
    public void rollbackTransactionForFunctionalTest_usesSavepointProxyConnectionAsConnectionAndSetNullCurrentConnection() throws Exception
    {
        databaseAdapter.rollbackTransactionForFunctionalTest();

        verify(savepointProxyConnection).rollbackTransactionForAutomationTest();

        assertNull(databaseAdapter.currentConnection());
    }

    private class TestableDataBaseAdapter extends DataBaseAdapter
    {
        TestableDataBaseAdapter()
        {
            super(dataSource, preparedStatementMapper);
        }

        @Override
        JSONObject newJSONObject()
        {
            return newJSONObject;
        }

        @Override
        SavepointProxyConnection getConnectionForFunctionalTests() throws SQLException {
            return savepointProxyConnection;
        }
    }
}
