package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

// Обращения в базу данных следует хранить в отдельном классе. Удобно переиспользовать их в других местах и как правило - часто.
public class CardDatabaseUtils {
    private static CardDatabaseUtils INSTANCE;
    private static final String DBC_URL = "jdbc:sqlite:card.s3db";

    private SQLiteDataSource dataSource;
    private Connection connection;

    // Забей хуй и не разбирай эти статические методы. Потому что это отдельный топик об Design Patterns.
    // Это придётся учить, когда познаешь язык.
    public static synchronized CardDatabaseUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CardDatabaseUtils();
            INSTANCE.dataSource = new SQLiteDataSource();
            INSTANCE.dataSource.setUrl(DBC_URL);
            try {
                INSTANCE.connection = INSTANCE.dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return INSTANCE;
    }

    private CardDatabaseUtils() {
    }

    public void createCardTableIfNotExists() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS card" + "(id INT PRIMARY KEY, " + "number TEXT NOT NULL, " + "pin TEXT NOT NULL, " + "balance INTEGER DEFAULT 0)");
    }

    public void createAccount(String cardNumber, int pin) throws SQLException {
        connection.createStatement().executeUpdate("INSERT INTO card(number , pin, balance) VALUES " + "('" + cardNumber + "', '" + pin + "', '" + 0 + "')");
    }

    public ResultSet getAccountInfoByNumber(String number) throws SQLException {
        ResultSet result = connection.createStatement().executeQuery("SELECT * FROM card WHERE number LIKE " + number + ";");
        boolean isPresent = result.next();
        return isPresent ? result : null;
    }

    public ResultSet getAccountInfoByNumberAndPin(String number, int pin) throws SQLException {
        ResultSet result = connection.createStatement().executeQuery("SELECT * FROM card WHERE number LIKE " + number + " AND pin LIKE " + pin + ";");
        boolean isPresent = result.next();
        return isPresent ? result : null;
    }

    // Используй этот метод, что бы обновлять баланс при транзакции
    // Удаление аккаунта напиши сам
    public void updateCardBalance(String cardNumber, long newBalance) throws SQLException {
        connection.createStatement().executeUpdate("UPDATE card SET balance = " + newBalance + " WHERE number = " + cardNumber);
    }
}
