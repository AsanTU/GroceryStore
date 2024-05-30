package sample.grocerystore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.grocerystore.Database;

import java.sql.*;

public class ProductRepository {

    private static ProductRepository instance;
    private final ObservableList<Product> products;

    private ProductRepository() {
        products = FXCollections.observableArrayList();
        loadProductsFromDatabase();
    }

    public static ProductRepository getInstance() {
        if (instance == null) instance = new ProductRepository();
        return instance;
    }


    public void sellProduct(Product product) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getQuantity());
            pstmt.setInt(2, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        Product existingProduct = findProductByName(product.getName());
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
            existingProduct.setPricePerPeace(product.getPricePerPiece());
            existingProduct.setTotalPrice(existingProduct.getQuantity() * product.getPricePerPiece());
            updateProductInDatabase(existingProduct);
        } else {
            product.setId(products.size() + 1);
            products.add(product);
            insertProductIntoDatabase(product);
            reloadProductsWithSequentialIds();
            SelectedProducts.addToHistory(product);
        }
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    private Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) return product;
        }
        return null;
    }

    private void loadProductsFromDatabase() {
        products.clear();
        String sql = "SELECT * FROM products ORDER BY id";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_per_peace"),
                        rs.getDouble("total_price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertProductIntoDatabase(Product product) {
        String sql = "INSERT INTO products(name, quantity, price_per_peace, total_price) VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getQuantity());
            pstmt.setDouble(3, product.getPricePerPiece());
            pstmt.setDouble(4, product.getTotalPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductInDatabase(Product product) {
        String sql = "UPDATE products SET quantity = ?, price_per_peace = ?, total_price = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getQuantity());
            pstmt.setDouble(2, product.getPricePerPiece());
            pstmt.setDouble(3, product.getTotalPrice());
            pstmt.setInt(4, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProductFromDatabase(Product product) {
        String sql = "DELETE FROM products WHERE name = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProduct(Product product) {
        deleteProductFromDatabase(product);
        products.remove(product);
        reloadProductsWithSequentialIds();
    }

    private void reloadProductsWithSequentialIds() {
        int idCounter = 1;
        for (Product product : products) {
            product.setId(idCounter++);
            updateProductIdInDatabase(product);
        }
    }

    private void updateProductIdInDatabase(Product product) {
        String sql = "UPDATE products SET id = ? WHERE name = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
