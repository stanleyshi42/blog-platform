package menus;

import daos.PostDAO;
import documents.Post;
import documents.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewPostMenu {

    private static void printPost(Post post) {
        System.out.println(post.getUsername());
        System.out.println("    " + post.getText());
        for (String s : post.getTags()) {
            System.out.println(s);
        }
        System.out.println("    Likes: " + post.getLikes());
        for (String s : post.getComments()) {
            System.out.println(s);
        }
        System.out.println("============================================================================================================");

    }

    private static void printAllPosts() {
        ArrayList<Post> posts = PostDAO.getAllPosts();

        if (posts == null) {
            System.out.println("No posts found");
            return;
        }

        for (Post p : posts) {
            printPost(p);
        }


    }

    private static void printPostsByUsername(Scanner sc) {
        try {
            System.out.println("Enter username:");
            String username = sc.nextLine();
            ArrayList<Post> posts = PostDAO.getPostsByUsername(username);

            if (posts == null) {
                System.out.println("No posts found");
                return;
            }

            for (Post p : posts) {
                printPost(p);
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine(); // Clear invalid input
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void run(Scanner sc, User user) {

        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. View all posts");
            System.out.println("2. View posts by username");
            System.out.println("3. Return");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        printAllPosts();
                        continue;
                    case 2:
                        printPostsByUsername(sc);
                        continue;
                    case 3:
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
