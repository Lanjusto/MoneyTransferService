package com.github.lanjusto.moneytransferservice.model.exceptions;

public class SumMustBePositiveException extends MoneyTransferServiceException {
    public SumMustBePositiveException() {
        super("Transaction sum must be positive.");
    }
}
