package sample.grocerystore.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import sample.grocerystore.App;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.ProductRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {

    private NavBarController navBarController;

    @FXML
    private AnchorPane navBar;

    @FXML
    private BorderPane tablePane;

    @FXML
    private FontAwesomeIconView bars;

    @FXML
    private TableColumn<Product, Integer> idProduct;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<Product, Double> totalPriceProduct;

    @FXML
    private TableColumn<Product, Integer> quantityProduct;

    @FXML
    private FontAwesomeIconView searchFont;

    @FXML
    private TableColumn<Product, Double> pricePerPeaceProduct;

    @FXML
    private TableView<Product> table;

    @FXML
    private TextField textInput;

    @FXML
    private Text titleTable;

    private ObservableList<Product> products;

    private ProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = ProductRepository.getInstance();
        setupTable();
        setupSearchFilter();
    }

    private void setupTable() {
        idProduct.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityProduct.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        pricePerPeaceProduct.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPricePerPeace()).asObject());
        totalPriceProduct.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());

        table.setItems(productRepository.getProducts());
    }

    private void setupSearchFilter() {
        FilteredList<Product> filteredList = new FilteredList<>(productRepository.getProducts(), p -> true);
        textInput.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return product.getName().toLowerCase().contains(lowerCaseFilter);
            });
        }));

        SortedList<Product> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    public void setNavBarController(NavBarController navBarController) {
        this.navBarController = navBarController;
    }

    @FXML
    void showTextInput(MouseEvent event) {
        textInput.setVisible(!textInput.isVisible());
    }

    @FXML
    void navigateToAddProductView(MouseEvent event) throws IOException {
        App.setRoot("new-arrivals-panel");
    }
}
