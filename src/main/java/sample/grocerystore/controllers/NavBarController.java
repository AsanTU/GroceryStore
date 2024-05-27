package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import sample.grocerystore.App;

import java.io.IOException;

public class NavBarController {
    @FXML
    public void switchToAddPage() throws IOException {
        App.setRoot("inventory-management-panel");
    }

    @FXML
    public void switchToTablePage() throws IOException {
        App.setRoot("product-selection-panel");
    }

    @FXML
    public void switchToPricePage() throws IOException {
        App.setRoot("history-panel");
    }
}
