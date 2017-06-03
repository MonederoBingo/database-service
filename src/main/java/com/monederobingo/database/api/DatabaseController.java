package com.monederobingo.database.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;
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

    @RequestMapping(method = POST, value = "/select")
    public ResponseEntity<ServiceResult> select(@RequestBody SelectQuery query) throws Exception
    {
        ServiceResult serviceResult = databaseService.select(query);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/selectList")
    public ResponseEntity<ServiceResult> selectList(@RequestBody SelectQuery query) throws Exception
    {
        ServiceResult serviceResult = databaseService.selectList(query);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/insert")
    public ResponseEntity<ServiceResult> insert(@RequestBody InsertQuery query) throws Exception
    {
        ServiceResult serviceResult = databaseService.insert(query);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/update")
    public ResponseEntity<ServiceResult> update(@RequestBody UpdateQuery query) throws Exception
    {
        ServiceResult serviceResult = databaseService.update(query);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }
}
