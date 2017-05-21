package com.monederobingo.database.api;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.model.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController
{

    private final DatabaseService databaseService;

    @Autowired public DatabaseController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @RequestMapping
    public ResponseEntity<ServiceResult> select(@RequestBody String query) throws Exception
    {
        ServiceResult serviceResult = databaseService.select(query);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }
}
