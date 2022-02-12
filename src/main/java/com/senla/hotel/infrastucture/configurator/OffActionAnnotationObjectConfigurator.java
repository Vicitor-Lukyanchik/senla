package com.senla.hotel.infrastucture.configurator;

import java.lang.reflect.Field;

import com.senla.hotel.annotation.OffAction;
import com.senla.hotel.exception.ConfigException;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.infrastucture.ObjectConfigurator;
import com.senla.hotel.ui.Action;

public class OffActionAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, ApplicationContext context) {
        Class implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(OffAction.class)) {
                field.setAccessible(true);
                Action action = () -> System.out.println("\n This command is off");
                try {
                    field.set(t, action);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ConfigException("Can not set value on Action");
                }
            }
        }
    }
}
