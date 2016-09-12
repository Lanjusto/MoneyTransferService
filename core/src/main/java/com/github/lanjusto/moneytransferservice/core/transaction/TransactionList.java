package com.github.lanjusto.moneytransferservice.core.transaction;

import com.github.lanjusto.moneytransferservice.model.Transaction;

import java.util.ArrayList;

/**
 * To avoid typization issues while deserializing from JSON.
 */
public class TransactionList extends ArrayList<Transaction>{

}
