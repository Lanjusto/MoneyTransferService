package com.github.lanjusto.moneytransferservice.api.resource.account.entity;

import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.exceptions.NoAccountFoundException;
import org.restlet.resource.Get;

public interface AccountEntityResource {
    @Get("json")
    Account retrieve() throws NoAccountFoundException;
}
