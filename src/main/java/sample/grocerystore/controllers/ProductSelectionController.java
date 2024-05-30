package sample.grocerystore.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.grocerystore.App;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.ProductRepository;
import sample.grocerystore.models.SelectedProducts;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductSelectionController implements Initializable {

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> pricePerPieceColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, Double> totalPriceColumn;

    @FXML
    private TextField searchProductTextField;

    private ProductRepository productRepository;

    private CheckPanelController checkPanelController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = ProductRepository.getInstance();
        setupTable();
    }

    public void setCheckPanelController(CheckPanelController checkPanelController) {
        this.checkPanelController = checkPanelController;
    }

    @FXML
    void handleTableRowClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            if (checkPanelController == null) {
                throw new IllegalStateException("CheckPanelController is not set.");
            }

            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                if (!SelectedProducts.getSelectedProducts().stream().anyMatch(p -> p.getId() == selectedProduct.getId())) {
                    Product productCopy = new Product(
                            selectedProduct.getId(),
                            selectedProduct.getName(),
                            1,
                            selectedProduct.getPricePerPiece(),
                            selectedProduct.getPricePerPiece()
                    );

                    SelectedProducts.addSelectedProduct(productCopy);

                }
            }
        }
    }


    private void setupTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        pricePerPieceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", price));
                }
            }
        });

        totalPriceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", price));
                }
            }
        });

        FilteredList<Product> filteredData = new FilteredList<>(productRepository.getProducts(), p -> true);

        searchProductTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return product.getName().toLowerCase().contains(lowerCaseFilter);
        }));

        tableView.setItems(filteredData);
    }

    @FXML
    void navigateToCheckPanel() throws IOException {
        App.setRoot("check-panel");
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Product already selected");
        alert.setHeaderText(null);
        alert.setContentText("You cannot select the same product twice.");
        alert.showAndWait();
    }
}