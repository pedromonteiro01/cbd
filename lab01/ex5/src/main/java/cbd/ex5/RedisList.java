package cbd.ex5;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisList {
    private Jedis jedis;

    public static String USERS = "users_list"; // Key list for users' name

    public RedisList() {
        this.jedis = new Jedis("localhost");
    }

    public void saveMessage(String user, String message) { // save a message into list (on left)
        jedis.lpush(user, message);
    }

    public void saveUserLeft(String username) {
        jedis.lpush(USERS, username);
    }

    public void saveUserRight(String username) {
        jedis.rpush(USERS, username);
    }

    public List<String> getMessage(String m) { // get all messages from list
        return jedis.lrange(m, 0, -1);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
