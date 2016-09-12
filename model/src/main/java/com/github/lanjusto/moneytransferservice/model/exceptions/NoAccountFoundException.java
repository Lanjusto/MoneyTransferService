package com.github.lanjusto.moneytransferservice.model.exceptions;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class NoAccountFoundException extends MoneyTransferServiceException {
    public NoAccountFoundException(@NotNull String accountId) {
        super(MessageFormat.format("No account found by id {0}", accountId));
    }
}
