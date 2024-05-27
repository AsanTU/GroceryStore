package sample.grocerystore.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.grocerystore.App;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.SelectedProducts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckPanelController implements Initializable {

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Text productDetailText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    @FXML
    void navigateToBack() throws IOException {
        App.setRoot("product-selection-panel");
    }

    public void addProductToCheckPanel(Product product) {
        if (!SelectedProducts.getSelectedProducts().contains(product)) {
            SelectedProducts.addSelectedProduct(product);
        }
    }
}
