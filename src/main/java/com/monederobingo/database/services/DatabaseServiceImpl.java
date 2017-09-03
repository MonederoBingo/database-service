package com.monederobingo.database.services;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.common.db.adapter.DataBaseAdapter;
import com.monederobingo.database.common.db.adapter.DatabaseAdapterFactory;
import com.monederobingo.libs.common.context.ThreadContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import xyz.greatapp.libs.database.queries.*;
import xyz.greatapp.libs.service.Environment;
import xyz.greatapp.libs.service.ServiceResult;
import xyz.greatapp.libs.service.database.requests.DeleteQueryRQ;
import xyz.greatapp.libs.service.database.requests.InsertQueryRQ;
import xyz.greatapp.libs.service.database.requests.SelectQueryRQ;
import xyz.greatapp.libs.service.database.requests.UpdateQueryRQ;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseServiceImpl implements DatabaseService {
    private final ThreadContextService oldThreadContextService;
    private final DatabaseAdapterFactory oldDatabaseAdapterFactory;
    private final xyz.greatapp.libs.service.context.ThreadContextService threadContextService;
    private final xyz.greatapp.libs.database.adapter.DatabaseAdapterFactory databaseAdapterFactory;

    private final static Map<Environment, String> schemasMap = new HashMap<>();

    static {
        schemasMap.put(Environment.DEV, "monedero");
        schemasMap.put(Environment.PROD, "monedero");
        schemasMap.put(Environment.AUTOMATION_TEST, "monedero_test");
        schemasMap.put(Environment.UAT, "monedero_test");
        schemasMap.put(Environment.INTEGRATION_TEST, "public");
    }

    private String getSchema() {
        return schemasMap.getOrDefault(threadContextService.getEnvironment(), "monedero_test") + ".";
    }

    @Autowired
    public DatabaseServiceImpl(ThreadContextService oldThreadContextService, DatabaseAdapterFactory oldDatabaseAdapterFactory,
                               @Qualifier("newThreadContextService") xyz.greatapp.libs.service.context.ThreadContextService threadContextService,
                               xyz.greatapp.libs.database.adapter.DatabaseAdapterFactory databaseAdapterFactory) {
        this.oldThreadContextService = oldThreadContextService;
        this.oldDatabaseAdapterFactory = oldDatabaseAdapterFactory;
        this.threadContextService = threadContextService;
        this.databaseAdapterFactory = databaseAdapterFactory;
    }

    @Override
    public xyz.greatapp.libs.service.ServiceResult select(SelectQueryRQ query) throws Exception {
        return new Select(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }

    private xyz.greatapp.libs.database.adapter.DataBaseAdapter getDatabaseAdapter() throws Exception {
        return databaseAdapterFactory.getDatabaseAdapter(threadContextService.getEnvironment());
    }

    @Override
    public xyz.greatapp.libs.service.ServiceResult selectList(SelectQueryRQ query) throws Exception {
        getOldDatabaseAdapter().executeUpdate("ALTER ROLE postgres SET search_path = monedero_test;");
        return new SelectList(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }

    private DataBaseAdapter getOldDatabaseAdapter() throws Exception {
        return oldDatabaseAdapterFactory.getDatabaseAdapter(oldThreadContextService.getEnvironment());
    }

    @Override
    public xyz.greatapp.libs.service.ServiceResult insert(InsertQueryRQ query) throws Exception {
        getOldDatabaseAdapter().executeUpdate("ALTER ROLE postgres SET search_path = monedero_test;");
        return new Insert(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }

    public xyz.greatapp.libs.service.ServiceResult update(UpdateQueryRQ query) throws Exception {
        getOldDatabaseAdapter().executeUpdate("ALTER ROLE postgres SET search_path = monedero_test;");
        return new Update(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }

    @Override
    public ServiceResult delete(DeleteQueryRQ query) throws Exception {
        getOldDatabaseAdapter().executeUpdate("ALTER ROLE postgres SET search_path = monedero_test;");
        return new Delete(getDatabaseAdapter(), getSchema(), query)
                .execute();
    }
}
