package com.monederobingo.database;

import com.monederobingo.libs.api.filters.ContextFilter;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.context.ThreadContextServiceImpl;
import com.monederobingo.libs.common.environments.DevEnvironment;
import com.monederobingo.libs.common.environments.EnvironmentFactory;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import com.monederobingo.libs.common.environments.ProdEnvironment;
import com.monederobingo.libs.common.environments.UATEnvironment;
import com.monederobingo.libs.common.environments.UnitTestEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.greatapp.libs.database.adapter.DatabaseAdapterFactory;
import xyz.greatapp.libs.database.datasources.DataSourceFactory;
import xyz.greatapp.libs.database.datasources.DriverManagerDataSourceFactory;
import xyz.greatapp.libs.database.environments.AutomationTestEnvironment;
import xyz.greatapp.libs.database.environments.IntegrationTestEnvironment;

@Configuration
public class AppConfiguration
{
    @Bean
    public ThreadContextService getOldThreadContextService()
    {
        return new ThreadContextServiceImpl();
    }

    @Bean
    public UnitTestEnvironment getUnitTestEnvironment()
    {
        return new UnitTestEnvironment();
    }

    @Bean
    public DevEnvironment getDevEnvironment()
    {
        return new DevEnvironment();
    }

    @Bean
    public FunctionalTestEnvironment getFunctionalTestEnvironment()
    {
        return new FunctionalTestEnvironment();
    }

    @Bean
    public UATEnvironment getUATEnvironment()
    {
        return new UATEnvironment();
    }

    @Bean
    public ProdEnvironment getProdEnvironment()
    {
        return new ProdEnvironment();
    }

    @Bean
    public ContextFilter getContextFilter()
    {
        return new ContextFilter(getOldThreadContextService(), getEnvironmentFactory());
    }

    @Bean(name = "newThreadContextService")
    public xyz.greatapp.libs.service.context.ThreadContextService getThreadContextService() {
        return new xyz.greatapp.libs.service.context.ThreadContextServiceImpl();
    }

    @Bean
    public DatabaseAdapterFactory getDatabaseAdapterFactory() {
        return new DatabaseAdapterFactory(new DataSourceFactory(new DriverManagerDataSourceFactory()),
                new xyz.greatapp.libs.database.environments.DevEnvironment(),
                new xyz.greatapp.libs.database.environments.UATEnvironment(),
                new xyz.greatapp.libs.database.environments.ProdEnvironment(),
                new AutomationTestEnvironment(),
                new IntegrationTestEnvironment());
    }

    private EnvironmentFactory getEnvironmentFactory()
    {
        return new EnvironmentFactory(
                getDevEnvironment(),
                getUnitTestEnvironment(),
                getFunctionalTestEnvironment(),
                getUATEnvironment(),
                getProdEnvironment()
        );
    }
}
