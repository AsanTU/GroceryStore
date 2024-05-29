package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sample.grocerystore.models.SelectedProducts;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private ListView<String> listForHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listForHistory.setItems(SelectedProducts.getHistory());
    }
}
