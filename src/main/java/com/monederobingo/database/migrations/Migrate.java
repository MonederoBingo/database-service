package com.monederobingo.database.migrations;

import org.flywaydb.core.Flyway;

import static java.lang.System.getenv;

public class Migrate
{
    private final static String[] schemas = new String[] { "monedero", "monedero_test" };

    public static void main(String[] args)
    {
        Flyway flyway = new Flyway();
        flyway.setDataSource(
                getenv("JDBC_DATABASE_URL"),
                getenv("JDBC_DATABASE_USERNAME"),
                getenv("JDBC_DATABASE_PASSWORD"));
        flyway.setLocations("classpath:db/migration");
        for (String schema : schemas)
        {
            flyway.setSchemas(schema);
            flyway.migrate();
        }
    }

}
