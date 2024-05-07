package sample.grocerystore;

public class Product {

    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private String status;

    public Product(Integer id, String name, Integer quantity, Double price, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
