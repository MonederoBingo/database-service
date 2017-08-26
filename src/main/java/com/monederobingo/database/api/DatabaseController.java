package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.greatapp.libs.service.database.requests.InsertQueryRQ;
import xyz.greatapp.libs.service.database.requests.SelectQueryRQ;
import xyz.greatapp.libs.service.database.requests.UpdateQueryRQ;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DatabaseController {
    private final DatabaseService databaseService;
    private final ServiceLogger logger;

    @Autowired
    public DatabaseController(DatabaseService databaseService, ServiceLogger logger) {
        this.databaseService = databaseService;
        this.logger = logger;
    }

    @RequestMapping(method = POST, value = "/select")
    public ResponseEntity<xyz.greatapp.libs.service.ServiceResult> select(@RequestBody SelectQueryRQ query) {
        try {
            if (invalidParams(query)) {
                return new ResponseEntity<>(new xyz.greatapp.libs.service.ServiceResult(false, "query.must.not.be.null"), BAD_REQUEST);
            }
            return new ResponseEntity<>(databaseService.select(query), OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new xyz.greatapp.libs.service.ServiceResult(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    private boolean invalidParams(SelectQueryRQ query) {
        return query == null ||
                query.getTable() == null;
    }

    @RequestMapping(method = POST, value = "/selectList")
    public ResponseEntity<xyz.greatapp.libs.service.ServiceResult> selectList(@RequestBody SelectQueryRQ query) throws Exception {
        try {
            return new ResponseEntity<>(databaseService.selectList(query), OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new xyz.greatapp.libs.service.ServiceResult(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/insert")
    public ResponseEntity<xyz.greatapp.libs.service.ServiceResult> insert(@RequestBody InsertQueryRQ query) throws Exception {
        try {
            return new ResponseEntity<>(databaseService.insert(query), OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new xyz.greatapp.libs.service.ServiceResult(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/update")
    public ResponseEntity<xyz.greatapp.libs.service.ServiceResult> update(@RequestBody UpdateQueryRQ query) throws Exception {
        try {
            return new ResponseEntity<>(databaseService.update(query), OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new xyz.greatapp.libs.service.ServiceResult(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }
}
