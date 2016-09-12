package com.github.lanjusto.moneytransferservice.api.resource.account.list;

import com.github.lanjusto.moneytransferservice.api.resource.AbstractServerResource;
import com.github.lanjusto.moneytransferservice.core.account.AccountList;
import org.jetbrains.annotations.NotNull;

public class AccountListServerResource extends AbstractServerResource implements AccountListResource {
    @NotNull
    @Override
    public AccountList retrieve() {
        return accountService.getAllAccounts();
    }
}
