package Models;

public class Brand {
    private int id;
    private String name;

    //Constructor Method
    public Brand(String name) {
        this.name = name;
    }
    //Overloaded with ID value in case constructed from existing row
    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}