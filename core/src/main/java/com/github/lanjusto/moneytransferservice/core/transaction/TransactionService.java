package com.github.lanjusto.moneytransferservice.core.transaction;

import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface TransactionService {
    void store(@NotNull Transaction transaction) throws MoneyTransferServiceException;

    @NotNull
    TransactionList getAllTransactions();

    @Nullable
    Transaction get(@NotNull UUID id);
}
