import java.sql.*;
import java.util.Scanner;

public class IDValidator {

    //Checks to see if id exists in table.
    public static boolean isValid(String table, int id) {
        //Selects field from table with ID
        String query = "SELECT * FROM " + table + " WHERE ID = " + id;
        try (Connection connection = DBConnector.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            //if statement runs isValid returns true
            return true;
        } catch (SQLException e) {
            System.err.println("Error checking ID: " + e.getMessage());
        }
        //if statement doesn't run isValid returns false
        return false;
    }

    //Loops to get valid input from user
    public static int promptForValidId(Scanner scanner, String table, String prompt) {
        int id = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter " + prompt + " ID: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (isValid(table, id)) {
                    valid = true;
                } else {
                    System.out.println("Invalid " + prompt + " ID. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the " + prompt + " ID.");
                scanner.nextLine();
            }
        }

        return id;
    }

    // Overloaded method to handle the prompt for User ID, Product ID, or Discount ID
    public static int promptForUserId(Scanner scanner) {
        return promptForValidId(scanner, "db_user", "User");
    }

    public static int promptForProductId(Scanner scanner) {
        return promptForValidId(scanner, "db_product", "Product");
    }

    public static int promptForDiscountId(Scanner scanner) {
        return promptForValidId(scanner, "db_discount", "Discount");
    }

    //Returns true if input is "y" or false if input is "n"
    public static boolean getYesOrNo(Scanner scanner) {
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.equals("Y")) {
            return true;
        } else if (input.equals("N")) {
            return false;
        } else {
            System.out.println("Invalid input.");
            return getYesOrNo(scanner);
        }
    }
}
