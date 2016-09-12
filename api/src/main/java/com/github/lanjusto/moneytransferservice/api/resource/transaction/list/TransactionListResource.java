package com.github.lanjusto.moneytransferservice.api.resource.transaction.list;

import com.github.lanjusto.moneytransferservice.core.transaction.TransactionList;
import org.jetbrains.annotations.NotNull;
import org.restlet.resource.Get;

public interface TransactionListResource {
    @NotNull
    @Get("json")
    TransactionList retrieve();
}
