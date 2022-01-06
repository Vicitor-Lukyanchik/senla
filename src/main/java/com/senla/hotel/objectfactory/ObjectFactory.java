package com.senla.hotel.objectfactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.config.Config;
import com.senla.hotel.configurator.ObjectConfigurator;
import com.senla.hotel.exception.ConfigException;

public class ObjectFactory {

    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(Config config) {
        for (Class<? extends ObjectConfigurator> aClass : config.getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(getObjectInstance(aClass));
        }
    }

    public <T> T createObject(Class type) {
        T t = (T) getObjectInstance(type);
        configurators.forEach(objectConfigurator -> objectConfigurator.configurate(t));
        return t;
    }

    private <T> T getObjectInstance(Class<? extends T> implClass) {
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new ConfigException("Can not create object");
        }
    }
}
