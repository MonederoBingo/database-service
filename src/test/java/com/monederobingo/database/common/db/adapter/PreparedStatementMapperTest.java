package com.monederobingo.database.common.db.adapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PreparedStatementMapperTest
{

    private static final Object[] NO_VALUES = null;
    private static final String STRING_VALUE = "Any String";
    private static final int INTEGER_VALUE = 10;
    private static final boolean BOOLEAN_VALUE = Boolean.TRUE;
    private static final double DOUBLE_VALUE = 0.15d;
    private static final long LONG_VALUE = 15L;
    private static final String[] STRING_ARRAY_VALUE = {"value1", "value2"};
    private static final Object[] VALID_VALUES = {null, STRING_VALUE, INTEGER_VALUE, BOOLEAN_VALUE, DOUBLE_VALUE, LONG_VALUE, STRING_ARRAY_VALUE};
    @Mock
    private PreparedStatement statement;
    @Mock
    private Connection connection;
    @Mock
    private Array anArray;
    private PreparedStatementMapper mapper;
    private Object stubObject = createStubObject();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception
    {
        mapper = new PreparedStatementMapper();
        given(statement.getConnection()).willReturn(connection);
        given(connection.createArrayOf("varchar", STRING_ARRAY_VALUE)).willReturn(anArray);
    }

    @Test
    public void map_forNullValues_doNothing() throws Exception
    {
        mapper.map(statement, NO_VALUES);
        verifyZeroInteractions(statement);
    }

    @Test
    public void map_forValidValues_setValuesWithRespectiveObject() throws Exception
    {
        mapper.map(statement, VALID_VALUES);
        verify(statement).setNull(1, Types.NULL);
        verify(statement).setString(2, STRING_VALUE);
        verify(statement).setInt(3, INTEGER_VALUE);
        verify(statement).setBoolean(4, BOOLEAN_VALUE);
        verify(statement).setDouble(5, DOUBLE_VALUE);
        verify(statement).setLong(6, LONG_VALUE);
        verify(statement).setArray(7, anArray);
    }

    @Test
    public void map_forInvalidObject_throwsAnRuntimeException() throws Exception
    {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Unsupported SQL type for object : Test Object");

        mapper.map(statement, new Object[]{stubObject});

    }

    private Object createStubObject()
    {
        return new Object(){
            @Override
            public String toString() {
                return "Test Object";
            }
        };
    }
}