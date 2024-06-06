package menus;

import daos.PostDAO;
import documents.Post;
import documents.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewPostMenu {

    // Prompt user for a comment for the selected post
    private static void commentMenu(Scanner sc, Post selectedPost) {
        try {
            System.out.println("Enter your comment:");
            String input = sc.nextLine();

            PostDAO.updatePostComments(selectedPost.getId(), input);

            System.out.println("Comment made");
            System.out.println("Hit enter to return");
            sc.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine(); // Clear invalid input
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // After the user selects a post, prompt them to enter a comment or like the post
    private static void selectPostMenu(Scanner sc, ArrayList<Post> posts) {
        try {
            System.out.println("Enter a post number to select it, or hit enter to return:");
            String input = sc.nextLine();

            if (input == "")
                return;

            int index = Integer.parseInt(input) - 1; // Adjust to 0 based index
            Post selectedPost = posts.get(index);

            System.out.println("1. Comment");
            System.out.println("2. Like");
            System.out.println("3. Return");
            int menuInput = sc.nextInt();
            sc.nextLine();

            switch (menuInput) {
                case 1:
                    commentMenu(sc, selectedPost);
                    break;
                case 2:
                    PostDAO.incrementLikesById(selectedPost.getId());
                    System.out.println("Post liked");
                    System.out.println("Hit enter to return");
                    sc.nextLine();
                    break;
                case 3:
                    return;
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

    // Display a post to the end user
    private static void printPost(int index, Post post) {
        System.out.println(index + ".");
        System.out.println(post.getUsername());
        System.out.println("    " + post.getText());
        for (String s : post.getTags()) {
            System.out.println(s);
        }
        System.out.println("    Likes: " + post.getLikes());
        System.out.println("    ~Comments~");
        for (String s : post.getComments()) {
            System.out.println("       " + s);
        }
        System.out.println("============================================================================================================");

    }

    // Print all posts in the database
    private static void printAllPosts(Scanner sc) {
        ArrayList<Post> posts = PostDAO.getAllPosts();

        if (posts == null) {
            System.out.println("No posts found");
            return;
        }

        int index = 0;
        for (Post p : posts) {
            index++;
            printPost(index, p);
        }

        selectPostMenu(sc, posts);

    }

    // Print all posts by a specific user
    private static void printPostsByUsername(Scanner sc) {
        try {
            System.out.println("Enter username:");
            String username = sc.nextLine();
            ArrayList<Post> posts = PostDAO.getPostsByUsername(username);

            if (posts == null) {
                System.out.println("No posts found");
                return;
            }

            int index = 0;
            for (Post p : posts) {
                printPost(index, p);
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
                        printAllPosts(sc);
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
