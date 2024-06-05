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
            System.out.println(d.toJson());
            String username = d.get("username").toString();
            String text = d.get("text").toString();
            ArrayList<String> tags = (ArrayList<String>) d.get("tags");

            Post post = new Post(username, text, 0, tags, new ArrayList<>());
            posts.add(post);
        }

        mongoClient.close();
        return posts;
    }

    // TODO DELETE
    public static void main() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");


        ArrayList<String> tags = new ArrayList<>();
        tags.add("#new");
        tags.add("#firstpost");

        Document document = new Document()
                .append("userId", 1)
                .append("text", "My first post!")
                .append("likes", 2)
                .append("tags", tags);

        // Create (Insert)
        collection.insertOne(document);
        System.out.println("Document inserted successfully!");

        // Read (Find) - Find all documents
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

        // Update (updateOne)
        Document updateFilter = new Document("_id", document.get("_id"));  // Assuming _id is used
        Document update = new Document("$set", new Document("age", 35));
        collection.updateOne(updateFilter, update);
        System.out.println("Document updated successfully!");

        // Delete (deleteOne)
        //collection.deleteOne(updateFilter);
        //System.out.println("Document deleted successfully!");

        mongoClient.close();
    }

}
