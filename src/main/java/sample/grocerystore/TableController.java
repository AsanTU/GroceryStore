package sample.grocerystore;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    private MainController mainController;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private BorderPane tablePane;

    @FXML
    private FontAwesomeIconView bars;

    @FXML
    private TableColumn<Product, Integer> idProduct;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<Product, Double> priceProduct;

    @FXML
    private TableColumn<Product, Integer> quantityProduct;

    @FXML
    private FontAwesomeIconView searchFont;

    @FXML
    private TableColumn<Product, String> statusProduct;

    @FXML
    private TableView<Product> table;

    @FXML
    private TextField textInput;

    @FXML
    private Text titleTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valueForTable();
    }

    private void valueForTable() {
        idProduct.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceProduct.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        quantityProduct.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        statusProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        ObservableList<Product> products = FXCollections.observableArrayList(
                new Product(1, "Asan", 2, 100.4, "Sale"),
                new Product(1, "Asan", 2, 100.4, "Sale"),
                new Product(1, "Asan", 2, 100.4, "Sale")
        );
        table.setItems(products);
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
}
