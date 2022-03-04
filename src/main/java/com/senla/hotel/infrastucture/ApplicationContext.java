package com.senla.hotel.infrastucture;

import com.senla.hotel.annotation.Singleton;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<Class, Object> cache = new HashMap<>();
    private Config config = new JavaConfig("com.senla.hotel");
    private ObjectFactory factory;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            implClass = config.getImplClass(type);
        }

        T t = factory.createObject(implClass);
        addSingleton(type, t);
        return t;
    }

    private <T> void addSingleton(Class<T> type, T t) {
        if (t.getClass().isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }
}
