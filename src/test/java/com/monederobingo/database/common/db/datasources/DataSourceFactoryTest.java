package com.monederobingo.database.common.db.datasources;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.monederobingo.libs.common.environments.ProdEnvironment;
import com.monederobingo.libs.common.environments.UATEnvironment;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceFactoryTest
{
    private DataSourceFactory dataSourceFactory;
    @Mock
    private ProdEnvironment prodEnvironment;
    @Mock
    private UATEnvironment uatEnvironment;
    @Mock
    private DriverManagerDataSourceFactory driverManagerDataSourceFactory;
    @Mock
    private DriverManagerDataSource driverManagerDataSource;

    @Before
    public void setUp() throws Exception
    {
        dataSourceFactory = new DataSourceFactory(driverManagerDataSourceFactory);
        given(driverManagerDataSourceFactory.createDriverManagerDataSource()).willReturn(driverManagerDataSource);
    }

    @Test
    public void shouldReturnNewDataSource() throws InterruptedException
    {
        // when
        DataSource dataSource = dataSourceFactory.getDataSource(prodEnvironment);

        // then
        assertNotNull(dataSource);
    }

    @Test
    public void shouldCreateOnlyOneDataSourceForSameEnvironment() throws InterruptedException
    {

        // when
        dataSourceFactory.getDataSource(prodEnvironment);
        dataSourceFactory.getDataSource(prodEnvironment);

        // then
        verify(driverManagerDataSourceFactory, times(1)).createDriverManagerDataSource();
    }

    @Test
    public void shouldCreateOneDataSourceForEveryDifferentEnvironment() throws InterruptedException
    {

        // when
        dataSourceFactory.getDataSource(prodEnvironment);
        dataSourceFactory.getDataSource(prodEnvironment);
        dataSourceFactory.getDataSource(uatEnvironment);

        // then
        verify(driverManagerDataSourceFactory, times(2)).createDriverManagerDataSource();
    }
}
