package banking.menu;

import banking.CardService;

import java.util.Optional;
import java.util.Random;

public class CreateAccountMenu {
    private static final Random random = new Random();
    private static final CardService cardService = new CardService();

    public boolean render() {
        String cardNumber = generateCardNumberAndValidate();
        int pin = random.nextInt(9999 - 1000 + 1) + 1000;

        cardService.createAccountInfo(cardNumber, pin);

        System.out.println("\nYour card has been created");
        System.out.println("Your card number:\n" + cardNumber);
        System.out.println("Your card PIN:\n" + pin + "\n");

        return true;
    }

    private String generateCardNumberAndValidate() {
        String cardNumber;
        boolean check;
        do {
            cardNumber = generateCardNumber();
            check = cardService.checkCardNumberExists(cardNumber);
        } while (check);

        return cardNumber;
    }

    private String generateCardNumber() {
        String cardNumber = "400000" + (random.nextInt(999_999_999 - 100_000_000 + 1) + 100_100_000);
        int checkSum;
        int[] creditCardInt = new int[cardNumber.length()];

        for (int i = 0; i < cardNumber.length(); i++) {
            creditCardInt[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
        }

        for (int i = creditCardInt.length - 1; i >= 0; i = i - 2) {
            int tempValue = creditCardInt[i];
            tempValue = tempValue * 2;
            if (tempValue > 9) {
                tempValue = tempValue % 10 + 1;
            }
            creditCardInt[i] = tempValue;
        }

        int total = 0;
        for (int i = 0; i < creditCardInt.length; i++) {
            total += creditCardInt[i];
        }

        if (total % 10 != 0) {
            checkSum = 10 - total % 10;
        } else {
            checkSum = 0;
        }
        return cardNumber + checkSum;
    }
}