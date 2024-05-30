package sample.grocerystore.models;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Sale {
    private final ObjectProperty<Timestamp> date;
    private final StringProperty productName;
    private final IntegerProperty quantity;
    private final DoubleProperty pricePerPiece;
    private final DoubleProperty totalPrice;

    public Sale(Timestamp date, String productName, int quantity, double pricePerPiece, double totalPrice) {
        this.date = new SimpleObjectProperty<>(date);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.pricePerPiece = new SimpleDoubleProperty(pricePerPiece);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    public Timestamp getDate() {
        return date.get();
    }

    public ObjectProperty<Timestamp> dateProperty() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date.set(date);
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getPricePerPiece() {
        return pricePerPiece.get();
    }

    public DoubleProperty pricePerPieceProperty() {
        return pricePerPiece;
    }

    public void setPricePerPiece(double pricePerPiece) {
        this.pricePerPiece.set(pricePerPiece);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }
}
