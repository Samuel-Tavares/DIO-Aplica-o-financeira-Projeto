package br.com.appbank;

import br.com.appbank.exception.AccountNotFoundException;
import br.com.appbank.exception.NoFundsEnoughException;
import br.com.appbank.exception.WalletNotFoundException;
import br.com.appbank.model.AccountWallet;
import br.com.appbank.model.InvestimentWallet;
import br.com.appbank.model.Investment;
import br.com.appbank.model.Wallet;
import br.com.appbank.repository.AccountRepository;
import br.com.appbank.repository.InvestmentRepository;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Olá seja bem vindo!");

        while(true) {
            System.out.println("Selecione a operação desejada");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar Investimentos ");
            System.out.println("11 - Listar Carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Historico de conta");
            System.out.println("14 - Sair");
            int option = scanner.nextInt();
            switch (option){
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestiment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transferToAccount();
                case 7 -> incInvestment();
                case 8 -> rescueInvestment();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 13 -> checkHistory();
                case 14 -> System.exit(0);
                default ->
                    System.out.println("Opção inválida");
            }
        }
    }

    private static void createAccount() {
        System.out.println("Informe as chaves pix (separadas por ';'");
        var pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Informe o valor inicial de deposito");
        long amount = scanner.nextLong();
        Wallet wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada!");
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa de investimento");
        int tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de deposito");
        long initialFunds = scanner.nextLong();
        Investment investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado");
    }

    private static void withdraw() {
        System.out.println("Informe a chave pix para saque:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será sacado:");
        long amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para deposito:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será depositado:");
        long amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem:");
        String source = scanner.next();
        System.out.println("Informe a chave pix destino:");
        String target = scanner.next();
        System.out.println("Informe o valor a ser depositado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void createWalletInvestiment() {
        System.out.println("Informe a chave pix da conta: ");
        String pix = scanner.next();
        AccountWallet account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento");
        int investimentId = scanner.nextInt();
        InvestimentWallet investimentWallet = investmentRepository.initInvestment(account,investimentId);
        System.out.println("Conta de investimento criada");
    }

    private static void incInvestment() {
        System.out.println("Informe a chave pix da conta para investimento:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será investido:");
        long amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment() {
        System.out.println("Informe a chave pix para resgate do invesitimento:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será resgatado:");
        long amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void checkHistory() {
        System.out.println("Informe a chave pix da conta para verificar extrato: ");
        String pix = scanner.next();
        AccountWallet wallet;
        try {
            var sortedHistory =  accountRepository.getHistory(pix);
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(DateTimeFormatter.ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionId());
                System.out.println(v.getFirst().description());
                System.out.println("R%" + (v.size() / 100) + "," + (v.size() % 100));
            });
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
