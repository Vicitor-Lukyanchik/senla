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
            InjectByType annotation = field.getAnnotation(InjectByType.class);
            if (annotation != null) {
                field.setAccessible(true);
                Object object = createObject(context, field, annotation);
                try {
                    field.set(t, object);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ObjectConfiguratorException("Can not inject object");
                }
            }
        }
    }

    private Object createObject(ApplicationContext context, Field field, InjectByType annotation) {
        Object object = null;
        if(annotation.clazz().equals(void.class)) {
            object = context.getObject(field.getType());
        } else {
            object = context.getObject(annotation.clazz());
        }
        return object;
    }
}
