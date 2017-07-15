package com.monederobingo.database.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.monederobingo.database.api.interfaces.DatabaseService;
import com.monederobingo.database.libs.ServiceLogger;
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
    private final ServiceLogger logger;

    @Autowired
    public DatabaseController(DatabaseService databaseService, ServiceLogger logger)
    {
        this.databaseService = databaseService;
        this.logger = logger;
    }

    @RequestMapping(method = POST, value = "/select")
    public ResponseEntity<ServiceResult<String>> select(@RequestBody SelectQuery query)
    {
        try
        {
            if(invalidParams(query))
            {
                return new ResponseEntity<>(new ServiceResult<>(false, "query.must.not.be.null"), BAD_REQUEST);
            }
            return new ResponseEntity<>(databaseService.select(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult<>(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    private boolean invalidParams(SelectQuery query)
    {
        return query == null ||
                query.getQuery() == null;
    }

    @RequestMapping(method = POST, value = "/selectList")
    public ResponseEntity<ServiceResult<String>> selectList(@RequestBody SelectQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.selectList(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult<>(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/insert")
    public ResponseEntity<ServiceResult<Long>> insert(@RequestBody InsertQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.insert(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult<>(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST, value = "/update")
    public ResponseEntity<ServiceResult<Integer>> update(@RequestBody UpdateQuery query) throws Exception
    {
        try
        {
            return new ResponseEntity<>(databaseService.update(query), OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ServiceResult<>(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }
}
