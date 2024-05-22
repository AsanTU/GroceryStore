package sample.grocerystore.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.grocerystore.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {

    private AddProductController addProductController;

    private InventoryManagementController inventoryManagementController;

    private PriceController priceController;

    @FXML
    private AnchorPane leftSide;

    @FXML
    private FontAwesomeIconView arrowLeft;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductController = new AddProductController();
        inventoryManagementController = new InventoryManagementController();
        priceController = new PriceController();
        inventoryManagementController.setNavBarController(this);
    }

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
        App.setRoot("price-panel");
    }
}
