package com.monederobingo.database.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acceptance_test/transaction")
public class TransactionController
{
    private final TransactionService transactionService;
    private final ThreadContextService threadContextService;

    @Autowired
    public TransactionController(TransactionService transactionService,
            ThreadContextService threadContextService)
    {
        this.transactionService = transactionService;
        this.threadContextService = threadContextService;
    }

    @RequestMapping(value = "/begin", method = GET)
    public ResponseEntity<ServiceResult<Object>> begin() throws Exception
    {
        if (threadContextService.getEnvironment() instanceof FunctionalTestEnvironment)
        {
            transactionService.beginTransaction();
        }
        return new ResponseEntity<>(new ServiceResult<>(true, ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/rollback", method = GET)
    public ResponseEntity<ServiceResult<Object>> rollback() throws Exception
    {
        if (threadContextService.getEnvironment() instanceof FunctionalTestEnvironment)
        {
            transactionService.rollbackTransaction();
        }
        return new ResponseEntity<>(new ServiceResult<>(true, ""), HttpStatus.OK);
    }
}
