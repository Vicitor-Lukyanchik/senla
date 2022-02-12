package com.senla.hotel.infrastucture;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.exception.ObjectFactoryException;

public class ObjectFactory {

    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private ApplicationContext context;

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(getObjectInstance(aClass));
        }
    }

    public <T> T createObject(Class type) {
        T t = (T) getObjectInstance(type);
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
        return t;
    }

    private <T> T getObjectInstance(Class<? extends T> implClass) {
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new ObjectFactoryException("Can not create object");
        }
    }
}
