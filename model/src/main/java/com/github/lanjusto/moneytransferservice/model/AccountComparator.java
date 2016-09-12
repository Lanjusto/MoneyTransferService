package com.github.lanjusto.moneytransferservice.model;

import java.util.Comparator;

public class AccountComparator implements Comparator<Account> {
    @Override
    public int compare(Account o1, Account o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
