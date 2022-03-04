package com.senla.hotel.infrastucture;

import com.senla.hotel.annotation.PostConstruct;
import com.senla.hotel.exception.ObjectFactoryException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        postConstruct(t);
        return t;
    }

    private <T> void postConstruct(T t) {
        for (Method method : t.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)){
                method.setAccessible(true);
                try {
                    method.invoke(t);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ObjectFactoryException("Can not build post construct method");
                }
            }
        }
    }

    private <T> T getObjectInstance(Class<? extends T> implClass) {
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ObjectFactoryException("Can not create object");
        }
    }
}
