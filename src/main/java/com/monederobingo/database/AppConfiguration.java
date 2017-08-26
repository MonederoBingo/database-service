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
    public DevEnvironment getOldDevEnvironment()
    {
        return new DevEnvironment();
    }

    @Bean
    public FunctionalTestEnvironment getFunctionalTestEnvironment()
    {
        return new FunctionalTestEnvironment();
    }

    @Bean
    public UATEnvironment getOldUATEnvironment()
    {
        return new UATEnvironment();
    }

    @Bean
    public ProdEnvironment getOldProdEnvironment()
    {
        return new ProdEnvironment();
    }

    @Bean
    public ContextFilter getOldContextFilter()
    {
        return new ContextFilter(getOldThreadContextService(), getEnvironmentFactory());
    }

    @Bean
    public xyz.greatapp.libs.service.context.ContextFilter getContextFilter()
    {
        return new xyz.greatapp.libs.service.context.ContextFilter(getThreadContextService());
    }

    @Bean(name = "newThreadContextService")
    public xyz.greatapp.libs.service.context.ThreadContextService getThreadContextService() {
        return new xyz.greatapp.libs.service.context.ThreadContextServiceImpl();
    }

    @Bean
    public DatabaseAdapterFactory getDatabaseAdapterFactory() {
        return new DatabaseAdapterFactory(new DataSourceFactory(new DriverManagerDataSourceFactory()),
                getDevEnvironment(),
                getUATEnvironment(),
                getProdEnvironment(),
                getAutomationTestEnvironment(),
                getIntegrationTestEnvironment());
    }

    @Bean
    public AutomationTestEnvironment getAutomationTestEnvironment() {
        return new AutomationTestEnvironment();
    }

    @Bean
    public xyz.greatapp.libs.database.environments.DevEnvironment getDevEnvironment() {
        return new xyz.greatapp.libs.database.environments.DevEnvironment();
    }

    @Bean
    public xyz.greatapp.libs.database.environments.UATEnvironment getUATEnvironment() {
        return new xyz.greatapp.libs.database.environments.UATEnvironment();
    }

    @Bean
    public xyz.greatapp.libs.database.environments.ProdEnvironment getProdEnvironment() {
        return new xyz.greatapp.libs.database.environments.ProdEnvironment();
    }

    @Bean
    public xyz.greatapp.libs.database.environments.IntegrationTestEnvironment getIntegrationTestEnvironment() {
        return new xyz.greatapp.libs.database.environments.IntegrationTestEnvironment();
    }

    private EnvironmentFactory getEnvironmentFactory()
    {
        return new EnvironmentFactory(
                getOldDevEnvironment(),
                getUnitTestEnvironment(),
                getFunctionalTestEnvironment(),
                getOldUATEnvironment(),
                getOldProdEnvironment()
        );
    }
}
