package br.com.appbank.repository;

import br.com.appbank.exception.AccountNotFoundException;
import br.com.appbank.exception.PixUseException;
import br.com.appbank.model.AccountWallet;
import br.com.appbank.model.Money;
import br.com.appbank.model.MoneyAudit;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.appbank.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();


    public AccountWallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()){
            List<String> pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (String p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixUseException("O pix fornecido já está em uso");
                }

            }
        }
        AccountWallet newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        AccountWallet target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito via chave pix " + pix);
    }

    public long withdraw(final String pix, final long amount) {
        AccountWallet source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        AccountWallet source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        AccountWallet target = findByPix(targetPix);
        String message = "Transferência via pix de " + sourcePix + " para " + targetPix;
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() ->
                        new AccountNotFoundException("A conta com a chave pix " + pix + "não existe"));
    }

    public List<AccountWallet> list() {
        return accounts;
    }

    public Map<OffsetDateTime, List<MoneyAudit>> getHistory(final String pix){
        AccountWallet wallet = findByPix(pix);
        List<MoneyAudit> audit = wallet.getFinancialTransactions();
        return audit.stream()
                .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(ChronoUnit.SECONDS)));
    }


}
