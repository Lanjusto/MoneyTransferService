package com.github.lanjusto.moneytransferservice.api.resource.account.list;

import com.github.lanjusto.moneytransferservice.core.account.AccountList;
import org.jetbrains.annotations.NotNull;
import org.restlet.resource.Get;

public interface AccountListResource {
    @Get(value = "json")
    @NotNull
    AccountList retrieve();
}
