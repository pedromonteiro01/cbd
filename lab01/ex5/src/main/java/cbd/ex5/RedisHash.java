package cbd.ex5;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisHash {
    private Jedis jedis;

    public static String USERS = "users_hash"; // Key hash for users' name
    public int i = 1;

    public RedisHash() {
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String key, Map<String, String> m) { // save multiple values into hash
        jedis.hmset(key, m);
    }

    public Map<String, String> getUser() { // get all values from hash without a given hash
        return jedis.hgetAll(USERS);
    }

    public Map<String, String> getUserMap(String s) { // get all values from a hash
        return jedis.hgetAll(s);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

}
