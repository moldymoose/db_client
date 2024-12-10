import java.sql.*;

public class DatabasePrinter {

    // Method to print all products with their ID and price
    public static void printProductList() {
        String sql = "SELECT ID, ProductName, Price FROM db_product WHERE Active = 1"; // Only active products
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("--------------------------------------------");
            System.out.printf("%-5s %-30s %-10s\n", "ID", "Product Name", "Price");
            System.out.println("--------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                System.out.printf("%-5d %-30s $%-10.2f\n", id, name, price);
            }

            System.out.println("--------------------------------------------");
        } catch (SQLException e) {
            System.err.println("Error fetching product list: " + e.getMessage());
        }
    }

    // Method to print details of a specific transaction including LineItemID
    public static void printTransactionDetails(int transactionId) {
        String query = "SELECT l.ID AS LineItemID, p.ProductName, p.Price, l.DiscountID, d.Description, " +
                "IFNULL(d.Amount, 0) AS DiscountAmount, " +
                "(p.Price - IFNULL(d.Amount, 0)) AS FinalPrice " +
                "FROM db_lineitem l " +
                "JOIN db_product p ON l.ProductID = p.ID " +
                "LEFT JOIN db_discount d ON l.DiscountID = d.ID " +
                "WHERE l.TransactionID = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("---------------------------------------------------------------------");
            System.out.println("Transaction Details (ID: " + transactionId + "):");
            System.out.println("---------------------------------------------------------------------");
            System.out.printf("%-10s %-30s %-10s %-10s %-15s\n", "LineItemID", "Product Name", "Price", "Discount", "Final Price");
            System.out.println("---------------------------------------------------------------------");

            double total = 0.0;
            while (rs.next()) {
                int lineItemId = rs.getInt("LineItemID");
                String productName = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                double discountAmount = rs.getDouble("DiscountAmount");
                double finalPrice = rs.getDouble("FinalPrice");
                total += finalPrice;

                String discountDescription = (rs.getString("Description") != null) ? rs.getString("Description") : "None";
                System.out.printf("%-10d %-30s $%-10.2f $%-10.2f $%-10.2f\n", lineItemId, productName, price, discountAmount, finalPrice);
            }

            System.out.println("---------------------------------------------------------------------");
            System.out.printf("Total Transaction Price: $%-10.2f\n", total);

        } catch (SQLException e) {
            System.err.println("Error fetching transaction details: " + e.getMessage());
        }
    }
}