package com.senla.hotel.infrastucture.configurator;

import com.senla.hotel.annotation.ConfigProperty;
import com.senla.hotel.exception.ObjectConfiguratorException;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.infrastucture.ObjectConfigurator;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Properties;

public class ConfigPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private static final String EMPTY_LINE = "";
    private static final String REGEX = ";";
    private static final String POINT = ".";
    private static final String STRING_TYPE = "string";
    private static final String DATE_TYPE = "date";
    private static final String DATE_PATTERN = "d.MM.yyyy";
    private static final String PROPERTY_PATH = "config.properties";

    private Properties properties = null;
    private ApplicationContext context;

    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            if (annotation != null) {
                this.context = context;
                configureField(t, field, annotation);
            }
        }
    }

    private void configureField(Object t, Field field, ConfigProperty annotation) {
        if (annotation.configName().equals(EMPTY_LINE)) {
            String propertyName = t.getClass().getSimpleName() + POINT + field.getName();
            String property = getProperty(context, annotation, propertyName);
            setField(t, field, property.split(REGEX));
        } else {
            throw new ObjectConfiguratorException("There is not config file with name: " + annotation.configName());
        }
    }

    private String getProperty(ApplicationContext context, ConfigProperty annotation, String propertyName) {
        if (!annotation.propertyName().equals(EMPTY_LINE)) {
            propertyName = annotation.propertyName();
        }
        if (properties == null) {
            properties = getProperties(context);
        }
        String property = properties.getProperty(propertyName);
        return requireProperty(property, annotation);
    }

    private Properties getProperties(ApplicationContext context) {
        try (FileInputStream fileInputStream = new FileInputStream(findResourcePath(PROPERTY_PATH).toString())) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException | URISyntaxException e) {
            throw new ObjectConfiguratorException("File does not exist: " + PROPERTY_PATH);
        }
    }

    private Path findResourcePath(String path) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(path);
        return Paths.get(url.toURI());
    }

    private String requireProperty(String property, ConfigProperty annotation) {
        if (property == null) {
            throw new ObjectConfiguratorException("There is not property with name: " + annotation.propertyName());
        }
        return property;
    }

    private <T> void setField(Object t, Field field, String[] values) {
        T[] result = getValues(field, values);
        try {
            field.setAccessible(true);
            if (field.getType().isArray()) {
                field.set(t, result);
            } else if (List.class.isAssignableFrom(field.getType())) {
                field.set(t, Arrays.asList(result));
            } else {
                field.set(t, result[0]);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ObjectConfiguratorException("Can not set value on field");
        }
    }

    private <T> T[] getValues(Field field, String[] values) {
        if (Integer.TYPE.equals(field.getType())) {
            return (T[]) parseToInt(values);
        } else if (Double.TYPE.equals(field.getType())) {
            return (T[]) parseToDouble(values);
        } else if (Boolean.TYPE.equals(field.getType())) {
            return (T[]) parseToBoolean(values);
        } else if (field.getAnnotation(ConfigProperty.class).type().equals(STRING_TYPE)) {
            return (T[]) values;
        } else if (field.getAnnotation(ConfigProperty.class).type().equals(DATE_TYPE)) {
            return parseToDate(values);
        }
        throw new ObjectConfiguratorException(
                "There is not type with name: " + field.getAnnotation(ConfigProperty.class).type());
    }

    private Integer[] parseToInt(String[] values) {
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Integer.valueOf(Integer.parseInt(values[i]));
        }
        return result;
    }

    private Double[] parseToDouble(String[] values) {
        Double[] result = new Double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Double.valueOf(Double.parseDouble(values[i]));
        }
        return result;
    }

    private Boolean[] parseToBoolean(String[] values) {
        Boolean[] result = new Boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Boolean.valueOf(Boolean.parseBoolean(values[i]));
        }
        return result;
    }

    private <T> T[] parseToDate(String[] values) {
        LocalDate[] result = new LocalDate[1];
        result[0] = LocalDate.parse(values[0], DateTimeFormatter.ofPattern(DATE_PATTERN));
        return (T[]) result;
    }
}