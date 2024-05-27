package sample.grocerystore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectedProducts {
    private static final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();

    public static ObservableList<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public static void addSelectedProduct(Product product) {
        selectedProducts.add(product);
    }
}

