package com.company.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Users {
    private static final int NUMBER_OF_HANGARS = 5;
    private final Map<Integer, Connection> users = new HashMap<>();
    public int addNewUser(Connection connection) {
        int i = getFreeHangar();
        users.put(i, connection);
        return i;
    }
    public Map<Integer, Connection> getUsers(){
        return users;
    }
    private int getFreeHangar(){
        Integer free = null;
        for (int i = 0; i < 5; i++) {
            if (!users.containsKey(i)) {
                free = i;
                break;
            }
        }
        if (free != null)
            return free;
        else throw new RuntimeException();
    }
    int deleteUser(Connection connection){
        AtomicInteger val = new AtomicInteger();

        users.forEach((key, value) -> {
            if (value == connection){
                val.set(key);
            }
        });
        users.remove(val.get());
        return val.get();
    }
}
