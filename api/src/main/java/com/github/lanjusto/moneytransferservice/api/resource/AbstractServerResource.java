package com.github.lanjusto.moneytransferservice.api.resource;

import com.github.lanjusto.moneytransferservice.api.InjectionPoint;
import com.github.lanjusto.moneytransferservice.api.MoneyTransferService;
import com.github.lanjusto.moneytransferservice.core.account.AccountService;
import com.github.lanjusto.moneytransferservice.core.transaction.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractServerResource extends ServerResource {
    protected TransactionService transactionService;
    protected AccountService accountService;

    protected void doInit() throws ResourceException {
        final InjectionPoint injectionPoint =
                (InjectionPoint) getContext()
                        .getAttributes()
                        .get(MoneyTransferService.INJECTION_POINT);

        accountService = injectionPoint.getAccountService();
        transactionService = injectionPoint.getTransactionService();
    }

    @NotNull
    protected UUID getAsUUID(@NotNull String key) {
        final String value = getAsString(key);

        return UUID.fromString(value);
    }

    @NotNull
    protected String getAsString(@NotNull String key) {
        final String value = (String) getRequestAttributes().get(key);

        assertThat(value).isNotEmpty();

        return value;
    }
}
