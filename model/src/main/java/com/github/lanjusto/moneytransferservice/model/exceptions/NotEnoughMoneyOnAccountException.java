package com.github.lanjusto.moneytransferservice.model.exceptions;

import com.github.lanjusto.moneytransferservice.model.Account;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class NotEnoughMoneyOnAccountException extends MoneyTransferServiceException {
    public NotEnoughMoneyOnAccountException(@NotNull Account account, @NotNull BigDecimal sum) {
        super(MessageFormat.format("No enough money on account {0}: balance is {1} while trying to put out {2}.",
                account.getId(),
                account.getBalance(),
                sum));
    }
}
