package Utilities;

import DAO.ProductDAO;
import model.Discount;
import model.Product;
import model.User;

import java.util.List;

public class PrintUtility {

    //defaults to get entire list of products
    public static void printProductList() {
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
}