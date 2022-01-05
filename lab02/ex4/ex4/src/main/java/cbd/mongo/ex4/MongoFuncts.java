package cbd.mongo.ex4;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import com.mongodb.client.FindIterable;

public class MongoFuncts {
    public static void insert(MongoCollection<Document> collection, Document document){
        collection.insertOne(document);
    }

    public static void update(MongoCollection<Document> collection, Document document, String field, String value, String newValue){
        Bson filter = eq(field, value);
        Bson updateOperation = set(field, newValue);
        UpdateResult updateResult = collection.updateOne(filter, updateOperation);
        System.out.println(updateResult);
    }

    public static void query(MongoCollection<Document> collection, Document condition){
        FindIterable<Document> iterable = collection.find(condition);
        MongoCursor<Document> cursor = iterable.iterator();
        System.out.println("---Querying---");
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
    }

    public static void queryWithFilter(MongoCollection<Document> collection, Bson condition){
        FindIterable<Document> iterable = collection.find(condition);
        MongoCursor<Document> cursor = iterable.iterator();
        System.out.println("---Querying with Filters---");
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
    }

    public static void createIndex(MongoCollection<Document> collection, String field){
        collection.createIndex(Indexes.ascending(field));
    }

    public static void createTextIndex(MongoCollection<Document> collection, String field){
        collection.createIndex(Indexes.text(field));
    }
}
