package cbd.redis;

import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class SetCsv {
    private Jedis jedis;
    public static String USERS = "popular_names"; // Key set for users' name

    public SetCsv() {
        this.jedis = new Jedis("localhost");
    }
    public void saveUser(String username, int value) {
        jedis.zadd(USERS, value, username); // sorted insertion
    }
    public Set<String> getUserSet() { // first element -> 0 | last element -> -1
        return jedis.zrevrange(USERS, 0, -1); // in this case elements are sorted
    }
    public List<String> getUserList() {
        return jedis.lrange(USERS, 0, -1);
    }
    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
