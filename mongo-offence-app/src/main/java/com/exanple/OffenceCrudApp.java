package com.exanple;


import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class OffenceCrudApp {
    public static void main(String[] args) {
       
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("MEPMS_DB");

            
            if (!db.listCollectionNames().into(new java.util.ArrayList<>()).contains("user")) {
                db.createCollection("user");
            }
            
            MongoCollection<Document> collection = db.getCollection("user");

            Document doc = new Document("username", "guest")
                                .append("password", "@12235#");
            collection.insertOne(doc);
            System.out.println("Inserted: " + doc.toJson());

            
            System.out.println("\nAll documents:");
            for (Document d : collection.find()) {
                System.out.println(d.toJson());
            }

            
//            collection.updateOne(eq("username", "guest"), set("password", "@001#"));
//            System.out.println("\nUpdated 'guest' password to '@001#'.");

            
            collection.deleteOne(eq("username", "guest"));
            System.out.println("Deleted document with username 'guest'.");

           
            System.out.println("\nList of all collections:");
            for (String name : db.listCollectionNames()) {
                System.out.println(name);
            }

            
            collection.drop();
            System.out.println("Collection 'Offence' dropped.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
