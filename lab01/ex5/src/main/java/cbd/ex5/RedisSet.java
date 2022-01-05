package cbd.ex5;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisSet {
    private Jedis jedis;

    public RedisSet() {
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String set, String username) { // save user into set
        jedis.sadd(set, username);
    }

    public Set<String> getUsers(String users) { // return values of a set
        return jedis.smembers(users);
    }

    public Boolean checkUser(String set, String user){ // return true if user is in set, otherwise return false
        return jedis.sismember(set, user);
    }

    public Set<String> setDiff(String set1, String set2){// diference between 2 sets: [a,b,c] - [c,d,e] = [a,b,d,e]
        return jedis.sdiff(set1, set2);
    }

    public void remFollow(String set1, String value){// remove value from set
        jedis.srem(set1, value);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }
}
