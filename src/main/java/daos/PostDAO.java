package daos;

import com.mongodb.client.*;
import documents.Post;
import documents.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class PostDAO {

    public static boolean addPost(String username, String text, ArrayList<String> tags) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document document = new Document()
                .append("username", username)
                .append("text", text)
                .append("likes", 0)
                .append("tags", tags)
                .append("comments", new ArrayList<String>());
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
        if (!result.iterator().hasNext()) return posts;

        posts = new ArrayList<>();
        for (Document d : result) {
            String id = d.get("_id").toString();
            String username = d.get("username").toString();
            String text = d.get("text").toString();
            int likes = Integer.parseInt(d.get("likes").toString());
            ArrayList<String> tags = (ArrayList<String>) d.get("tags");
            ArrayList<String> comments = (ArrayList<String>) d.get("comments");

            Post post = new Post(id, username, text, likes, tags, comments);
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
        if (!result.iterator().hasNext()) return posts;

        posts = new ArrayList<>();
        for (Document d : result) {
            String id = d.get("_id").toString();
            String text = d.get("text").toString();
            int likes = Integer.parseInt(d.get("likes").toString());
            ArrayList<String> tags = (ArrayList<String>) d.get("tags");
            ArrayList<String> comments = (ArrayList<String>) d.get("comments");

            Post post = new Post(id, username, text, likes, tags, comments);
            posts.add(post);
        }

        mongoClient.close();
        return posts;
    }

    public static Post getPostById(String id) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document filter = new Document("_id", new ObjectId(id));
        FindIterable<Document> result = collection.find(filter);

        // Check if operation was successful
        if (!result.iterator().hasNext()) {
            mongoClient.close();
            return null;
        }

        Document postDocument = result.iterator().next();
        String username = postDocument.get("username").toString();
        String text = postDocument.get("text").toString();
        int likes = Integer.parseInt(postDocument.get("likes").toString());
        ArrayList<String> tags = (ArrayList<String>) postDocument.get("tags");
        ArrayList<String> comments = (ArrayList<String>) postDocument.get("comments");

        Post post = new Post(id, username, text, likes, tags, comments);
        mongoClient.close();
        return post;
    }

    public static boolean updatePostText(String id, String text) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document updateFilter = new Document("_id", new ObjectId(id));
        Document updateDocument = new Document("$set", new Document("text", text));
        collection.updateOne(updateFilter, updateDocument);

        mongoClient.close();
        return true;
    }

    // Add given comment to post's comments list
    public static boolean updatePostComments(String id, String comment) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        ArrayList<String> comments = getPostById(id).getComments();
        comments.add(comment);

        Document updateFilter = new Document("_id", new ObjectId(id));
        Document updateDocument = new Document("$set", new Document("comments", comments));
        collection.updateOne(updateFilter, updateDocument);

        mongoClient.close();
        return true;
    }

    public static boolean incrementLikesById(String id) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Post post = getPostById(id);
        if (post == null)
            return false;
        post.setLikes(post.getLikes() + 1);

        Document updateFilter = new Document("_id", new ObjectId(post.getId()));
        Document updateDocument = new Document("$set", new Document("likes", post.getLikes()));
        collection.updateOne(updateFilter, updateDocument);

        mongoClient.close();
        return true;

    }

    public static boolean deletePost(String id) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        Document filter = new Document("_id", new ObjectId(id));
        var result = collection.deleteOne(filter);
        return result.wasAcknowledged();
    }

}
