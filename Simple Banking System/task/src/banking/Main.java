package banking;

import banking.menu.StartMenu;

import java.sql.SQLException;

public class Main {
    private static final CardDatabaseUtils cardDataBaseUtils = CardDatabaseUtils.getInstance();

    public static void main(String[] args) throws SQLException {
        cardDataBaseUtils.createCardTableIfNotExists();
        new StartMenu().render();
    }
}



