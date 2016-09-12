package com.github.lanjusto.moneytransferservice.core.transaction;

import com.github.lanjusto.moneytransferservice.model.Account;
import com.github.lanjusto.moneytransferservice.model.AccountComparator;
import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronization for money transfer between two accounts.
 * <p>
 * Locks both accounts.
 * <p>
 * Locks accounts in the specific order (see {@link AccountComparator}) to prevent dead-locks.
 */
class AccountLocks {
    private static final Map<String, ReentrantLock> LOCK_MAP = Maps.newConcurrentMap();
    private static final AccountComparator ACCOUNT_COMPARATOR = new AccountComparator();

    private final ReentrantLock innerLock;
    private final ReentrantLock outerLock;

    @NotNull
    static AccountLocks createLocks(@NotNull Account account1, @NotNull Account account2) {
        final int comparisonResult = ACCOUNT_COMPARATOR.compare(account1, account2);

        final ReentrantLock innerLock;
        final ReentrantLock outerLock;
        if (comparisonResult < 0) {
            innerLock = getLock(account1);
            outerLock = getLock(account2);
        } else if (comparisonResult > 0) {
            innerLock = getLock(account2);
            outerLock = getLock(account1);
        } else {
            Assertions.fail("Accounts are supposed to be different");
            throw new RuntimeException();
        }

        return new AccountLocks(innerLock, outerLock);
    }

    @NotNull
    private static ReentrantLock getLock(@NotNull Account account) {
        return LOCK_MAP.computeIfAbsent(account.getId(), id -> new ReentrantLock());
    }

    private AccountLocks(@NotNull ReentrantLock innerLock, @NotNull ReentrantLock outerLock) {
        this.innerLock = innerLock;
        this.outerLock = outerLock;
    }

    void lock() {
        outerLock.lock();
        innerLock.lock();
    }

    void release() {
        innerLock.unlock();
        outerLock.unlock();
    }
}
