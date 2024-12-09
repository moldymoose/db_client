import java.sql.*;
import java.util.Scanner;

public class UserManager {

    public static void addUser(Scanner scanner) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Phone Number (optional): ");
        String phoneNum = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        System.out.print("Enter Zip Code: ");
        int zip = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Defaulting active status to true
        boolean active = true;

        try (Connection conn = DBConnector.getConnection()) {
            String sql = "INSERT INTO db_user (Firstname, Lastname, PhoneNum, Address, City, State, Zip, Active) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, phoneNum);
                stmt.setString(4, address);
                stmt.setString(5, city);
                stmt.setString(6, state);
                stmt.setInt(7, zip);
                stmt.setBoolean(8, active);
                stmt.executeUpdate();
                System.out.println("User added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }
}