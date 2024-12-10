import java.sql.*;
import java.util.Scanner;

public class TransactionManager {

    public static void startTransaction(Scanner scanner) {
        System.out.println(" "); //new line for readability

        //gets userID and time of transaction
        int userId = IDValidator.promptForUserId(scanner);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try (Connection connection = DBConnector.getConnection()) {
            String sql = "INSERT INTO db_transaction (UserID, Date) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                //Creates transaction
                stmt.setInt(1, userId);
                stmt.setTimestamp(2, timestamp);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int transactionId = rs.getInt(1);  // Get the generated transaction ID
                    System.out.println("Transaction started successfully! Transaction ID: " + transactionId + "\n");

                    transactionLoop(scanner, connection, transactionId);
                }

            } catch (SQLException e) {
                System.err.println("Error starting transaction: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private static void transactionLoop(Scanner scanner, Connection connection, int transactionId) {
        //adds first line item
        addLineItem(scanner, connection, transactionId);

        //loops until finished
        boolean finished = false;
        while (!finished) {
            //prints current transaction details
            DatabasePrinter.printTransactionDetails(transactionId);

            // Ask user if they want to add, remove, or finish the transaction
            System.out.println("\nTransaction Options:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Checkout");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addLineItem(scanner, connection, transactionId);
                    break;
                case 2:
                    // Prompt for line item removal
                    removeLineItem(scanner, connection, transactionId);
                    break;
                case 3:
                    finished = true;  // Finish the transaction
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        System.out.println("Transaction completed successfully.");
    }

    private static void addLineItem(Scanner scanner, Connection connection, int transactionId) {
        DatabasePrinter.printProductList();

        int productId = IDValidator.promptForProductId(scanner);

        // Prompts user for discount, defaults to null if no discount is desired
        int discountId = 0;
        System.out.print("Would you like to apply a discount? y/n: ");
        if (IDValidator.getYesOrNo(scanner)) {
            discountId = IDValidator.promptForDiscountId(scanner);
        }

        // Insert the line item into db_lineitem
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
        } catch (SQLException e) {
            System.err.println("Error adding line item: " + e.getMessage());
        }
    }

    private static void removeLineItem(Scanner scanner, Connection connection, int transactionId) {
        System.out.print("Enter the ID of the line item you want to remove: ");
        int lineItemId = scanner.nextInt();
        scanner.nextLine();  // Consume newline after reading integer

        // Validate that the line item belongs to the given transaction
        String validateSql = "SELECT 1 FROM db_lineitem WHERE ID = ? AND TransactionID = ?";
        try (PreparedStatement validateStmt = connection.prepareStatement(validateSql)) {
            validateStmt.setInt(1, lineItemId);
            validateStmt.setInt(2, transactionId);
            ResultSet rs = validateStmt.executeQuery();
            if (rs.next()) {
                // If valid, remove the line item
                String deleteSql = "DELETE FROM db_lineitem WHERE ID = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, lineItemId);
                    deleteStmt.executeUpdate();
                    System.out.println("Line item removed from transaction.");
                }
            } else {
                System.out.println("Line item ID is invalid or doesn't belong to the current transaction.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing line item: " + e.getMessage());
        }
    }
}