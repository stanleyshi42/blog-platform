package daos;

import com.mongodb.client.*;
import documents.User;
import org.bson.Document;

public class UserDAO {

    public static boolean addUser(String username, String password) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("users");

        // Check if username is taken
        User user = getUser(username);
        if (user != null) {
            System.out.println("Error: Username taken");
            return false;
        }

        // Else, create user
        Document document = new Document()
                .append("username", username)
                .append("password", password);
        var result = collection.insertOne(document);

        mongoClient.close();
        // If operation was successful, return true
        if (result.wasAcknowledged())
            return true;
        return false;
    }

    public static User getUser(String username) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("users");

        Document filter = new Document("username", username);
        FindIterable<Document> result = collection.find(filter);

        User user = null;
        if (result.iterator().hasNext()) {
            Document userDocument = result.iterator().next();
            String password = userDocument.get("password").toString();
            user = new User(username, password);
        } else {
            return null;
        }

        mongoClient.close();
        return user;
    }

    // Check login credentials
    public static boolean authenticateUser(String username, String password) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("users");

        Document filter = new Document()
                .append("password", password)
                .append("username", username);
        FindIterable<Document> result = collection.find(filter);

        mongoClient.close();
        if (result.iterator().hasNext()) {
            return true;
        }
        return false;
    }

}
