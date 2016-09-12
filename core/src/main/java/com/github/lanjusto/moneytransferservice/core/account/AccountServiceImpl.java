package com.github.lanjusto.moneytransferservice.core.account;

import com.github.lanjusto.moneytransferservice.model.Account;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Default
@Singleton
class AccountServiceImpl implements AccountService {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    @NotNull
    @Override
    public AccountList getAllAccounts() {
        final AccountList accountList = new AccountList();
        accountList.addAll(accounts.values());
        return accountList;
    }

    @Nullable
    @Override
    public Account getAccountById(@NotNull String id) {
        return accounts.get(id);
    }

    @NotNull
    @Override
    public Account createNewAccount(@NotNull String id, @NotNull BigDecimal balance) {
        final Account account = new Account(id, balance);
        accounts.put(account.getId(), account);
        return account;
    }
}
