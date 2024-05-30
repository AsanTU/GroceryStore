package sample.grocerystore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class SelectedProducts {
    private static final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
    private static final ObservableList<Product> history = FXCollections.observableArrayList();

    public static ObservableList<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public static void addSelectedProduct(Product product) {
        selectedProducts.add(product);
        addToHistory(product);
    }

    public static void addToHistory(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setTimestamp(now);
        history.add(product);
    }

    public static void updateProductInHistory(Product updatedProduct) {
        for (Product product : history) {
            if (product.getId() == updatedProduct.getId()) {
                product.setQuantity(updatedProduct.getQuantity());
                product.setPricePerPeace(updatedProduct.getPricePerPiece());
                product.setTotalPrice(updatedProduct.getTotalPrice());
                product.setTimestamp(updatedProduct.getTimestamp());
                break;
            }
        }
    }

    public static void clearSelectedProducts() {
        selectedProducts.clear();
        history.clear();
    }

}
