package br.com.appbank.model;

import java.util.List;

import static br.com.appbank.model.BankService.ACCOUNT;

public class AccountWallet extends Wallet{

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor inicial da conta");
    }

    public void addMoney(final long amount, final String description) {
        List<Money> money = generateMoney(amount, description);
        this.money.addAll(money);
    }

    public List<String> getPix() {
        return pix;
    }

    @Override
    public String toString() {
        return super.toString() + "AccountWallet{" +
                "pix=" + pix +
                '}';
    }
}
