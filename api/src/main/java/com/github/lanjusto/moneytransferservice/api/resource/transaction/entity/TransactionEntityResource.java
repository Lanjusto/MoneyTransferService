package com.github.lanjusto.moneytransferservice.api.resource.transaction.entity;

import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface TransactionEntityResource {
    @Nullable
    @Get(value = "json")
    Transaction retrieve();

    @Put(value = "json")
    void store(@NotNull Transaction transaction) throws MoneyTransferServiceException;
}
