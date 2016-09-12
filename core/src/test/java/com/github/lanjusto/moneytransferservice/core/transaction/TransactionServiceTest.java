package com.github.lanjusto.moneytransferservice.core.transaction;

import com.github.lanjusto.moneytransferservice.core.account.AccountService;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import com.github.lanjusto.moneytransferservice.model.exceptions.MoneyTransferServiceException;
import com.github.lanjusto.moneytransferservice.model.exceptions.NotEnoughMoneyOnAccountException;
import com.github.lanjusto.moneytransferservice.model.exceptions.SameAccountException;
import com.github.lanjusto.moneytransferservice.model.exceptions.SumMustBePositiveException;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WeldJUnitClassRunner.class)
public class TransactionServiceTest {
    @Inject
    private AccountService accountService;

    @Inject
    private TransactionService transactionService;

    @Test
    public void simpleTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("100.00");
        final Account account1Before = accountService.createNewAccount("1", initialBalance);
        final Account account2Before = accountService.createNewAccount("2", initialBalance);

        final BigDecimal sum = new BigDecimal("30.00");
        final Transaction transaction = new Transaction(UUID.randomUUID(), account1Before, account2Before, sum);
        transactionService.store(transaction);

        final Account account1After = accountService.getAccountById(account1Before.getId());
        final Account account2After = accountService.getAccountById(account2Before.getId());

        assertThat(account1After).isNotNull();
        assertThat(account2After).isNotNull();

        assertThat(account1After.getBalance().add(sum)).isEqualTo(initialBalance);
        assertThat(account2After.getBalance().subtract(sum)).isEqualTo(initialBalance);
    }

    @Test
    public void manyTimesTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("100.00");
        final Account account1Before = accountService.createNewAccount("1", initialBalance);
        final Account account2Before = accountService.createNewAccount("2", initialBalance);

        final BigDecimal sum = new BigDecimal("30.00");
        final Transaction transaction = new Transaction(UUID.randomUUID(), account1Before, account2Before, sum);

        for (int i = 0; i < 10_000; i++) {
            transactionService.store(transaction);

            // Result does not depend on number of attempts to store the transaction

            final Account account1After = accountService.getAccountById(account1Before.getId());
            final Account account2After = accountService.getAccountById(account2Before.getId());

            assertThat(account1After).isNotNull();
            assertThat(account2After).isNotNull();

            assertThat(account1After.getBalance().add(sum)).isEqualTo(initialBalance);
            assertThat(account2After.getBalance().subtract(sum)).isEqualTo(initialBalance);
        }
    }

    @Test
    public void concurrentTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("10000.00");
        final Account account1Before = accountService.createNewAccount("1", initialBalance);
        final Account account2Before = accountService.createNewAccount("2", initialBalance);

        final BigDecimal sum = new BigDecimal("1.00");
        final int n = 10_000;
        final ExecutorService executorService = Executors.newFixedThreadPool(n);

        final List<Future> futureList = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            final Future<?> future = executorService.submit(() -> {
                try {
                    // 1.00 from account1 to account2...
                    final Transaction transaction1 = new Transaction(UUID.randomUUID(), account1Before, account2Before, sum);
                    transactionService.store(transaction1);

                    // ... and 1.00 vice-versa
                    final Transaction transaction2 = new Transaction(UUID.randomUUID(), account2Before, account1Before, sum);
                    transactionService.store(transaction2);
                } catch (MoneyTransferServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            futureList.add(future);
        }

        // waiting for execution
        for (Future future : futureList) {
            future.get();
        }

        final Account account1After = accountService.getAccountById(account1Before.getId());
        final Account account2After = accountService.getAccountById(account2Before.getId());

        assertThat(account1After).isNotNull();
        assertThat(account2After).isNotNull();

        // so balances must be the same
        assertThat(account1After.getBalance()).isEqualTo(initialBalance);
        assertThat(account2After.getBalance()).isEqualTo(initialBalance);
    }

    @Test(expected = NotEnoughMoneyOnAccountException.class)
    public void notEnoughMoneyTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("100.00");
        final Account account1Before = accountService.createNewAccount("1", initialBalance);
        final Account account2Before = accountService.createNewAccount("2", initialBalance);

        final BigDecimal sum = new BigDecimal("300.00");
        final Transaction transaction = new Transaction(UUID.randomUUID(), account1Before, account2Before, sum);
        transactionService.store(transaction);
    }

    @Test(expected = SameAccountException.class)
    public void sameAccountsTest() throws Exception {
        final Account account = accountService.createNewAccount("1", new BigDecimal("100.00"));

        final Transaction transaction = new Transaction(UUID.randomUUID(), account, account, new BigDecimal("300.00"));
        transactionService.store(transaction);
    }

    @Test(expected = SumMustBePositiveException.class)
    public void sumCannotBeZeroTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("100.00");
        final Account account1 = accountService.createNewAccount("1", initialBalance);
        final Account account2 = accountService.createNewAccount("2", initialBalance);

        final Transaction transaction = new Transaction(UUID.randomUUID(), account1, account2, new BigDecimal("0.00"));
        transactionService.store(transaction);
    }

    @Test(expected = SumMustBePositiveException.class)
    public void sumCannotBNegativeTest() throws Exception {
        final BigDecimal initialBalance = new BigDecimal("100.00");
        final Account account1 = accountService.createNewAccount("1", initialBalance);
        final Account account2 = accountService.createNewAccount("2", initialBalance);

        final Transaction transaction = new Transaction(UUID.randomUUID(), account1, account2, new BigDecimal("-10.00"));
        transactionService.store(transaction);
    }
}