package br.com.appbank.repository;

import br.com.appbank.exception.NoFundsEnoughException;
import br.com.appbank.model.AccountWallet;
import br.com.appbank.model.Money;
import br.com.appbank.model.MoneyAudit;
import br.com.appbank.model.Wallet;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.appbank.model.BankService.ACCOUNT;

public final class  CommonsRepository {

    public CommonsRepository() {
    }

    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException("Saldo insuficiente");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description){
        MoneyAudit history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }


}