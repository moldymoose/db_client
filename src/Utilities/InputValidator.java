package Utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputValidator {

    // Mapping of table names to user-friendly names for prompts
    private static final Map<String, String> tableNameToPrompt = new HashMap<>();

    static {
        tableNameToPrompt.put("db_user", "User");
        tableNameToPrompt.put("db_product", "Product");
        tableNameToPrompt.put("db_discount", "Discount");
    }

    // Prompt for a valid integer ID with database existence check
    public static int promptForValidId(String tableName, Scanner scanner) {
        // Get the human-readable table name for the prompt
        String entityName = tableNameToPrompt.get(tableName);

        int id = -1;
        while (true) {
            System.out.print("Enter " + entityName + " ID: ");
            // Ensure the input is a valid integer
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                // Validate the ID exists in the database
                if (isIdExists(tableName, id)) {
                    return id;
                } else {
                    System.out.println(entityName + " ID " + id + " does not exist in the database.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine();  // Consume the invalid input
            }
        }
    }

    // Check if the ID exists in the specified table and field
    private static boolean isIdExists(String tableName, int id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE ID = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;  // If count > 0, the ID exists
            }

        } catch (SQLException e) {
            System.err.println("Error validating ID existence: " + e.getMessage());
        }

        return false;
    }

    // Helper method to get a yes/no response
    public static boolean getYesOrNo(Scanner scanner) {
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
