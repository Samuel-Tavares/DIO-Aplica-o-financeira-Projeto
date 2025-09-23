package br.com.appbank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Money {

    private final List<MoneyAudit> history = new ArrayList<>();

    public Money(final MoneyAudit history) {
        this.history.add(history);
    }

    public void addHistory(final MoneyAudit history) {
        this.history.add(history);
    }

    public List<MoneyAudit> getHistory() {
        return history;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(history, money.history);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(history);
    }

    @Override
    public String toString() {
        return "Money{" +
                "history=" + history +
                '}';
    }

}
