package com.monederobingo.database.common.context;

import com.monederobingo.database.common.environments.Environment;
import com.monederobingo.database.common.i18n.Language;

public class ThreadContext {
    private Language _language;
    private Environment _environment;

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
