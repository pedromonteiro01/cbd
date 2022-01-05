package cbd.mongo.ex4.exD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.regex;

import org.bson.Document;

public class FunctsExD {
    static MongoCollection<Document> collection;
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase("cbd");
        collection = db.getCollection("restaurants");


        System.out.println("Numero de localidades distintas: "+ countLocalidade());

        System.out.println("Numero de restaurantes por localidade: ");
        Map<String, Integer> map = countRestByLocalidade();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("-> " + entry.getKey() + " - " + entry.getValue());
        }

        System.out.println("Nome de restaurantes contendo 'Park' no nome: ");
        List<String> list = getRestWithNameCloserTo("Park");
        for (String elem : list)
            System.out.println("-> "+elem);
        
    }

    // https://docs.mongodb.com/drivers/java/sync/v4.3/usage-examples/distinct/
    public static int countLocalidade(){
        int count = 0;
        
        DistinctIterable<String> documents = collection.distinct("localidade", String.class);
        MongoCursor<String> results = documents.iterator();
        
        while(results.hasNext()) {
            System.out.println(results.next());
            count++;
        }
            
        return count;
    }

    //https://www.javatips.net/api/com.mongodb.client.aggregateiterable
    public static Map<String, Integer> countRestByLocalidade(){
        Map<String, Integer> map = new HashMap<>();

        AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(group("$localidade",
                                                                        Accumulators.sum("number_restaurants",1))));

        for (Document doc : documents) {
            map.put(doc.get("_id").toString(), (int) (doc.get("number_restaurants")));
        }
        
        return map;
    }

    //https://stackoverflow.com/questions/44167845/how-to-query-contains-using-mongodb-java-3
    public static List<String> getRestWithNameCloserTo(String name){
        List<String> list = new ArrayList<String>();
        FindIterable<Document> documents = collection.find(regex("nome", ".*" + Pattern.quote(name) + ".*"));

        for (Document doc : documents) {
            list.add((String) doc.get("nome"));
        }
        
        return list;
    }
}
