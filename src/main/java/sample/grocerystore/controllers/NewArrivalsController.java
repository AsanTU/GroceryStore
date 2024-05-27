package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.grocerystore.App;
import javafx.scene.control.Alert;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.ProductRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewArrivalsController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField pricePerPeaceField;

    @FXML
    private Button addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBtn.setOnAction(event -> handleAddProduct());
    }

    private void handleAddProduct() {
        if (areFieldsEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields before adding a product.");
            return;
        }

        try {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double pricePerPiece = Double.parseDouble(pricePerPeaceField.getText());
            double totalPrice = quantity * pricePerPiece;

            Product newProduct = new Product(0, name, quantity, pricePerPiece, totalPrice);
            ProductRepository.getInstance().addProduct(newProduct);

            nameField.clear();
            quantityField.clear();
            pricePerPeaceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers for quantity and price per piece.");
        }
    }

    private boolean areFieldsEmpty() {
        return nameField.getText().isEmpty() || quantityField.getText().isEmpty() || pricePerPeaceField.getText().isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void navigateToInventoryPanel() throws IOException {
        App.setRoot("inventory-management-panel");
    }
}
