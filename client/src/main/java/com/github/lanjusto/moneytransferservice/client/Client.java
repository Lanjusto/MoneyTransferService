package com.github.lanjusto.moneytransferservice.client;

import com.github.lanjusto.moneytransferservice.core.account.AccountList;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import com.github.lanjusto.moneytransferservice.model.exceptions.NoAccountFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public interface Client {
    @Nullable
    Account getAccount(@NotNull String id) throws NoAccountFoundException;

    @NotNull
    AccountList getAllAccounts();

    @Nullable
    Transaction getTransaction(@NotNull UUID uuid);

    @NotNull
    Transaction createTransaction(@NotNull Account srcAccount,
                                  @NotNull Account dstAccount,
                                  @NotNull BigDecimal sum) throws MoneyTransferServiceException;
}
