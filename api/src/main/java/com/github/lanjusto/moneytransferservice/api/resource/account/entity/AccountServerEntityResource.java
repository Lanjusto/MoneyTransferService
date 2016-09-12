package com.github.lanjusto.moneytransferservice.api.resource.account.entity;

import com.github.lanjusto.moneytransferservice.api.resource.AbstractServerResource;
import com.github.lanjusto.moneytransferservice.api.url.APIUrlProvider;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.exceptions.NoAccountFoundException;

public class AccountServerEntityResource extends AbstractServerResource implements AccountEntityResource {
    @Override
    public Account retrieve() throws NoAccountFoundException {
        final String id = getAsString(APIUrlProvider.getAccountIdParameter());

        return accountService.getAccountById(id);
    }
}
