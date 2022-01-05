package com.jedis.app;

import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class WriteReadList {
    private Jedis jedis;

    public static String USERS = "users_list"; // Key list for users' name
    
    public WriteReadList() {
        this.jedis = new Jedis("localhost");
    }
    
    public void saveUserLeft(String username) {
        jedis.lpush(USERS, username);
    }

    public void saveUserRight(String username) {
        jedis.rpush(USERS, username);
    }

    public List<String> getUser() {
        return jedis.lrange(USERS, 0, -1); // first element -> 1 | last element -> -1
    }

    public Set<String> getAllKeys() {
        return jedis.keys("*");
    }

    public static void main(String[] args) {
        WriteReadList board = new WriteReadList();
        // set some users
        String[] users = { "Ana", "Pedro", "Maria", "Luis" };

        for (String user: users)
            board.saveUserLeft(user);
            //board.saveUserRight(user);
            board.getUser().stream().forEach(System.out::println);
            board.getAllKeys().stream().forEach(System.out::println);
    }
}