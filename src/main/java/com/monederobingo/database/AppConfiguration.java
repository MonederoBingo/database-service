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

@Configuration
public class AppConfiguration
{
    @Bean
    public ThreadContextService getThreadContextService()
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
        return new ContextFilter(getThreadContextService(), getEnvironmentFactory());
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
