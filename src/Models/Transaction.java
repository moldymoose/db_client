package Models;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userId;
    private String userFirstName;
    private String userLastName;
    private Timestamp date;

    //Constructor Method
    public Transaction(int userId, String userFirstName, String userLastName, Timestamp date) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.date = date;
    }
    //Overloaded with ID value in case constructed from existing row
    public Transaction(int id, int userId, String userFirstName, String userLastName, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.date = date;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}