package com.monederobingo.database.api;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.MonederoLogger;
import com.monederobingo.database.model.InsertQuery;
import com.monederobingo.database.model.SelectQuery;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.database.model.UpdateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController
{
    private final DatabaseService databaseService;
    private final MonederoLogger logger;

    @Autowired
    public DatabaseController(DatabaseService databaseService, MonederoLogger logger)
    {
        this.databaseService = databaseService;
        this.logger = logger;
    }

    @RequestMapping(method = POST, value = "/select")
    public ResponseEntity<ServiceResult> select(@RequestBody SelectQuery query)
    {
        try
        {
            return new ResponseEntity<>(databaseService.select(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult(false, ""), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/selectList")
    public ResponseEntity<ServiceResult> selectList(@RequestBody SelectQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.selectList(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult(false, ""), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/insert")
    public ResponseEntity<ServiceResult> insert(@RequestBody InsertQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.insert(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult(false, ""), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/update")
    public ResponseEntity<ServiceResult> update(@RequestBody UpdateQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.update(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult(false, ""), INTERNAL_SERVER_ERROR);
        }
    }
}
