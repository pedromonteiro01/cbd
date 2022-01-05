package cbd.mongo.ex4;


import java.util.Arrays;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

/* Examples: 
    https://www.mongodb.com/developer/quickstart/java-setup-crud-operations/?utm_campaign=javainsertingdocuments&utm_source=facebook&utm_medium=organic_social 
*/

public class MongoEx {

    public static void main( String[] args ) {

        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase("cbd");
        MongoCollection<Document> collection = db.getCollection("restaurants");
        
        /*
        > db.restaurants.find().limit(1)
        { "_id" : ObjectId("617a8c5e119b2fed10386399"), "address" : { "building" : "469", "coord" : 
        [ -73.961704, 40.662942 ], "rua" : "Flatbush Avenue", "zipcode" : "11225" }, 
        "localidade" : "Brooklyn", "gastronomia" : "Hamburgers", "grades" : [ { "date" : 
        ISODate("2014-12-30T00:00:00Z"), "grade" : "A", "score" : 8 }, { "date" : 
        ISODate("2014-07-01T00:00:00Z"), "grade" : "B", "score" : 23 }, { "date" : 
        ISODate("2013-04-30T00:00:00Z"), "grade" : "A", "score" : 12 }, { "date" : 
        ISODate("2012-05-08T00:00:00Z"), "grade" : "A", "score" : 12 } ], "nome" : 
        "Wendy'S", "restaurant_id" : "30112340" }
        */

        Document document = new Document("address", 
            new Document("building", "12").append("coord", Arrays.asList(-8.65136, 40.63805 ))
                .append("rua", "Rua Eca de Queiroz")
                .append("zipcode", "3810"))
                .append("localidade", "Aveiro")
                .append("gastronomia", "Portuguesa")
                .append("grades", Arrays.asList(
                    new Document("date", "2021-11-13T14:56:00Z").append("grade", "A")
                        .append("score", 9) ))
                        .append("nome", "Ramona")
                        .append("restaurant_id", "234427506");
        
        //insert function
        MongoFuncts.insert(collection, document);

        //update function
        String field = "gastronomia";
        String value = "Portuguesa";
        String newValue = "Aveirense";
        MongoFuncts.update(collection, document, field, value, newValue);

        //query function with Filter (eq, gte, lte,...)
        Bson condition = gte("restaurant_id", "100000");
        MongoFuncts.queryWithFilter(collection, condition);

        //query function
        Document cond = new Document("gastronomia", "Aveirense");
        MongoFuncts.query(collection, cond);

        //create indexes
        System.out.println("Indexes");
        String field2 = "localidade";
        MongoFuncts.createIndex(collection, field2);

        String field3 = "gastronomia";
        MongoFuncts.createIndex(collection, field3);

        String field4 = "nome";
        MongoFuncts.createTextIndex(collection, field4);

        for (Document index : collection.listIndexes()) {
            System.out.println(index.toJson());
         }
          
    }
}
