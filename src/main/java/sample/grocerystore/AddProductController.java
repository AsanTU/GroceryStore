package sample.grocerystore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private BorderPane addPane;

    public BorderPane getAddPane(){
        return addPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPane = new BorderPane();
    }

    @FXML
    public void switchToAddPage() throws IOException {
        App.setRoot("add-product-panel");
    }

    @FXML
    public void switchToTablePage() {
    }

    @FXML
    public void switchToPricePage() {
    }
}
