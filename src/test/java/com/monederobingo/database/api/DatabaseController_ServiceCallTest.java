package com.monederobingo.database.api;

import static org.mockito.Mockito.verify;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.UpdateQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseController_ServiceCallTest
{
    private DatabaseController controller;
    private SelectQuery selectQuery;
    private InsertQuery insertQuery;
    private UpdateQuery updateQuery;

    @Mock
    private DatabaseService service;
    @Mock
    private ServiceLogger logger;

    @Before
    public void setUp() throws Exception
    {
        controller = new DatabaseController(service, logger);
        selectQuery = new SelectQuery();
        insertQuery = new InsertQuery();
        updateQuery = new UpdateQuery();
    }

    @Test
    public void shouldCallDatabaseServiceForSelect() throws Exception
    {
        // when
        controller.select(selectQuery);

        // then
        verify(service).select(selectQuery);
    }

    @Test
    public void shouldCallDatabaseServiceForSelectList() throws Exception
    {
        // when
        controller.selectList(selectQuery);

        // then
        verify(service).selectList(selectQuery);
    }

    @Test
    public void shouldCallDatabaseServiceForInsert() throws Exception
    {
        // when
        controller.insert(insertQuery);

        // then
        verify(service).insert((insertQuery));
    }

    @Test
    public void shouldCallDatabaseServiceForUpdate() throws Exception
    {
        // when
        controller.update(updateQuery);

        // then
        verify(service).update(updateQuery);
    }
}
