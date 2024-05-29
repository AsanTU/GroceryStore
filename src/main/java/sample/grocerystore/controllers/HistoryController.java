package sample.grocerystore.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.SelectedProducts;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private ListView<String> listForHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listForHistory.getItems().clear();

        ObservableList<Product> selectedProducts = SelectedProducts.getSelectedProducts();

        for (Product product : selectedProducts) {
            String historyEntry = String.format(
                    "Time: %s\nID: %d\nName: %s\nQuantity: %d\nPrice Per Piece: %.2f\nTotal Price: %.2f\n",
                    product.getTimestamp(), product.getId(), product.getName(), product.getQuantity(),
                    product.getPricePerPiece(), product.getTotalPrice()
            );
            listForHistory.getItems().add(historyEntry);
        }
    }
}
