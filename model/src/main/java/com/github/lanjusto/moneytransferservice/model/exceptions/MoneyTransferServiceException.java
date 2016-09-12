package com.github.lanjusto.moneytransferservice.model.exceptions;

public abstract class MoneyTransferServiceException extends Exception {
    protected MoneyTransferServiceException(String message) {
        super(message);
    }
}
