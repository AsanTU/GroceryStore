package sample.grocerystore;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private AddProductController addProductController;

    private TableController tableController;

    private PriceController priceController;

    @FXML
    private AnchorPane leftSide;

    @FXML
    private FontAwesomeIconView arrowLeft;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductController = new AddProductController();
        tableController = new TableController();
        priceController = new PriceController();
        tableController.setMainController(this);
    }

    @FXML
    public void switchToAddPage() throws IOException {
        App.setRoot("add-product-panel");
    }

    @FXML
    public void switchToTablePage() throws IOException {
        App.setRoot("table-panel");
    }

    @FXML
    public void switchToPricePage() throws IOException {
        App.setRoot("price-panel");
    }
}
