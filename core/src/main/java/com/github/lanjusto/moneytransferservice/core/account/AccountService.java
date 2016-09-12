package com.github.lanjusto.moneytransferservice.core.account;

import com.github.lanjusto.moneytransferservice.model.Account;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public interface AccountService {
    @NotNull
    AccountList getAllAccounts();

    @Nullable
    Account getAccountById(@NotNull String id);

    @NotNull
    Account createNewAccount(@NotNull String id, @NotNull BigDecimal balance);
}
