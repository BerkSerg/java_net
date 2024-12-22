package org.example.demo.bookingservice.utils;

import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
@Value
public class BlackList {
    static ConcurrentHashMap<String, String> revokedTokens = new ConcurrentHashMap<>();

    public static void revokeToken(String token) {
        revokedTokens.put(token, token);
    }

    public static boolean isRevoked(String token) {
        return revokedTokens.containsKey(token);
    }

    public static void removeToken(String token) {
        revokedTokens.remove(token);
    }

}
