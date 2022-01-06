package com.senla.hotel.configurator;

import java.lang.reflect.Field;

import com.senla.hotel.annotation.Off;
import com.senla.hotel.exception.ConfigException;
import com.senla.hotel.ui.Action;

public class OffAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configurate(Object t) {
        Class implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Off.class)) {
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
