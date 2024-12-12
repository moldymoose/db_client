package Utilities;

import DAO.*;
import Models.*;

import java.util.List;

public class PrintUtility {

    //defaults to get entire list of products
    public static void printProductList() {
        System.out.print("\n");
        // Fetch and print all products
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.readAll();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("Available Products:");
        System.out.printf("%-5s %-30s %-10s %-15s\n", "ID", "Product Name", "Price", "Active");
        System.out.println("-------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d %-30s %-10.2f %-15b\n",
                    product.getId(), product.getProductName(), product.getPrice(), product.isActive());
        }
        System.out.println();
    }

    //Overloading to print a specific list of products
    public static void printProductList(List<Product> products) {
        System.out.print("\n");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("Available Products:");
        System.out.printf("%-5s %-30s %-10s %-15s\n", "ID", "Product Name", "Price", "Active");
        System.out.println("-------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d %-30s %-10.2f %-15b\n",
                    product.getId(), product.getProductName(), product.getPrice(), product.isActive());
        }
        System.out.println();
    }

    public static void printUserList(List<User> users) {
        System.out.print("\n");
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("User List:");
        System.out.printf("%-5s %-20s %-20s %-15s %-30s %-15s %-15s %-10s\n",
                "ID", "First Name", "Last Name", "Phone", "Address", "City", "State", "Active");
        System.out.println("-------------------------------------------------------------------------------------");

        for (User user : users) {
            System.out.printf("%-5d %-20s %-20s %-15s %-30s %-15s %-15s %-10b\n",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNum(),
                    user.getAddress(), user.getCity(), user.getState(), user.isActive());
        }
        System.out.println();
    }

    public static void printDiscountList(List<Discount> discounts) {
        System.out.print("\n");
        if (discounts.isEmpty()) {
            System.out.println("No discounts available.");
            return;
        }

        System.out.println("Discount List:");
        System.out.printf("%-5s %-50s %-10s\n", "ID", "Description", "Amount");
        System.out.println("--------------------------------------------------------------");

        for (Discount discount : discounts) {
            System.out.printf("%-5d %-50s %-10.2f\n",
                    discount.getId(), discount.getDescription(), discount.getAmount());
        }
        System.out.println();
    }

    public static void printTransaction(Transaction transaction) {
        LineItemDAO lineItemDAO = new LineItemDAO();

        List<LineItem> lineItems = lineItemDAO.readAll(transaction);

        if (lineItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.printf("%-10s %-25s %-15s %-15s %-15s%n", "ID", "Product", "Price", "Discount", "SubTotal");
        System.out.println("--------------------------------------------------");

        float cartTotal = 0.0f;
        for (LineItem lineItem : lineItems) {
            float totalCost = lineItem.getProductPrice() - lineItem.getDiscountAmount();
            cartTotal += totalCost;

            System.out.printf("%-10d %-25s $%-14.2f $%-14.2f $%-14.2f%n",
                    lineItem.getId(),
                    lineItem.getProduct(),
                    lineItem.getProductPrice(),
                    lineItem.getDiscountAmount(),
                    totalCost);
        }

        // Print Cart Total
        System.out.println("--------------------------------------------------");
        System.out.printf("Cart Total: $%-14.2f%n", cartTotal);
    }

    public static void printTransactionList(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println();
        System.out.printf("%-15s %-25s %-20s %-20s\n", "Transaction ID", "Date/Time", "First Name", "Last Name");
        System.out.println("----------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            // Directly access the user's first and last name from the transaction object
            System.out.printf("%-15d %-25s %-20s %-20s\n",
                    transaction.getId(),
                    transaction.getDate().toString(),  // Print Date/Time
                    transaction.getUserFirstName(), // Directly from the Transaction object
                    transaction.getUserLastName());  // Directly from the Transaction object
        }
        System.out.println();
    }
}