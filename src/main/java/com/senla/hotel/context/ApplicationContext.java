package com.senla.hotel.context;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.config.Config;
import com.senla.hotel.config.JavaConfig;
import com.senla.hotel.objectfactory.ObjectFactory;

public class ApplicationContext {

    private static ApplicationContext instance;
    
    private Map<Class, Object> cache = new HashMap<>();
    private Config config = new JavaConfig("com.senla.hotel");
    private ObjectFactory factory = new ObjectFactory(config);
    
    public static ApplicationContext getInstance() {
        if(instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }
    
    public <T> T getObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if(cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);
        //will check annotation Singleton
        cache.put(type, t);
        return t;
    }

    public Config getConfig() {
        return config;
    }
}
