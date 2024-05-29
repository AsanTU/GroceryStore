package sample.grocerystore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SelectedProducts {
    private static final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
    private static final ObservableList<String> history = FXCollections.observableArrayList();

    public static ObservableList<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public static ObservableList<String> getHistory() {
        return history;
    }

    public static void addSelectedProduct(Product product) {
        selectedProducts.add(product);
        addToHistory(product);
    }

    private static void addToHistory(Product product) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        String historyEntry = String.format(
                "Time: %s\nID: %d\nName: %s\nQuantity: %d\nPrice Per Piece: %.2f\nTotal Price: %.2f\n",
                formattedDateTime, product.getId(), product.getName(), product.getQuantity(),
                product.getPricePerPiece(), product.getTotalPrice()
        );

        history.add(historyEntry);
    }
}

