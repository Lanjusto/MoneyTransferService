package com.github.lanjusto.moneytransferservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private final UUID uuid;
    private final Account srcAccount;
    private final Account dstAccount;
    private final BigDecimal sum;

    @JsonCreator
    public Transaction(@JsonProperty("uuid") @NotNull UUID uuid,
                       @JsonProperty("srcAccount") @NotNull Account srcAccount,
                       @JsonProperty("dstAccount") @NotNull Account dstAccount,
                       @JsonProperty("sum") @NotNull BigDecimal sum) {
        this.uuid = uuid;
        this.srcAccount = srcAccount;
        this.dstAccount = dstAccount;
        this.sum = sum;
    }

    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    @NotNull
    public Account getSrcAccount() {
        return srcAccount;
    }

    @NotNull
    public Account getDstAccount() {
        return dstAccount;
    }

    @NotNull
    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Transaction)) {
            return false;
        }

        final Transaction that = (Transaction) o;
        return Objects.equal(getUuid(), that.getUuid()) &&
                Objects.equal(getSrcAccount(), that.getSrcAccount()) &&
                Objects.equal(getDstAccount(), that.getDstAccount()) &&
                Objects.equal(getSum(), that.getSum());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid(),
                getSrcAccount(),
                getDstAccount(),
                getSum());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "uuid=" + uuid +
                ", srcAccount=" + srcAccount +
                ", dstAccount=" + dstAccount +
                ", sum=" + sum +
                '}';
    }
}
