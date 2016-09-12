package com.github.lanjusto.moneytransferservice.api;

import com.github.lanjusto.moneytransferservice.core.account.AccountService;
import com.github.lanjusto.moneytransferservice.core.transaction.TransactionService;

import javax.inject.Inject;

/**
 * This class contains all dependencies which may be useful for classes instantiated by Restlet.
 * <p>
 * It's more convenient to keep instance of only one class in context (see #ServerResource.getContext) and get other
 * dependencies via getters.
 */
public class InjectionPoint {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Inject
    private InjectionPoint(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
