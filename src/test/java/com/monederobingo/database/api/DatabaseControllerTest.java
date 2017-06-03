package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.UpdateQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseControllerTest
{
    private DatabaseController controller;
    @Mock
    private DatabaseService service;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service);
    }

    @Test public void shouldCallDatabaseServiceForSelect() throws Exception
    {
        //given
        SelectQuery selectQuery = new SelectQuery();

        //when
        controller.select(selectQuery);

        //then
        verify(service).select(selectQuery);
    }

    @Test
    public void shouldCallDatabaseServiceForSelectList() throws Exception
    {
        //given
        SelectQuery selectQuery = new SelectQuery();

        //when
        controller.selectList(selectQuery);

        //then
        verify(service).selectList(selectQuery);
    }

    @Test
    public void shouldCallDatabaseServiceForInsert() throws Exception
    {
        //given
        InsertQuery insertQuery = new InsertQuery();

        //when
        controller.insert(insertQuery);

        //then
        verify(service).insert((insertQuery));
    }

    @Test
    public void shouldCallDatabaseServiceForUpdate() throws Exception
    {
        //given
        UpdateQuery updateQuery = new UpdateQuery();

        //when
        controller.update(updateQuery);

        //then
        verify(service).update(updateQuery);
    }

}
