package banking;

import banking.menu.AccountInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

// Класс, который производит действия с базой данных и превращает результаты запроса в более удобный для использования класс (DAO)
// Тут всё просто. По одному методу на каждое действие.
public class CardService {
    private final CardDatabaseUtils cardDatabaseUtils = CardDatabaseUtils.getInstance();

    public Optional<AccountInfo> getAccountInfoByNumber(String cardNumber) {
        ResultSet accountInfoResultSet = null;
        try {
            accountInfoResultSet = cardDatabaseUtils.getAccountInfoByNumber(cardNumber);
        } catch (SQLException e) {
            System.out.println("Failed to get card details for card " + cardNumber);
        }
        return mapResultSetToAccountInfo(accountInfoResultSet);
    }

    public Optional<AccountInfo> getAccountInfoByNumberAndPin(String cardNumber, int pin) {
        ResultSet accountInfoResultSet = null;
        try {
            accountInfoResultSet = cardDatabaseUtils.getAccountInfoByNumberAndPin(cardNumber, pin);
        } catch (SQLException e) {
            System.out.println("Failed to get details for card " + cardNumber + " and pin " + pin);
        }
        return mapResultSetToAccountInfo(accountInfoResultSet);
    }

    public boolean checkCardNumberExists(String cardNumber) {
        return getAccountInfoByNumber(cardNumber).isPresent();
    }

    public void addToBalance(String cardNumber, long amount) {
        AccountInfo accountInfo = this.getAccountInfoByNumber(cardNumber).get();
        accountInfo.balance += amount;
        try {
            cardDatabaseUtils.updateCardBalance(accountInfo.cardNumber, accountInfo.balance);
        } catch (SQLException e) {
            System.out.println("Failed to update card balance for card " + cardNumber);
        }
    }

    public void createAccountInfo(String cardNumber, int pin) {
        try {
            cardDatabaseUtils.createAccount(cardNumber, pin);
        } catch (Exception e) {
            System.out.println("Failed to create account");
        }
    }

    // В идеале - это тоже должен выполнять отдельный класс - так называемы Mapper класс
    private Optional<AccountInfo> mapResultSetToAccountInfo(ResultSet resultSet) {
        AccountInfo accountInfo = null;
        if (resultSet != null) {
            accountInfo = new AccountInfo();
            try {
                accountInfo.id = resultSet.getInt("id");
                accountInfo.cardNumber = resultSet.getString("number");
                accountInfo.pin = resultSet.getInt("pin");
                accountInfo.balance = resultSet.getLong("balance");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(accountInfo);
    }
}
