import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Display menu
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Start a Transaction");
            System.out.println("2. Add a User");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    TransactionManager.startTransaction(scanner);
                    break;
                case 2:
                    UserManager.addUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}