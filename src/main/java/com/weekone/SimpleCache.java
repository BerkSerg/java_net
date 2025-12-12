package com.weekone;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleCache {
    private volatile Map<String, Object> cache = new HashMap<>();
    private Queue<String> keys = new ConcurrentLinkedQueue<>();

    public Object get(String key) {
        return cache.get(key);
    }

    public void put(String key, Object value) {
        if (keys.size() == 100){
            String keyQ = keys.poll();
            cache.remove(keyQ);
        }
        cache.put(key, value);
        keys.add(key);
    }
}
