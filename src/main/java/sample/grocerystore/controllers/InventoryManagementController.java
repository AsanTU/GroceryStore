package sample.grocerystore.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.grocerystore.models.Product;
import sample.grocerystore.models.ProductRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {

    @FXML
    private TableColumn<Product, Integer> idProduct;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<Product, Double> totalPriceProduct;

    @FXML
    private TableColumn<Product, Integer> quantityProduct;

    @FXML
    private TableColumn<Product, Double> pricePerPeaceProduct;

    @FXML
    private TableView<Product> table;

    @FXML
    private TextField textInput;

    private ObservableList<Product> products;

    private ProductRepository productRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = ProductRepository.getInstance();
        products = FXCollections.observableArrayList(productRepository.getProducts());
        setupTable();
        setupSearchFilter();
        setupRowDoubleClickHandler();
    }

    private void setupTable() {
        idProduct.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityProduct.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        pricePerPeaceProduct.setCellValueFactory(cellData -> cellData.getValue().pricePerPieceProperty().asObject());
        totalPriceProduct.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        pricePerPeaceProduct.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", item));
                }
            }
        });

        totalPriceProduct.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", item));
                }
            }
        });

        table.setItems(products);
    }

    private void setupSearchFilter() {
        FilteredList<Product> filteredList = new FilteredList<>(productRepository.getProducts(), p -> true);
        textInput.textProperty().addListener(((observableValue, oldValue, newValue) -> filteredList.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) return true;
            String lowerCaseFilter = newValue.toLowerCase();
            return product.getName().toLowerCase().contains(lowerCaseFilter);
        })));

        SortedList<Product> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    @FXML
    void showTextInput() {
        textInput.setVisible(!textInput.isVisible());
    }

    @FXML
    void navigateToAddProductView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/grocerystore/fxml/new-arrivals-panel.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            Scene scene = stage.getScene();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/sample/grocerystore/styles.css")).toExternalForm());

            stage.setTitle("New Arrivals");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void refreshTable() {
        table.refresh();
    }

    private void setupRowDoubleClickHandler() {
        table.setRowFactory(tv -> {
            var row = new TableRow<Product>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
                    Product selectedProduct = row.getItem();
                    deleteProduct(selectedProduct);
                }
            });
            return row;
        });
    }

    private void deleteProduct(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> action = alert.showAndWait();
        if (action.isPresent() && action.get() == ButtonType.OK) {
            productRepository.removeProduct(product);
            products.remove(product);
            refreshTable();
        }
    }
}
