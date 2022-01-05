package com.jedis.app;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class WriteReadHash {
    private Jedis jedis;

    public static String USERS = "users_hash"; // Key hash for users' name
    public int i = 1;
    
    public WriteReadHash() {
        this.jedis = new Jedis("localhost");
    }
    
    public void saveUser(String username) {
        String user = "user"+Integer.toString(i);
        jedis.hset(USERS, user, username);
        System.out.println("User -> "+user+" value -> "+username);
        i++;
    }

    public Map<String, String> getUser() {
        return jedis.hgetAll(USERS);
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    public static void main(String[] args) {
        WriteReadHash board = new WriteReadHash();
        // set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };

        for (String user: users)
            board.saveUser(user);
            board.getUser().forEach((key, value) -> System.out.println(key + " : " + value));
            board.getAllKeys().stream().forEach(System.out::println);
    }
}
