package com.github.lanjusto.moneytransferservice.api.resource.transaction.list;

import com.github.lanjusto.moneytransferservice.api.resource.AbstractServerResource;
import com.github.lanjusto.moneytransferservice.core.transaction.TransactionList;
import org.jetbrains.annotations.NotNull;

public class TransactionListServerResource extends AbstractServerResource implements TransactionListResource {
    @NotNull
    @Override
    public TransactionList retrieve() {
        return transactionService.getAllTransactions();
    }
}
