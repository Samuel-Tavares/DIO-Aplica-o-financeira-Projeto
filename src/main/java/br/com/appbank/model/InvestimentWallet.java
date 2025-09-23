package br.com.appbank.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.appbank.model.BankService.INVESTMENT;


public class InvestimentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestimentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "Investimento");
    }

    public void updateAmount(final long percent) {
        long amount = getFunds() * percent / 100;
        MoneyAudit history = new MoneyAudit(
                UUID.randomUUID(), getService(),
                "Rendimentos", OffsetDateTime.now());
        List<Money> money = Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

    public Investment getInvestment() {
        return investment;
    }

    public AccountWallet getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return super.toString() + "InvestimentWallet{" +
                "investment=" + investment +
                ", account=" + account +
                '}';
    }
}
