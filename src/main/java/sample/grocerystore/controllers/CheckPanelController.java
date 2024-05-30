package sample.grocerystore.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sample.grocerystore.App;
import sample.grocerystore.Database;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.ProductRepository;
import sample.grocerystore.models.SelectedProducts;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckPanelController implements Initializable {

    @FXML
    private Button decrementBtn;

    @FXML
    private Button incrementBtn;

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Text productDetailText;

    @FXML
    private Button sellButton;

    private ProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = ProductRepository.getInstance();

        ObservableList<Product> selectedProducts = SelectedProducts.getSelectedProducts();
        productListView.setItems(selectedProducts);

        productListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getName() == null) {
                    setText(null);
                } else {
                    setText(product.getName());
                }
            }
        });

        productListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showProductDetails(newValue);
            }
        });
    }

    private void showProductDetails(Product product) {
        String details = String.format(
                "ID: %d\nName: %s\nQuantity: %d\nPrice Per Piece: %.2f\nTotal Price: %.2f",
                product.getId(), product.getName(), product.getQuantity(), product.getPricePerPiece(), product.getTotalPrice()
        );
        productDetailText.setText(details);

        for (Button button : Arrays.asList(decrementBtn, incrementBtn)) {
            button.setVisible(true);
        }
    }

    @FXML
    void navigateToBack() throws IOException {
        App.setRoot("product-selection-panel");
    }

    public void addProductToCheckPanel(Product product) {
        Product newProduct = new Product(product.getId(), product.getName(), 1, product.getPricePerPiece(), product.getPricePerPiece());
        if (!SelectedProducts.getSelectedProducts().contains(newProduct)) {
            SelectedProducts.addSelectedProduct(newProduct);
        }
    }

    @FXML
    void decrementQuantity() {
        Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int currentQuantity = selectedProduct.getQuantity();
            if (currentQuantity > 1) {
                selectedProduct.setQuantity(currentQuantity - 1);
                selectedProduct.setTotalPrice(selectedProduct.getPricePerPiece() * selectedProduct.getQuantity());
                showProductDetails(selectedProduct);
                SelectedProducts.updateProductInHistory(selectedProduct);
            } else {
                showAlert("Minimum Quantity Reached", "Product quantity cannot be less than 1.");
            }
        }
    }

    @FXML
    void incrementQuantity() {
        Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int currentQuantity = selectedProduct.getQuantity();
            Product originalProduct = productRepository.getProductById(selectedProduct.getId());
            if (originalProduct != null && currentQuantity < originalProduct.getQuantity()) {
                selectedProduct.setQuantity(currentQuantity + 1);
                selectedProduct.setTotalPrice(selectedProduct.getPricePerPiece() * selectedProduct.getQuantity());
                showProductDetails(selectedProduct);
                SelectedProducts.updateProductInHistory(selectedProduct);
            } else {
                showAlert("Maximum Quantity Reached", "Product quantity cannot exceed the available stock.");
            }
        }
    }

    @FXML
    public void sell() {
        double totalPrice = calculateTotalPrice();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Your total price is $" + String.format("%.2f", totalPrice) + ". Do you want to proceed?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        System.out.println("before if");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("after if");
            for (Product p : productListView.getItems()) {
                System.out.println("sell " + p.getName());
                // Уменьшаем количество продукта в базе данных на количество, проданное пользователю
                Product originalProduct = productRepository.getProductById(p.getId());
                if (originalProduct != null) {
                    int remainingQuantity = originalProduct.getQuantity() - p.getQuantity();
                    originalProduct.setQuantity(remainingQuantity);
                    productRepository.updateProductInDatabase(originalProduct);
                }
            }

            for (Product p : productListView.getItems()) {
                System.out.println("history insertion");
                try {
                    Connection conn = Database.connect();
                    String sql = "INSERT INTO history (sale_date, product_name, quantity, price_per_peace, total_price) VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, p.getName());
                    pstmt.setInt(2, p.getQuantity());
                    pstmt.setDouble(3, p.getPricePerPiece());
                    pstmt.setDouble(4, p.getTotalPrice());
                    System.out.println(p.getName());
                    pstmt.executeUpdate();

                    pstmt.close();
                    conn.close();
                    System.out.println("insertion completed");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("insertion error");
                }
            }

            // Очищаем список выбранных продуктов после продажи
            SelectedProducts.clearSelectedProducts();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

            successAlert.setTitle("Sale Complete");
            System.out.println("sell completed");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Sale successfully completed. Total price: $" + String.format("%.2f", totalPrice));

            successAlert.showAndWait();

            // Очищаем список выбранных продуктов в интерфейсе
            clearSelectedProducts();
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : SelectedProducts.getSelectedProducts()) {
            totalPrice += product.getTotalPrice();
        }
        return totalPrice;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearSelectedProducts() {
        SelectedProducts.clearSelectedProducts();
        productListView.getItems().clear();
        productDetailText.setText("");
    }
}
