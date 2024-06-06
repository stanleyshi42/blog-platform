package blogPlatform;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;

import daos.*;
import documents.*;


// Drops all collections and recreates them with test data
public class DbInit {

    private static void dropUsersCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("users");

        collection.drop();
        mongoClient.close();
    }

    private static void dropPostsCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        collection.drop();
        mongoClient.close();
    }

    public static void main(String[] args) {

        dropUsersCollection();
        dropPostsCollection();

        UserDAO.addUser("test", "password");
        UserDAO.addUser("stan98", "pass1");
        UserDAO.addUser("john_johnson", "john");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("firstpost");
        tags.add("#hi");
        PostDAO.addPost("stan98", "hello world!", tags);
        PostDAO.addPost("test", "test post plz ignore", new ArrayList<String>());
        PostDAO.addPost("stan98", "I love Java", new ArrayList<String>());
        PostDAO.addPost("john_johnson", "coffee good", new ArrayList<String>());
        PostDAO.addPost("john_johnson", "i miss twitter", new ArrayList<String>());

        ArrayList<Post> posts = PostDAO.getAllPosts();
        Post post = posts.getFirst();
        PostDAO.incrementLikesById(post.getId());
        PostDAO.updatePostComments(post.getId(), "hi ^_^");
        PostDAO.updatePostComments(post.getId(), "⊂(◉‿◉)つ");
        PostDAO.updatePostText(post.getId(), "hello world!!!");

    }
}
