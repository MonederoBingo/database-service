package com.monederobingo.database.database.common.context;

import com.monederobingo.database.database.common.db.queryagent.QueryAgent;
import com.monederobingo.database.database.common.environments.Environment;
import com.monederobingo.database.database.common.i18n.Language;

public class ThreadContext {
    private QueryAgent _clientQueryAgent;
    private Language _language;
    private Environment _environment;

    public QueryAgent getClientQueryAgent() {
        return _clientQueryAgent;
    }

    public void setClientQueryAgent(QueryAgent clientQueryAgent) {
        _clientQueryAgent = clientQueryAgent;
    }

    public Language getLanguage() {
        return _language;
    }

    public void setLanguage(Language language) {
        _language = language;
    }

    public Environment getEnvironment() {
        return _environment;
    }

    public void setEnvironment(Environment environment) {
        _environment = environment;
    }
}
