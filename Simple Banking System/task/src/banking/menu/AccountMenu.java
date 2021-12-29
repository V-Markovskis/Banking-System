package banking.menu;

import banking.CardService;

import java.util.Scanner;

public class AccountMenu {
    private static final String MENU_TEXT = "\n1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit";
    private static final String LOG_OUT_TEXT = "\nYou have successfully logged out!\n";
    private static final String INCORRECT_TEXT = "\nIncorrect command";

    private final Scanner sc = new Scanner(System.in);
    private final CardService cardService = new CardService();

    private AccountInfo accountInfo;

    public AccountMenu(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public boolean render() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println(MENU_TEXT);
            String menuInput = sc.next();

            switch (menuInput) {
                case "1":
                    accountInfo.balance = cardService.getAccountInfoByNumber(this.accountInfo.cardNumber).get().balance;
                    System.out.println("\nBalance: " + accountInfo.balance);
                    break;
                case "2":
                    new AddIncomeMenu(accountInfo).render();
                    break;
                case "3":
                    //  do transfer
                    break;
                case "4":
                    // close account
                    /** TODO: сделать следующее:
                     * 1. Добавить новый метод в CardDatabaseUtils, которые будет удалять аккаунт
                     * 2. Использоват его в CardService и добавить сообщение
                     */
                    break;
                case "5":
                    System.out.println(LOG_OUT_TEXT);
                    isRunning = false;
                    break;
                default:
                    System.out.println(INCORRECT_TEXT);
                    break;
            }
        }
        return true;
    }
}
