package com.github.lanjusto.moneytransferservice.client;

import com.github.lanjusto.moneytransferservice.api.Application;
import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WeldJUnitClassRunner.class)
public class ClientTest {
    @Inject
    private Application application;

    @Inject
    private Client client;

    @Before
    public void setUp() throws Exception {
        application.startService();
    }

    @After
    public void tearDown() throws Exception {
        application.stopService();
    }

    @Test
    public void simpleTest() throws Exception {
        final List<Account> accounts = client.getAllAccounts();

        assertThat(accounts.size()).isGreaterThan(2);

        // choosing 0th account src and 1st as dst:
        final Account srcAccount0 = accounts.get(0);
        final Account dstAccount0 = accounts.get(1);
        final BigDecimal srcInitialBalance = srcAccount0.getBalance();
        final BigDecimal dstInitialBalance = dstAccount0.getBalance();

        final BigDecimal sum = new BigDecimal("30.00");

        assertThat(srcInitialBalance).isGreaterThanOrEqualTo(sum);

        // creating transaction
        final Transaction transaction = client.createTransaction(srcAccount0, dstAccount0, sum);

        assertThat(srcAccount0).isEqualTo(transaction.getSrcAccount());
        assertThat(dstAccount0).isEqualTo(transaction.getDstAccount());
        assertThat(sum).isEqualTo(transaction.getSum());

        final Transaction reloadedTransaction = client.getTransaction(transaction.getUuid());

        assertThat(reloadedTransaction).isEqualTo(transaction);

        final Account srcAccount1 = client.getAccount(srcAccount0.getId());
        final Account dstAccount1 = client.getAccount(dstAccount0.getId());

        assertThat(srcAccount1).isNotNull();
        assertThat(dstAccount1).isNotNull();

        assertThat(srcAccount1.getBalance()).isEqualTo(srcInitialBalance.subtract(sum));
        assertThat(dstAccount1.getBalance()).isEqualTo(dstInitialBalance.add(sum));
    }
}