package sample.grocerystore.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Product {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final DoubleProperty pricePerPeace;
    private final DoubleProperty totalPrice;
    private LocalDateTime timestamp;

    private final int initialQuantity;

    public Product(int id, String name, int quantity, double pricePerPeace, double totalPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.pricePerPeace = new SimpleDoubleProperty(pricePerPeace);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.initialQuantity = quantity;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }


    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getPricePerPiece() {
        return pricePerPeace.get();
    }

    public void setPricePerPeace(double pricePerPeace) {
        this.pricePerPeace.set(pricePerPeace);
    }

    public DoubleProperty pricePerPieceProperty() {
        return pricePerPeace;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
