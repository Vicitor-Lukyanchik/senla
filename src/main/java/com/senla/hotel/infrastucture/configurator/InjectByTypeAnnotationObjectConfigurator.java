package com.senla.hotel.infrastucture.configurator;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.exception.ObjectConfiguratorException;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.infrastucture.ObjectConfigurator;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                try {
                    field.set(t, object);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ObjectConfiguratorException("Can not inject object");
                }
            }
        }
    }
}
