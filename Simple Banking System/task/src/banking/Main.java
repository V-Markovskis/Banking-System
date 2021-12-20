package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private static final Map<String, Integer> cardMap = new HashMap<>();
    private static final Map<String, Long> balanceMap = new HashMap<>();
    private static SQLiteDataSource dataSource;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static Connection connection;
    private static String databaseName;
    private static String url;
    private static String cardNumber;
    private static int pin;
    private static long money;
    private static long newBalance;
    private static long moneyRemaining;
    private static String cardNumberInput;
    private static long moneySend;
    private static ResultSet resultSet;


    public static void main(String[] args) throws SQLException {
        databaseName = "card.s3db";
        url = "jdbc:sqlite:" + databaseName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        connection = dataSource.getConnection();
        statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS card" +
                "(id INT PRIMARY KEY, " +
                "number TEXT NOT NULL, " +
                "pin TEXT NOT NULL, " +
                "balance INTEGER DEFAULT 0)");

        String input;
        boolean isRunning = true;

        while(isRunning) {
            System.out.println("1. Create an account\n2. Log into account\n0. Exit");
            input = sc.next();

            switch (input) {
                case "0":
                    isRunning = false;
                    break;
                case "1":
                    createAnAccount();
                    break;
                case "2":
                    isRunning = login();
                    break;
                default:
                    System.out.println("\nIncorrect command!\n");
            }
        }

        System.out.println("\nBye!");
    }

    public static void oneMethodToCloseThemAll(ResultSet resultSet, Statement statement, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                if (!statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void createAnAccount() throws SQLException {
        boolean check;
        int checkSum;

        do {
            check = false;
            cardNumber = "400000" + (random.nextInt(999_999_999 - 100_000_000 + 1) + 100_100_000);
            pin = random.nextInt(9999 - 1000 + 1) + 1000;

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
                total+= creditCardInt[i];
            }

            if (total % 10 != 0) {
                checkSum = 10 - total % 10;
            } else {
                checkSum = 0;
            }
            cardNumber = cardNumber + checkSum;


            if (!cardMap.containsKey(cardNumber) && !cardMap.containsValue(pin)) {
                cardMap.put(cardNumber, pin);
                statement.executeUpdate("INSERT INTO card(number , pin) VALUES " + "('" + cardNumber + "', '" + pin + "')");
                check = true;
            }
        } while (!check);
        balanceMap.put(cardNumber, 0L);
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:\n" + cardNumber);
        System.out.println("Your card PIN:\n" + pin + "\n");
    }

    private static boolean login() throws SQLException {
        int pinInput;
        System.out.println("\nEnter your card number:");
        cardNumberInput = sc.next();
        System.out.println("Enter your PIN:");
        pinInput = sc.nextInt();

        if (cardMap.containsKey(cardNumberInput) && cardMap.containsValue(pinInput)) {
            System.out.println("\nYou have successfully logged in!");
            return customerMenu(cardNumberInput);
        } else {
            System.out.println("Wrong card number or PIN\n");
            return true;
        }
    }

    private static boolean customerMenu(String cardNumberInput) throws SQLException {
        String input;
        boolean isRunning = true;


        while (isRunning) {
            System.out.println("\n1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
            input = sc.next();

            switch (input) {
                case "0":
                    oneMethodToCloseThemAll(resultSet, statement, preparedStatement, connection);
                    return false;
                case "1":
                    System.out.println("\nBalance: " + balanceMap.get(cardNumberInput));
                    break;
                case "2":
                    addIncome(cardNumberInput);
                    break;
                case "3":
                    doTransfer(cardNumberInput);
                    break;
                case "4":
                    closeAccount();
                    isRunning = false;
                    break;
                case "5":
                    System.out.println("\nYou have successfully logged out!\n");
                    isRunning = false;
                    break;
                default:
                    System.out.println("\nIncorrect command");
                    break;
            }
        }
        return true;
    }
    private static void addIncome(String cardNumberInput) throws SQLException {
        //add income method here
        long increment;
        long mapBalance = balanceMap.get(cardNumberInput);
        System.out.println("\nEnter income:\n");
        increment = sc.nextLong();
        newBalance = mapBalance + increment;
        balanceMap.put(cardNumberInput, newBalance);
        System.out.println("\nIncome was added!");

        String updateIncome = "UPDATE card SET balance = ? WHERE number = ?";
        preparedStatement = connection.prepareStatement(updateIncome);
        preparedStatement.setLong(1,newBalance);
        preparedStatement.setString(2, cardNumberInput);
        preparedStatement.executeUpdate();
    }

    private static void doTransfer(String cardNumberInput) throws SQLException {
        //money transfer
        System.out.println("Enter card number:\n");
        cardNumber = sc.next();
        if (cardNumber.equals(cardNumberInput)) {
            System.out.println("You can't transfer money to the same account!");
        }

        int[] creditCardInt = new int[cardNumber.length()];

        for (int i = 0; i < cardNumber.length(); i++) {
            creditCardInt[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
        }

        for (int i = creditCardInt.length - 2; i >= 0; i = i - 2) {
            int tempValue = creditCardInt[i];
            tempValue = tempValue * 2;
            if (tempValue > 9) {
                tempValue = tempValue % 10 + 1;
            }
            creditCardInt[i] = tempValue;
        }

        int total = 0;
        for (int i = 0; i < creditCardInt.length; i++) {
            total+= creditCardInt[i];
        }

        if (total % 10 != 0) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else {
            ifExists();
        }
    }

    private static void ifExists() throws SQLException {
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM card WHERE number == " + cardNumber);
        if (resultSet.next()) {
            doTransferAfterValidation();
        } else {
            System.out.println("Such a card does not exist.");
        }
    }

    private static void doTransferAfterValidation() throws SQLException {
        System.out.println("Enter how much money you want to transfer:\n");
        money = sc.nextInt();

        if (money > balanceMap.get(cardNumberInput)) {
            System.out.println("Not enough money!");
        } else {
            moneyRemaining = balanceMap.get(cardNumberInput) - money;
            System.out.println("Success!");
            balanceMap.put(cardNumberInput, moneyRemaining);

            statement.executeUpdate("UPDATE card SET balance = " + moneyRemaining + " WHERE number = " + cardNumberInput);
        }


        moneySend = balanceMap.get(cardNumber) + money;
        balanceMap.put(cardNumber, moneySend);
        statement.executeUpdate("UPDATE card SET balance = " + moneySend + " WHERE number = " + cardNumber);
    }

    private static void closeAccount() throws SQLException {
        statement.executeUpdate("DELETE FROM card WHERE number = '" + cardMap.get(cardNumberInput) + "';");
        cardMap.remove(cardNumberInput);
        System.out.println("The account has been closed!");
    }
}