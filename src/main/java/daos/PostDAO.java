package daos;

import com.mongodb.client.*;
import documents.Post;
import documents.User;
import org.bson.Document;

import java.util.ArrayList;

public class PostDAO {

    public static boolean addPost(String username, String text, ArrayList<String> tags) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document document = new Document()
                .append("username", username)
                .append("text", text)
                .append("tags", tags);
        collection.insertOne(document);

        mongoClient.close();
        return true;
    }

    public static ArrayList<Post> getAllPosts() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        FindIterable<Document> result = collection.find();

        ArrayList<Post> posts = null;

        // Check if operation was successful
        if (!result.iterator().hasNext())
            return posts;

        posts = new ArrayList<>();
        for (Document d : result) {
            String id = d.get("_id").toString();
            String username = d.get("username").toString();
            String text = d.get("text").toString();
            ArrayList<String> tags = (ArrayList<String>) d.get("tags");

            Post post = new Post(id, username, text, 0, tags, new ArrayList<>());
            System.out.println(post); // TODO delete debug text
            posts.add(post);
        }

        mongoClient.close();
        return posts;
    }

    public static ArrayList<Post> getPostsByUsername(String username) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document filter = new Document("username", username);
        FindIterable<Document> result = collection.find(filter);

        ArrayList<Post> posts = null;

        // Check if operation was successful
        if (!result.iterator().hasNext())
            return posts;

        posts = new ArrayList<>();
        for (Document d : result) {
            String id = d.get("_id").toString();
            String text = d.get("text").toString();
            ArrayList<String> tags = (ArrayList<String>) d.get("tags");

            Post post = new Post(id, username, text, 0, tags, new ArrayList<>());
            System.out.println(post); // TODO delete debug text
            posts.add(post);
        }

        mongoClient.close();
        return posts;
    }

}
