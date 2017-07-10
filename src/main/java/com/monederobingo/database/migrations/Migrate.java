package com.monederobingo.database.migrations;

import org.flywaydb.core.Flyway;

public class Migrate
{
    private final static String[] schemas = new String[] { "monedero", "monedero_test" };

    public static void main(String[] args)
    {
        Flyway flyway = new Flyway();
        flyway.setDataSource(getUrl(), getUsername(), getPassword());
        flyway.setLocations("classpath:db/migration");
        for (String schema : schemas)
        {
            flyway.setSchemas(schema);
            flyway.migrate();
        }
    }

    private static String getUrl()
    {
        String url = System.getenv("JDBC_DATABASE_URL");
        return url != null ? url : "jdbc:postgresql://localhost:5432";
    }

    private static String getUsername()
    {
        String user = System.getenv("JDBC_DATABASE_USERNAME");
        return user != null ? user : "postgres";
    }

    private static String getPassword()
    {
        String password = System.getenv("JDBC_DATABASE_PASSWORD");
        return password != null ? password : "root";
    }
}
