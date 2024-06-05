package menus;

import java.util.Scanner;

import daos.UserDAO;
import documents.User;

public class LoginMenu {

    private static boolean validateUsername(String username) {

        if (username.contains(" ")) {
            System.out.println("Error: Username contains whitespace");
            return false;
        }

        if (username == null) {
            System.out.println("Error: Invalid username");
            return false;
        }

        return true;
    }

    private static void registerMenu(Scanner sc) {

        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Password:");
            String password = sc.nextLine();

            username = username.trim();
            password = password.trim();

            if (!LoginMenu.validateUsername(username)) {
                return;
            }
            if (password.contains(" ")) {
                System.out.println("Error: Password contains whitespace");
                return;
            }

            if (UserDAO.addUser(username, password))
                System.out.println("New user successfully registered");
            else
                System.out.println("Failed to register new user");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void loginMenu(Scanner sc) {

        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Password:");
            String password = sc.nextLine();

            // Authenticate and log in
            if (UserDAO.authenticateUser(username, password)) {
                User user = UserDAO.getUser(username);
                if (user != null) {
                    //MainMenu.run(sc, user);
                }

            } else
                System.out.println("Login failed");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void run() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("~~~Better Twitter~~~");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        loginMenu(sc);
                        continue;
                    case 2:
                        registerMenu(sc);
                        continue;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Invalid input");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
