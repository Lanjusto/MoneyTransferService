package com.github.lanjusto.moneytransferservice.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.lanjusto.moneytransferservice.model.exceptions.NotEnoughMoneyOnAccountException;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class Account {
    private final String id;
    private BigDecimal balance;

    @JsonCreator
    public Account(@JsonProperty("id") @NotNull String id,
                   @JsonProperty("balance") @NotNull BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public void putMoneyOut(@NotNull BigDecimal sum) throws NotEnoughMoneyOnAccountException {
        assertThat(sum).isPositive();

        final BigDecimal newBalance = this.balance.subtract(sum);
        if (newBalance.signum() < 0) {
            throw new NotEnoughMoneyOnAccountException(this, sum);
        } else {
            this.balance = newBalance;
        }
    }

    public void putMoneyIn(@NotNull BigDecimal sum) {
        assertThat(sum).isPositive();

        this.balance = this.balance.add(sum);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        final Account that = (Account) o;
        return Objects.equal(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
