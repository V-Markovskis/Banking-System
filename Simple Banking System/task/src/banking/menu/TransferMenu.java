package banking.menu;

import java.sql.SQLException;

// Эту еболу перепишешь сам таким же образом как и сделанны другие меню.

/**
 * TODO: В классе CardService создай метод transferMoney(String cardNumberFrom, String cardNumberTo, long balance);
 * По идее в классе CardDatabaseUtils для трансфера ничего не надо добавлять. Можно сделать всё используя существующие методы.
 **/

public class TransferMenu {
    private AccountInfo accountInfo;

    public TransferMenu(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public boolean render() throws SQLException {
//
//        System.out.println("Enter card number:\n");
//        String toCardNumber = sc.next();
//        if (toCardNumber.equals(accountInfo.cardNumber)) {
//            System.out.println("You can't transfer money to the same account!");
//        }
//
//        int[] creditCardInt = new int[toCardNumber.length()];
//
//        for (int i = 0; i < toCardNumber.length(); i++) {
//            creditCardInt[i] = Integer.parseInt(toCardNumber.substring(i, i + 1));
//        }
//
//        for (int i = creditCardInt.length - 2; i >= 0; i = i - 2) {
//            int tempValue = creditCardInt[i];
//            tempValue = tempValue * 2;
//            if (tempValue > 9) {
//                tempValue = tempValue % 10 + 1;
//            }
//            creditCardInt[i] = tempValue;
//        }
//
//        int total = 0;
//        for (int i = 0; i < creditCardInt.length; i++) {
//            total += creditCardInt[i];
//        }
//
//        if (total % 10 != 0) {
//            System.out.println("Probably you made a mistake in the card number. Please try again!");
//        } else {
//            ifExists(toCardNumber);
//        }
        return true;
    }

    private void ifExists(String toCardNumber) throws SQLException {
//        Statement statement = connection.createStatement();
//        resultSet = statement.executeQuery("SELECT * FROM card WHERE number = " + toCardNumber);
//        if (resultSet.next()) {
//            doTransferAfterValidation(toCardNumber);
//        } else {
//            System.out.println("Such a card does not exist.");
//        }
    }

    private static void doTransferAfterValidation(String toCardNumber) throws SQLException {
//        System.out.println("Enter how much money you want to transfer:\n");
//        money = sc.nextInt();
//
//        if (money > balanceMap.get(cardNumberInput)) {
//            System.out.println("Not enough money!");
//        } else {
//            moneyRemaining = balanceMap.get(cardNumberInput) - money;
//            System.out.println("Success!");
//            balanceMap.put(cardNumberInput, moneyRemaining);
//            newBalance = moneyRemaining;
//
//            statement.executeUpdate("UPDATE card SET balance = " + moneyRemaining + " WHERE number = " + cardNumberInput);
//        }
//
//        statement.executeUpdate("UPDATE card SET balance = " + money + " WHERE number = " + toCardNumber);
    }

}
