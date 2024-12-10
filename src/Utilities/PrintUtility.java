package Utilities;

import DAO.DiscountDAO;
import DAO.LineItemDAO;
import DAO.ProductDAO;
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
        System.out.printf("%-5s %-30s %-10s\n", "ID", "Description", "Amount");
        System.out.println("---------------------------------------------------");

        for (Discount discount : discounts) {
            System.out.printf("%-5d %-30s %-10.2f\n",
                    discount.getId(), discount.getDescription(), discount.getAmount());
        }
        System.out.println();
    }

    public static void printTransaction(Transaction transaction) {
        LineItemDAO lineItemDAO = new LineItemDAO();
        ProductDAO productDAO = new ProductDAO();
        DiscountDAO discountDAO = new DiscountDAO();

        List<LineItem> lineItems = lineItemDAO.readAll(transaction);

        if (lineItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("Shopping Cart:");
        System.out.printf("%-5s %-30s %-15s %-15s %-15s\n", "ID", "Product", "Price", "Discount", "Total Cost");
        System.out.println("---------------------------------------------------");

        float cartTotal = 0.0f;
        for (LineItem lineItem : lineItems) {
            Product product = productDAO.read(lineItem.getProductId());
            Discount discount = discountDAO.read(lineItem.getDiscountId());

            float discountValue = 0.0F;
            if(discount!= null) {
                discountValue = discount.getAmount();
            }
            cartTotal += (product.getPrice() - discountValue);
            System.out.printf("%-5s %-30s %-15.2f %-15.2f %-15.2f\n", lineItem.getId(), product.getProductName(), product.getPrice(), discountValue, (product.getPrice() - discountValue));

        }
        System.out.println("---------------------------------------------------");
        System.out.printf("%-5s %-10.2f\n", "Cart Total:", cartTotal);
        System.out.println();
    }
}