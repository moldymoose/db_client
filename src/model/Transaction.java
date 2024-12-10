package model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userId;
    private Timestamp date;

    //Constructor Method
    public Transaction(int userId, Timestamp date) {
        this.userId = userId;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}