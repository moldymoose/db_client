package UI;

import DAO.*;
import Models.*;
import Utilities.InputValidator;
import Utilities.PrintUtility;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        String choice = "1";

        while (!choice.equals("7")) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Print All Users");
            System.out.println("2. Print All Products");
            System.out.println("3. Print All Transactions");
            System.out.println("4. Print All Discount Promotions");
            System.out.println("5. Add User");
            System.out.println("6. Add Product");
            System.out.println("7. Add Discount Promotion");
            System.out.println("8. Modify User");
            System.out.println("9. Modify Product");
            System.out.println("10. Modify Discount Promotion");
            System.out.println("11. Modify Transaction");
            System.out.println("12. Delete User");
            System.out.println("13. Delete Product");
            System.out.println("14. Back to Main Menu");

            System.out.print("Please select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    UserDAO userDAO = new UserDAO();
                    List<User> users = userDAO.readAll();
                    PrintUtility.printUserList(users);
                    break;
                case "2":
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.readAll();
                    PrintUtility.printProductList(products);
                    break;
                case "3":
                    TransactionDAO transactionDAO = new TransactionDAO();
                    List<Transaction> transactions = transactionDAO.readAll();
                    PrintUtility.printTransactionList(transactions);
                    break;
                case "4":
                    DiscountDAO discountDAO = new DiscountDAO();
                    List<Discount> discounts = discountDAO.readAll();
                    PrintUtility.printDiscountList(discounts);
                    break;
                case "5":
                    addNewUser(scanner);
                    break;
                case "6":
                    addNewProduct(scanner);
                    break;
                case "7":
                    addNewDiscountPromotion(scanner);
                    break;
                case "8":
                    modifyUser(scanner);
                    break;
                case "9":
                    modifyProduct(scanner);
                    break;
                case "10":
                    modifyDiscount(scanner);
                    break;
                case "11":
                    modifyTransaction(scanner);
                    break;
                case "12":
                    deleteUser(scanner);
                    break;
                case "13":
                    deleteProduct(scanner);
                    break;
                case "14":
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void modifyDiscount(Scanner scanner) {
        // Get discount ID and modify existing discount
        int discountId = InputValidator.promptForValidId("db_discount", scanner);
        DiscountDAO discountDAO = new DiscountDAO();
        Discount discount = discountDAO.read(discountId);

        System.out.println("Updating discount: " + discount.getDescription());

        System.out.print("Enter new discount description (or press Enter to skip): ");
        String description = scanner.nextLine();
        if (description.isEmpty()) description = discount.getDescription();  // No change if empty

        System.out.print("Enter new discount amount (or press Enter to skip): ");
        String amountInput = scanner.nextLine();
        float amount = discount.getAmount();  // Default to current amount if empty

        if (!amountInput.isEmpty()) {
            try {
                amount = Float.parseFloat(amountInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. No changes made to the discount.");
            }
        }

        // Create the updated discount object
        Discount updatedDiscount = new Discount(
                discount.getId(),  // ID remains the same
                description,
                amount
        );

        discountDAO.update(updatedDiscount);  // Update in the database
        System.out.println("Discount updated successfully.");
    }

    private static void modifyProduct(Scanner scanner) {
        // Get product ID and modify existing product
        int productId = InputValidator.promptForValidId("db_product", scanner);
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.read(productId);

        System.out.println("Updating product: " + product.getProductName());

        System.out.print("Enter new product name (or press Enter to skip): ");
        String productName = scanner.nextLine();
        if (productName.isEmpty()) productName = product.getProductName();  // No change if empty

        System.out.print("Enter new product description (or press Enter to skip): ");
        String description = scanner.nextLine();
        if (description.isEmpty()) description = product.getDescription();  // No change if empty

        System.out.print("Enter new price (or press Enter to skip): ");
        String priceInput = scanner.nextLine();
        float price = product.getPrice();  // Default to current price if empty

        if (!priceInput.isEmpty()) {
            try {
                price = Float.parseFloat(priceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. No changes made to the price.");
            }
        }

        System.out.print("Enter new brand ID (or press Enter to skip): ");
        String brandIdInput = scanner.nextLine();
        int brandId = product.getBrandId();  // Default to current brand ID if empty

        if (!brandIdInput.isEmpty()) {
            try {
                brandId = Integer.parseInt(brandIdInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid brand ID. No changes made to the brand.");
            }
        }

        // Create the updated product object
        Product updatedProduct = new Product(
                product.getId(),  // ID remains the same
                brandId,
                productName,
                description,
                price,
                product.isActive()  // Active status remains the same
        );

        productDAO.update(updatedProduct);  // Update in the database
        System.out.println("Product updated successfully.");
    }

    private static void modifyTransaction(Scanner scanner) {
        //Get the transaction ID to modify
        int transactionId = InputValidator.promptForValidId("db_transaction", scanner);
        TransactionDAO transactionDAO = new TransactionDAO();
        Transaction transaction = transactionDAO.read(transactionId);

        if (transaction == null) {
            System.out.println("Transaction not found.");
            return;
        }

        System.out.println("\nModifying Transaction ID: " + transaction.getId());
        System.out.println("Transaction Details: ");
        PrintUtility.printTransaction(transaction);

        // Start modifying the transaction with a loop
        transactionLoop(scanner, transaction);
    }

    private static void transactionLoop(Scanner scanner, Transaction transaction) {
        String choice = "0";

        while(!choice.equals("3")) {  // Continue loop until option 3 is chosen
            PrintUtility.printTransaction(transaction);  // Print the current transaction details

            System.out.println("\nTransaction Options:");
            System.out.println("1: Add Item");
            System.out.println("2: Remove Item");
            System.out.println("3: Return to Admin Menu");

            choice = scanner.nextLine();  //Get user input for transaction action

            switch (choice) {
                case "1":
                    addLineItem(scanner, transaction);  // Add item to the transaction
                    break;
                case "2":
                    removeLineItem(scanner, transaction);  // Remove item from the transaction
                    break;
                case "3":
                    System.out.println("Returning to Admin Menu.");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }

    private static void addLineItem(Scanner scanner, Transaction transaction) {
        PrintUtility.printProductList();

        LineItem lineItem = null;
        int productID = InputValidator.promptForValidId("db_product", scanner);

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.read(productID);

        LineItemDAO lineItemDAO = new LineItemDAO();

        System.out.println("Would you like to apply a discount code? y/n");
        if(InputValidator.getYesOrNo(scanner)) {
            System.out.print("Enter your discount code: ");
            int discountCode = InputValidator.promptForValidId("db_discount", scanner);

            DiscountDAO discountDAO = new DiscountDAO();
            Discount discount = discountDAO.read(discountCode);

            if(discount.getAmount() < product.getPrice()) {
                lineItem = new LineItem(product.getId(), product.getProductName(), product.getPrice(), transaction.getId(), discountCode, discount.getAmount());
            } else {
                System.out.println("Sorry that code isn't valid.");
                lineItem = new LineItem(productID, product.getProductName(), product.getPrice(), transaction.getId());
            }
        } else {
            lineItem = new LineItem(productID, product.getProductName(), product.getPrice(), transaction.getId());
        }
        lineItem = lineItemDAO.create(lineItem);
    }

    public static void removeLineItem(Scanner scanner, Transaction transaction) {
        int lineItemID = InputValidator.promptForValidId("db_lineitem", scanner);
        LineItemDAO lineItemDAO = new LineItemDAO();
        LineItem lineItem = lineItemDAO.read(lineItemID);

        //checks line item transaction id against transaction passed into it.
        if (lineItem.getTransactionId() == transaction.getId()) {
            lineItemDAO.delete(lineItemID);
            System.out.println("Item deleted.");
        } else {
            System.out.println("That item number does not match your current transaction.");
        }
    }

    private static void addNewUser(Scanner scanner) {
        // Get user details and create a new user
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone number (or press Enter to skip): ");
        String phoneNum = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter state: ");
        String state = scanner.nextLine();
        System.out.print("Enter zip code: ");
        String zipInput = "";
        int zip = 0;

        while (!zipInput.isEmpty()) {
            zipInput = scanner.nextLine();
            try {
                //Try to parse the input as an integer
                zip = Integer.parseInt(zipInput);

                // Check if the zip code has exactly 5 digits
                if (zip >= 10000 && zip <= 99999) {
                    break;  // Valid 5-digit zip code
                } else {
                    System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                }
            } catch (NumberFormatException e) {
                //If the input is not a valid integer, prompt the user again
                System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
            }
        }

        User newUser = new User(firstName, lastName, phoneNum, address, city, state, zip, true);
        UserDAO userDAO = new UserDAO();
        userDAO.create(newUser); // Assuming create method exists in UserDAO
        System.out.println("User added successfully.");
    }

    private static void addNewProduct(Scanner scanner) {
        // Get product details and create a new product
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product description (or press Enter to skip): ");
        String description = scanner.nextLine();
        if(description.isEmpty()) {
            //assigns description to null if string is empty
            description = null;
        }
        System.out.print("Enter product price: ");
        float price = scanner.nextFloat();
        System.out.print("Enter brand ID: ");
        int brandId = scanner.nextInt();

        Product newProduct = new Product(brandId, productName, description, price, true);
        ProductDAO productDAO = new ProductDAO();
        productDAO.create(newProduct); // Assuming create method exists in ProductDAO
        System.out.println("Product added successfully.");
    }

    private static void addNewDiscountPromotion(Scanner scanner) {
        // Get discount promotion details and create a new discount
        System.out.print("Enter discount description (or press Enter to skip): ");
        String description = scanner.nextLine();
        System.out.print("Enter discount amount: ");
        float amount = scanner.nextFloat();

        Discount newDiscount = new Discount(description, amount);
        DiscountDAO discountDAO = new DiscountDAO();
        newDiscount = discountDAO.create(newDiscount);
        System.out.println("Discount added successfully.");
    }

    private static void modifyUser(Scanner scanner) {
        //Get user ID and modify existing user
        int userId = InputValidator.promptForValidId("db_user", scanner);
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(userId);

        System.out.println("Updating account for " + user.getFirstName() + " " + user.getLastName());

        System.out.print("Enter new first name (or press Enter to skip): ");
        String firstName = scanner.nextLine();
        if (firstName.isEmpty()) firstName = user.getFirstName();

        System.out.print("Enter new last name (or press Enter to skip): ");
        String lastName = scanner.nextLine();
        if (lastName.isEmpty()) lastName = user.getLastName();

        System.out.print("Enter new phone number (or press Enter to skip): ");
        String phoneNum = scanner.nextLine();
        if (phoneNum.isEmpty()) phoneNum = user.getPhoneNum();

        System.out.print("Enter new address (or press Enter to skip): ");
        String address = scanner.nextLine();
        if (address.isEmpty()) address = user.getAddress();

        System.out.print("Enter new city (or press Enter to skip): ");
        String city = scanner.nextLine();
        if (city.isEmpty()) city = user.getCity();

        System.out.print("Enter new state (or press Enter to skip): ");
        String state = scanner.nextLine();
        if (state.isEmpty()) state = user.getState();

        System.out.print("Enter new zip code (or press Enter to skip): ");
        String zipInput = scanner.nextLine();

        int zip = user.getZip();  //Default to current zip

        while (!zipInput.isEmpty()) {
            try {
                //Try to parse the input as an integer
                zip = Integer.parseInt(zipInput);

                //Check if the zip code has 5 digits
                if (zip >= 10000 && zip <= 99999) {
                    break;  // Valid 5-digit zip code
                } else {
                    System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                    zipInput = scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                //If the input is not a valid integer, prompt the user again
                System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                zipInput = scanner.nextLine();
            }
        }

        //Create updated user object and call the DAO update method
        User updatedUser = new User(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                phoneNum,
                address,
                city,
                state,
                zip,
                user.isActive()
        );

        userDAO.update(updatedUser);
    }

    private static void deleteUser(Scanner scanner) {
        int userId = InputValidator.promptForValidId("db_user", scanner);
        UserDAO userDAO = new UserDAO();
        userDAO.delete(userId);
        System.out.println("User deleted successfully.");
    }

    private static void deleteProduct(Scanner scanner) {
        int productId = InputValidator.promptForValidId("db_product", scanner);
        ProductDAO productDAO = new ProductDAO();
        productDAO.delete(productId);
        System.out.println("Product deleted successfully.");
    }
}
