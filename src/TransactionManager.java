import java.sql.*;
import java.util.Scanner;

public class TransactionManager {

    public static void startTransaction(Scanner scanner) {

        int userId = IDValidator.promptForUserId(scanner);
        int productId = IDValidator.promptForProductId(scanner);

        //prompts user for discount, defaults to null if no discount is desired
        int discountId = 0;
        System.out.print("You like to apply a discount? y/n: ");
        if (IDValidator.getYesOrNo(scanner)) {
            discountId = IDValidator.promptForDiscountId(scanner);
        }

        // Time of transaction
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try (Connection connection = DBConnector.getConnection()) {
            String sql = "INSERT INTO db_transaction (UserID, Date) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setTimestamp(2, timestamp);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int transactionId = rs.getInt(1);  // Get the generated transaction ID
                    System.out.println("Transaction started successfully! Transaction ID: " + transactionId);

                    // Now add the line item to the db_lineitem table
                    String lineItemSql = "INSERT INTO db_lineitem (ProductID, TransactionID, DiscountID) VALUES (?, ?, ?)";
                    try (PreparedStatement lineItemStmt = connection.prepareStatement(lineItemSql)) {
                        lineItemStmt.setInt(1, productId);
                        lineItemStmt.setInt(2, transactionId);
                        if (discountId == 0) {
                            lineItemStmt.setNull(3, java.sql.Types.INTEGER); // Set null for the DiscountID
                        } else {
                            lineItemStmt.setInt(3, discountId); // Set the discount ID if not null
                        }
                        lineItemStmt.executeUpdate();
                        System.out.println("Line item added to transaction.");
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error starting transaction: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
