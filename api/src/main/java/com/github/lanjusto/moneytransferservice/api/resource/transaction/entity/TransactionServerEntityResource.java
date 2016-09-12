package com.github.lanjusto.moneytransferservice.api.resource.transaction.entity;

import com.github.lanjusto.moneytransferservice.api.resource.AbstractServerResource;
import com.github.lanjusto.moneytransferservice.api.url.APIUrlProvider;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restlet.data.Status;

import java.util.UUID;

public class TransactionServerEntityResource extends AbstractServerResource implements TransactionEntityResource {
    @Nullable
    @Override
    public Transaction retrieve() {
        final UUID uuid = getAsUUID(APIUrlProvider.getTransactionIdParameter());
        final Transaction transaction = transactionService.get(uuid);
        if (transaction == null) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        } else {
            return transaction;
        }
    }

    @Override
    public void store(@NotNull Transaction transaction) throws MoneyTransferServiceException {
        transactionService.store(transaction);
        setStatus(Status.SUCCESS_CREATED);
    }
}
