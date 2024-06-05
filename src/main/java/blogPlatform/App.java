package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class App {

    public static void main(String[] args) {

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        // Get a reference to the database and collection
        MongoDatabase database = mongoClient.getDatabase("blogPlatform");
        MongoCollection<Document> collection = database.getCollection("posts");

        // Create a list of documents to insert
        List<Document> documentsToInsert = new ArrayList<Document>();
        documentsToInsert.add(new Document("name", "John").append("age", 30));
        documentsToInsert.add(new Document("name", "Jane").append("age", 25));
        documentsToInsert.add(new Document("name", "Bob").append("age", 35));

        // Insert the documents into the collection
        collection.insertMany(documentsToInsert);

        System.out.println("Documents inserted successfully!");

        // Close the MongoDB client
        mongoClient.close();
    }

}
