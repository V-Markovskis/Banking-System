// You can experiment here, it won’t be checked

import java.sql.*;
import org.sqlite.SQLiteDataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Task {
  private static SQLiteDataSource dataSource;
  private static Statement statement;
  private static Connection connection;
  private static String databaseName;
  private static String url;
  public static void main(String[] args) throws SQLException {
    databaseName = "C:/SQLite/store.db";
    url = "jdbc:sqlite:" + databaseName;
    dataSource = new SQLiteDataSource();
    dataSource.setUrl(url);
    connection = dataSource.getConnection();
    statement = connection.createStatement();
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS invoice" +
            "(id INT PRIMARY KEY, " +
            "shipping_address TEXT NOT NULL, " +
            "total_cost INTEGER)");


    String insertInvoiceSQL = "INSERT INTO \"invoice\" " +
            "(id, shipping_address, total_cost) VALUES (?, ?, ?)";

    String selectAddressSQL = "SELECT shipping_address " +
            "FROM \"invoice\" WHERE id = ?";

    try (Connection con = dataSource.getConnection()) {
      dataSource = new SQLiteDataSource();
      // Disable auto-commit mode
      con.setAutoCommit(false);

      try (PreparedStatement insertInvoice = con.prepareStatement(insertInvoiceSQL)) {

        // Create a savepoint
        Savepoint savepoint = con.setSavepoint();

        // Insert an invoice
        int invoiceId = 1;
        insertInvoice.setInt(1, invoiceId);
        insertInvoice.setString(2, "Dearborn, Michigan");
        insertInvoice.setInt(3, 100500);
        insertInvoice.executeUpdate();

        PreparedStatement selectAddress = con.prepareStatement(selectAddressSQL);
        selectAddress.setInt(1, invoiceId);
        ResultSet resultSet = selectAddress.executeQuery();

        if (resultSet.getString(1).equals("Dearborn, Michigan")) {
          con.rollback(savepoint);
        }

        con.commit();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
