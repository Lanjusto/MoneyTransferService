package com.github.lanjusto.moneytransferservice.api.url;

import org.jetbrains.annotations.NotNull;

public class APIUrlProvider {
    private static final String ROOT = "http://localhost";
    private static final String VERSION = "v1";

    private static final String ACCOUNT_RESOURCE = "accounts";
    private static final String TRANSACTION_RESOURCE = "transactions";

    private static final String TRANSACTION_ID = "transactionId";
    private static final String ACCOUNT_ID = "accountId";

    private static final APIUrl ACCOUNT_ENTITY_URL = new APIUrl(ROOT, VERSION, ACCOUNT_RESOURCE, ACCOUNT_ID);
    private static final APIUrl ACCOUNT_LIST_URL = new APIUrl(ROOT, VERSION, ACCOUNT_RESOURCE, null);
    private static final APIUrl TRANSACTION_ENTITY_URL = new APIUrl(ROOT, VERSION, TRANSACTION_RESOURCE, TRANSACTION_ID);
    private static final APIUrl TRANSACTION_LIST_URL = new APIUrl(ROOT, VERSION, TRANSACTION_RESOURCE, null);

    private APIUrlProvider() {
        // not to be instantiated
    }

    @NotNull
    public static String getTransactionIdParameter() {
        return TRANSACTION_ID;
    }

    @NotNull
    public static String getAccountIdParameter() {
        return ACCOUNT_ID;
    }

    @NotNull
    public static APIUrl getTransactionListUrl() {
        return TRANSACTION_LIST_URL;
    }

    @NotNull
    public static APIUrl getTransactionEntityUrl() {
        return TRANSACTION_ENTITY_URL;
    }

    @NotNull
    public static APIUrl getAccountListUrl() {
        return ACCOUNT_LIST_URL;
    }

    @NotNull
    public static APIUrl getAccountEntityUrl() {
        return ACCOUNT_ENTITY_URL;
    }
}
