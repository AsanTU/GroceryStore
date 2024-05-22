package sample.grocerystore.models;

public class Product {

    private Integer id;
    private String name;
    private Integer quantity;
    private Double pricePerPeace;
    private Double totalPrice;

    public Product(Integer id, String name, Integer quantity, Double pricePerPeace, Double totalPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.pricePerPeace = pricePerPeace;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPricePerPeace() {
        return pricePerPeace;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void addQuantity(int quantity){
        this.quantity += quantity;
        recalculateTotalPrice();
    }

    public void updatePricePerPeace(double newPricePerPeace){
        this.pricePerPeace = newPricePerPeace;
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice(){
        this.totalPrice = this.quantity * this.pricePerPeace;
    }
}
