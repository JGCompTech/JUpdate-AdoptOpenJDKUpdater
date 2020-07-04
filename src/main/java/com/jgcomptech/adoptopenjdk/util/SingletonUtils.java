package com.jgcomptech.adoptopenjdk.util;

import java.util.HashMap;
import java.util.function.Supplier;

@SuppressWarnings({"SingletonPattern", "unchecked"})
public class SingletonUtils {
    public static final HashMap<Class, Object> instances = new HashMap<>();
    public static <T> T getInstance(final Class<T> singletonObj, final Supplier<T> creator) {
        if(!instances.containsKey(singletonObj)) {
            if(creator == null) return null;
            instances.put(singletonObj, creator.get());
        }
        return (T) instances.get(singletonObj);
    }

    public static boolean instanceExists(final Class singletonObj) {
        return instances.containsKey(singletonObj);
    }
}
