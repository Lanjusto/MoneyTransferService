package com.github.lanjusto.moneytransferservice.model.exceptions;

import com.github.lanjusto.moneytransferservice.model.Account;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class SameAccountException extends MoneyTransferServiceException {
    public SameAccountException(@NotNull Account account) {
        super(MessageFormat.format("Can not transfer money from account {0} to itself.", account.getId()));
    }
}
