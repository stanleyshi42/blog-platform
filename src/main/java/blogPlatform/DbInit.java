package blogPlatform;

import daos.PostDAO;
import daos.UserDAO;

import java.util.ArrayList;

public class DbInit {
    public static void main(String[] args) {

        UserDAO.addUser("stan", "pass1");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("jvm");
        PostDAO.addPost("stan", "i love java", tags);
    }
}
