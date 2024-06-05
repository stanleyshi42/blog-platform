package menus;

import daos.PostDAO;
import documents.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CreatePostMenu {
    public static void run(Scanner sc, User user) {
        try {
            System.out.println("Enter post:");
            String textInput = sc.nextLine();

            // TODO ask for hashtags

            if(PostDAO.addPost(user.getUsername(), textInput, null))
                System.out.println("Post successfully made");
            else
                System.out.println("Post failed");

            System.out.println("Hit enter to return");
            sc.nextLine();
            return;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
