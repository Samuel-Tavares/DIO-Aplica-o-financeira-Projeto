package br.com.appbank.repository;

import br.com.appbank.exception.AccountWithInvestimentException;
import br.com.appbank.exception.InvestmentNotFoundException;
import br.com.appbank.exception.WalletNotFoundException;
import br.com.appbank.model.AccountWallet;
import br.com.appbank.model.InvestimentWallet;
import br.com.appbank.model.Investment;

import java.util.ArrayList;
import java.util.List;

import static br.com.appbank.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {

    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestimentWallet> wallets = new ArrayList<>();

    public Investment create(final int tax, final long initialFunds){
        this.nextId++;
        Investment investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestimentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()){
            var accountsInUse = wallets.stream().map(InvestimentWallet::getAccount).toList();
            if (accountsInUse.contains(account)) {
                throw new AccountWithInvestimentException("A conta fornecida já possui um investimento");
            }
        }
        Investment investment = findById(id);
        InvestimentWallet wallet = new InvestimentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public List<InvestimentWallet>listWallets(){
        return this.wallets;
    }

    public List<Investment> list(){
        return this.investments;
    }

    public InvestimentWallet withdraw(final String pix, final long funds){
        InvestimentWallet wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "saque de investimento");
        if (wallet.getFunds() == 0){
            wallets.remove(wallet);
        }
        return wallet;
    }

    public InvestimentWallet deposit(final String pix, final long funds){
        InvestimentWallet wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.reduceMoney(funds), wallet.getService(), "Depósito de investimento");
        return wallet;
    }


    public void updateAmount(){
        wallets.forEach(w -> w.getInvestment().tax());
    }


    public Investment findById(final long id) {
        return investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(
                        () -> new InvestmentNotFoundException("Não há investimento com o ID fornecedio!")
                );
    }

    public InvestimentWallet findWalletByAccountPix(final String pix){
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException("Não há carteiras com o pix fornecido!")
                );
    }


}
