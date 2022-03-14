package com.senla.hotel.infrastucture.configurator;

import com.senla.hotel.annotation.Log;
import com.senla.hotel.exception.ObjectConfiguratorException;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.infrastucture.ObjectConfigurator;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;

public class LoggerAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Log.class)) {
                field.setAccessible(true);
                Object object = LogManager.getLogger(t.getClass().getName());
                try {
                    field.set(t, object);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ObjectConfiguratorException("Can not inject logger");
                }
            }
        }
    }
}
