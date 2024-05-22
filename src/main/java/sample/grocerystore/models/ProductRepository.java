package sample.grocerystore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductRepository {

    private static ProductRepository instance;
    private final ObservableList<Product> products;
    private int nextId = 1;

    private ProductRepository() {
        products = FXCollections.observableArrayList();
    }

    public static ProductRepository getInstance() {
        if (instance == null) instance = new ProductRepository();
        return instance;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        Product existingProduct = findProductByName(product.getName());
        if (existingProduct != null) {
            existingProduct.addQuantity(product.getQuantity());
            existingProduct.updatePricePerPeace(product.getPricePerPeace());
        } else {
            product.setId(nextId++);
            products.add(product);
        }
    }

    private Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) return product;
        }
        return null;
    }
}
