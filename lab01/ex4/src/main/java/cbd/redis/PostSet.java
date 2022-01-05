package cbd.redis;

import java.util.Set;
import redis.clients.jedis.Jedis;

class PostSet {
    private Jedis jedis;
    public static String USERS = "set_names";

    // Key set for users' name
    public PostSet() {
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String username) {
        jedis.zadd(USERS, 0 , username);
    }

    public Set<String> getUser(String s) {
        return jedis.zrangeByLex(USERS, "[" + s + "*", "[" + s + (char)0xFF);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
