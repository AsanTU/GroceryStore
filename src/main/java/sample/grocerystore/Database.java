package sample.grocerystore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:grocery_store.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                + " product_name TEXT NOT NULL,\n"
                + " quantity INTEGER NOT NULL,\n"
                + " price_per_peace REAL NOT NULL,\n"
                + " total_price REAL NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
