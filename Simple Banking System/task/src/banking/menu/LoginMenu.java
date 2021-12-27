package banking.menu;

import banking.CardService;

import java.util.Optional;
import java.util.Scanner;

public class LoginMenu {
    private static final Scanner sc = new Scanner(System.in);
    
    public boolean render() {
        String cardNumberInput;
        int pinInput;
        CardService cardService = new CardService();

        System.out.println("\nEnter your card number:");
        cardNumberInput = sc.next();
        System.out.println("Enter your PIN:");
        pinInput = sc.nextInt();

        Optional<AccountInfo> accountInfo = cardService.getAccountInfoByNumberAndPin(cardNumberInput, pinInput);
        accountInfo.ifPresentOrElse(this::renderAccountMenu, this::logWrongDataMessage);
        return true;
    }

    private void renderAccountMenu(AccountInfo accountInfo) {
        new AccountMenu(accountInfo).render();
    }

    private void logWrongDataMessage() {
        System.out.println("Wrong card number or PIN\n");
    }
}
