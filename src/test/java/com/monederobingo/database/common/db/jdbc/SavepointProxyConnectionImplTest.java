package com.monederobingo.database.common.db.jdbc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SavepointProxyConnectionImplTest
{
    private SavepointProxyConnectionImpl savepointProxyConnection;
    @Mock
    private Connection wrappedConnection;
    @Mock
    private SavepointPgProxyDriver driver;
    @Mock
    private Savepoint savepoint;

    @Before
    public void setUp() throws Exception
    {
        savepointProxyConnection = new SavepointProxyConnectionImpl(wrappedConnection, driver);
        given(wrappedConnection.setSavepoint(anyString())).willReturn(savepoint);
    }

    @Test
    public void shouldCallCreateStatementInWrappedConnection() throws SQLException
    {
        //when
        savepointProxyConnection.createStatement();

        //then
        verify(wrappedConnection).createStatement();
    }

    @Test
    public void shouldCallPreparedStatementInWrappedConnection() throws SQLException
    {
        //when
        savepointProxyConnection.prepareStatement("SELECT * FROM dummy;");

        //then
        verify(wrappedConnection).prepareStatement("SELECT * FROM dummy;");
    }

    @Test
    public void shouldCallPrepareCallInWrappedConnection() throws SQLException
    {
        //when
        savepointProxyConnection.prepareCall("SELECT * FROM dummy;");

        //then
        verify(wrappedConnection).prepareCall("SELECT * FROM dummy;");
    }

    @Test
    public void shouldCallNativeSQLInWrappedConnection() throws SQLException
    {
        //when
        savepointProxyConnection.nativeSQL("SELECT * FROM dummy;");

        //then
        verify(wrappedConnection).nativeSQL("SELECT * FROM dummy;");
    }

    @Test
    public void shouldCallGetAutoCommitInWrappedConnection() throws SQLException
    {
        //when
        savepointProxyConnection.getAutoCommit();

        //then
        verify(wrappedConnection).getAutoCommit();
    }

    @Test
    public void shouldNotCallGetAutoCommitInWrappedConnectionWhenProxyConnectionIsActive() throws SQLException
    {
        //given
        savepointProxyConnection.beginTransactionForAutomationTest();

        //when
        savepointProxyConnection.getAutoCommit();

        //then
        verify(wrappedConnection, never()).getAutoCommit();
    }

    @Test
    public void shouldCallSetSavepointInWrappedConnectionWhenProxyConnectionIsActiveAndAutoCommitIsFalse() throws SQLException
    {
        //given
        savepointProxyConnection.beginTransactionForAutomationTest();

        //when
        savepointProxyConnection.setAutoCommit(false);

        //then
        verify(wrappedConnection, times(2)).setSavepoint(anyString());
    }

    @Test
    public void shouldNotCallSetSavepointInWrappedConnectionWhenProxyConnectionIsActiveAndAutoCommitIsTrue() throws SQLException
    {
        //given
        savepointProxyConnection.beginTransactionForAutomationTest();

        //when
        savepointProxyConnection.setAutoCommit(true);

        //then
        verify(wrappedConnection, times(1)).setSavepoint(anyString());
    }

    @Test
    public void shouldCallReleaseSavepointInWrappedConnectionWhenProxyConnectionIsActiveAndAutoCommitIsFalse() throws SQLException
    {
        //given
        savepointProxyConnection.beginTransactionForAutomationTest();
        savepointProxyConnection.setAutoCommit(false);

        //when
        savepointProxyConnection.setAutoCommit(false);

        //then
        verify(wrappedConnection, times(2)).releaseSavepoint(any(Savepoint.class));
    }

    @Test
    public void shouldCallSetAutoCommitInWrappedConnectionWhenConnectionIsNotActiveAndAutoCommitIsTrue() throws SQLException
    {
        //when
        savepointProxyConnection.setAutoCommit(true);

        //then
        verify(wrappedConnection).setAutoCommit(true);
    }

    @Test
    public void shouldCallSetAutoCommitInWrappedConnectionWhenConnectionIsActiveAndAutoCommitIsTrueInWrappedConnection() throws SQLException
    {
        //given
        given(wrappedConnection.getAutoCommit()).willReturn(true);

        //when
        savepointProxyConnection.setAutoCommit(false);

        //then
        verify(wrappedConnection).setAutoCommit(false);
    }

    //TODO: Continue with tests for commit method
}
