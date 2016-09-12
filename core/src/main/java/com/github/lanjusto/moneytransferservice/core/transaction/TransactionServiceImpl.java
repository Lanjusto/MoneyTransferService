package com.github.lanjusto.moneytransferservice.core.transaction;

import com.github.lanjusto.moneytransferservice.core.account.AccountService;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Default
class TransactionServiceImpl implements TransactionService {
    private final ConcurrentHashMap<UUID, Transaction> transactions = new ConcurrentHashMap<>();

    private final AccountService accountService;

    @Inject
    private TransactionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void store(@NotNull Transaction transaction) throws MoneyTransferServiceException {
        final Account srcAccount = accountService.getAccountById(transaction.getSrcAccount().getId());
        final Account dstAccount = accountService.getAccountById(transaction.getDstAccount().getId());
        final BigDecimal sum = transaction.getSum();

        if (srcAccount == null) {
            throw new NoAccountFoundException(transaction.getSrcAccount().getId());
        } else if (dstAccount == null) {
            throw new NoAccountFoundException(transaction.getDstAccount().getId());
        } else if (srcAccount.equals(dstAccount)) {
            throw new SameAccountException(srcAccount);
        } else if (sum.signum() <= 0) {
            throw new SumMustBePositiveException();
        }

        final boolean isTransactionFirstTimeAdded = (transactions.putIfAbsent(transaction.getUuid(), transaction) == null);
        if (isTransactionFirstTimeAdded) {
            transferMoney(srcAccount, dstAccount, sum);
        }
    }

    private void transferMoney(@NotNull Account srcAccount, @NotNull Account dstAccount, @NotNull BigDecimal sum) throws NotEnoughMoneyOnAccountException {
        final AccountLocks accountLocks = AccountLocks.createLocks(srcAccount, dstAccount);

        accountLocks.lock();
        try {
            srcAccount.putMoneyOut(sum);
            dstAccount.putMoneyIn(sum);
        } finally {
            accountLocks.release();
        }
    }

    @NotNull
    @Override
    public TransactionList getAllTransactions() {
        final TransactionList transactionList = new TransactionList();
        transactionList.addAll(transactions.values());
        return transactionList;
    }

    @Nullable
    @Override
    public Transaction get(@NotNull UUID id) {
        return transactions.get(id);
    }
}
