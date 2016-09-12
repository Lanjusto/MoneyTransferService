package com.github.lanjusto.moneytransferservice.client;

import com.github.lanjusto.moneytransferservice.api.resource.account.entity.AccountEntityResource;
import com.github.lanjusto.moneytransferservice.api.resource.account.list.AccountListResource;
import com.github.lanjusto.moneytransferservice.api.resource.transaction.entity.TransactionEntityResource;
import com.github.lanjusto.moneytransferservice.api.url.APIUrlProvider;
import com.github.lanjusto.moneytransferservice.core.account.AccountList;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import com.github.lanjusto.moneytransferservice.model.exceptions.NoAccountFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restlet.resource.ClientResource;

import javax.enterprise.inject.Default;
import java.math.BigDecimal;
import java.util.UUID;

@Default
class ClientImpl implements Client {
    @Nullable
    @Override
    public Account getAccount(@NotNull String id) throws NoAccountFoundException {
        final ClientResource cr = new ClientResource(APIUrlProvider.getAccountEntityUrl().getAbsoluteWithParameter(id));
        final AccountEntityResource accountEntityResource = cr.wrap(AccountEntityResource.class);
        return accountEntityResource.retrieve();
    }

    @NotNull
    @Override
    public AccountList getAllAccounts() {
        final ClientResource cr = new ClientResource(APIUrlProvider.getAccountListUrl().getAbsolute());
        final AccountListResource accountListResource = cr.wrap(AccountListResource.class);
        return accountListResource.retrieve();
    }

    @Nullable
    @Override
    public Transaction getTransaction(@NotNull UUID uuid) {
        final String url = APIUrlProvider.getTransactionEntityUrl().getAbsoluteWithParameter(uuid.toString());
        final ClientResource cr = new ClientResource(url);
        final TransactionEntityResource resource = cr.wrap(TransactionEntityResource.class);
        return resource.retrieve();
    }

    @NotNull
    @Override
    public Transaction createTransaction(@NotNull Account srcAccount,
                                         @NotNull Account dstAccount,
                                         @NotNull BigDecimal sum) throws MoneyTransferServiceException {
        final Transaction transaction = new Transaction(UUID.randomUUID(), srcAccount, dstAccount, sum);

        final String url = APIUrlProvider.getTransactionEntityUrl().getAbsoluteWithParameter(transaction.getUuid().toString());
        final ClientResource cr = new ClientResource(url);
        final TransactionEntityResource resource = cr.wrap(TransactionEntityResource.class);
        resource.store(transaction);

        return transaction;
    }
}
