package com.github.lanjusto.moneytransferservice.core.account;

import com.github.lanjusto.moneytransferservice.model.Account;

import java.util.ArrayList;

/**
 * To avoid typization issues while deserializing from JSON.
 */
public class AccountList extends ArrayList<Account> {

}
