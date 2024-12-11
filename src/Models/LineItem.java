package Models;

public class LineItem {
    private int id;
    private int productId;
    private String product;
    private float productPrice;
    private int transactionId;
    private Integer discountId = null; //Interger class allows for null value
    private float discountAmount = 0.0f;

    //Constructor method
    public LineItem(int productId, String product, float productPrice, int transactionId, Integer discountId, float discountAmount) {
        this.productId = productId;
        this.product = product;
        this.productPrice = productPrice;
        this.transactionId = transactionId;
        this.discountId = discountId; //Can be null
        this.discountAmount = discountAmount;
    }
    //Overloaded with ID value in case constructed from existing row
    public LineItem(int id, int productId, String product, float productPrice, int transactionId, Integer discountId, float discountAmount) {
        this.id = id;
        this.productId = productId;
        this.product = product;
        this.productPrice = productPrice;
        this.transactionId = transactionId;
        this.discountId = discountId; //Can be null
        this.discountAmount = discountAmount;
    }
    //Overloading for constructing with no discount
    public LineItem(int productId, String product, float productPrice, int transactionId) {
        this.productId = productId;
        this.product = product;
        this.productPrice = productPrice;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
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

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }
}