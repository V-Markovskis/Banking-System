package banking.menu;

import banking.CardService;

import java.util.Scanner;

public class AddIncomeMenu {
    private static final Scanner sc = new Scanner(System.in);
    private static final CardService cardService = new CardService();

    private AccountInfo accountInfo;

    public AddIncomeMenu(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public boolean render() {
        Long income = askToAddIncome();
        updateBalance(income);
        return true;
    }

    private Long askToAddIncome() {
        System.out.println("\nEnter income:\n");
        return sc.nextLong();
    }

    private void updateBalance(Long increment) {
        System.out.println("Adding balance");
        cardService.addToBalance(accountInfo.cardNumber, increment);
        accountInfo.balance = cardService.getAccountInfoByNumber(accountInfo.cardNumber).get().balance;
        System.out.println("new balance: " + accountInfo.balance);
    }
}
