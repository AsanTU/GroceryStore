package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.grocerystore.App;
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

    private ProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = ProductRepository.getInstance();
        addBtn.setOnAction(event -> handleAddProduct());
    }

    private void handleAddProduct() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double pricePerPeace = Double.parseDouble(pricePerPeaceField.getText());
        double totalPrice = quantity * pricePerPeace;

        Product newProduct = new Product(0, name, quantity, pricePerPeace, totalPrice);
        productRepository.addProduct(newProduct);

        nameField.clear();
        quantityField.clear();
        pricePerPeaceField.clear();
    }

    @FXML
    void navigateToInventoryPanel(MouseEvent event) throws IOException {
        App.setRoot("inventory-management-panel");
    }

    @FXML
    void navigateToBack(MouseEvent event) throws IOException {
        App.setRoot("inventory-management-panel");
    }
}
