package sample.grocerystore;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductTable {

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " quantity INTEGER NOT NULL,\n"
                + " price_per_peace REAL NOT NULL,\n"
                + " total_price REAL NOT NULL\n"
                + ");";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
