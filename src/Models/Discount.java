package Models;

public class Discount {
    private int id;
    private String description;
    private Float amount;

    //Constructor Method
    public Discount(String description, Float amount) {
        this.description = description;
        this.amount = amount;
    }
    //Overloaded with ID value in case constructed from existing row
    public Discount(int id, String description, Float amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}