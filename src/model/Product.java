package model;

public class Product {
    private int id;
    private int brandId;
    private String productName;
    private String description; //Can be null
    private Float price;
    private boolean active;

    //Constructor Method
    public Product(int id, int brandId, String productName, String description, Float price, boolean active) {
        this.id = id;
        this.brandId = brandId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.active = active;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
