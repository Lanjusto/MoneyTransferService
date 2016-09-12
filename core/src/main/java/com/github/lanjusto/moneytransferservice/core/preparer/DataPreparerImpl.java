package com.github.lanjusto.moneytransferservice.core.preparer;

import com.github.lanjusto.moneytransferservice.core.account.AccountService;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Random;

@Default
@Singleton
class DataPreparerImpl implements DataPreparer {
    private static final int STRING_ID_LENGTH = 18;
    private static final int ACCOUNTS_CNT = 100;
    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("100.00");

    private final AccountService accountService;
    private final Random random = new Random();

    @Inject
    private DataPreparerImpl(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void prepareData() {
        for (int i = 0; i < ACCOUNTS_CNT; i++) {
            accountService.createNewAccount(getRandomId(), DEFAULT_BALANCE);
        }
    }

    /**
     * @return random string having length STRING_ID_LENGTH.
     */
    @NotNull
    private String getRandomId() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < STRING_ID_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
