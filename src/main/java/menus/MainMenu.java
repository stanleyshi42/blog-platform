package menus;

import documents.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public static void run(Scanner sc, User user) {

        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. Create post");
            System.out.println("2. View posts");
            System.out.println("3. Update posts");
            System.out.println("4. Delete posts");
            System.out.println("5. Logout");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        CreatePostMenu.run(sc, user);
                        continue;
                    case 2:
                        // TODO
                        continue;
                    case 3:
                        // TODO
                        continue;
                    case 4:
                        // TODO
                        continue;
                    case 5:
                        return; // Return to login menu
                    default:
                        System.out.println("Invalid input");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine(); // Clear invalid input
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }
}
