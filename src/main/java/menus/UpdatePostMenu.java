package menus;

import daos.PostDAO;
import documents.Post;
import documents.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdatePostMenu {

    private static boolean printPosts(ArrayList<Post> posts) {
        try {
            if (posts == null) {
                System.out.println("No posts found");
                return false;
            }

            int index = 0;
            for (Post p : posts) {
                System.out.println(++index + ".");
                System.out.println("    " + p.getText());
                for (String s : p.getTags()) {
                    System.out.println(s);
                }
                System.out.println("    Likes: " + p.getLikes());
                for (String s : p.getComments()) {
                    System.out.println(s);
                }
                System.out.println("============================================================================================================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void run(Scanner sc, User user) {
        try {
            ArrayList<Post> posts = PostDAO.getPostsByUsername(user.getUsername());

            if (!printPosts(posts)) {
                return;
            }

            System.out.println("Enter post number to edit:");
            int indexInput = sc.nextInt() - 1; // Adjust to 0 based index
            sc.nextLine();

            if (indexInput >= posts.size()) {
                System.out.println("Invalid input");
                return;
            }

            Post post = posts.get(indexInput);

            System.out.println("Enter new post text:");
            String textInput = sc.nextLine();

            PostDAO.updatePostText(post.getId(), textInput);

            System.out.println("Post updated");
            System.out.println("Hit enter to return");
            sc.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
