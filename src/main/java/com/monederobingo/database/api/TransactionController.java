package com.monederobingo.database.api;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.monederobingo.database.api.interfaces.TransactionService;
import com.monederobingo.database.libs.ServiceLogger;
import com.monederobingo.database.model.ServiceResult;
import com.monederobingo.libs.common.context.ThreadContextService;
import com.monederobingo.libs.common.environments.FunctionalTestEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acceptance_test/transaction")
public class TransactionController
{
    private final TransactionService transactionService;
    private final ThreadContextService threadContextService;
    private final ServiceLogger logger;

    @Autowired
    public TransactionController(TransactionService transactionService,
            ThreadContextService threadContextService, ServiceLogger logger)
    {
        this.transactionService = transactionService;
        this.threadContextService = threadContextService;
        this.logger = logger;
    }

    @RequestMapping(value = "/begin", method = GET)
    public ResponseEntity<ServiceResult<Object>> begin()
    {
        if (threadContextService.getEnvironment() instanceof FunctionalTestEnvironment)
        {
            try
            {
                transactionService.beginTransaction();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
                return new ResponseEntity<>(new ServiceResult<>(false, ""), INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new ServiceResult<>(true, ""), OK);
    }

    @RequestMapping(value = "/rollback", method = GET)
    public ResponseEntity<ServiceResult<Object>> rollback()
    {
        if (threadContextService.getEnvironment() instanceof FunctionalTestEnvironment)
        {
            try
            {
                transactionService.rollbackTransaction();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
                return new ResponseEntity<>(new ServiceResult<>(false, ""), INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new ServiceResult<>(true, ""), OK);
    }
}
