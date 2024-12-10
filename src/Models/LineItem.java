package Models;

public class LineItem {
    private int id;
    private int productId;
    private int transactionId;
    private Integer discountId = null; //Interger class allows for null value

    //Constructor method
    public LineItem(int productId, int transactionId, Integer discountId) {
        this.productId = productId;
        this.transactionId = transactionId;
        this.discountId = discountId; //Can be null
    }
    //Overloaded with ID value in case constructed from existing row
    public LineItem(int id, int productId, int transactionId, Integer discountId) {
        this.id = id;
        this.productId = productId;
        this.transactionId = transactionId;
        this.discountId = discountId; //Can be null
    }
    //Overloading for constructing with no discount
    public LineItem(int productId, int transactionId) {
        this.productId = productId;
        this.transactionId = transactionId;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}