package com.senla.hotel.infrastucture;

import java.util.Set;

import org.reflections.Reflections;

import com.senla.hotel.exception.ConfigException;

public class JavaConfig implements Config {

    private Reflections scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        Set<Class<? extends T>> implClasses = scanner.getSubTypesOf(ifc);
        if (implClasses.size() != 1) {
            throw new ConfigException("Interface has zero or 2 or more implemented classes");
        }
        return implClasses.iterator().next();
    }

    public Reflections getScanner() {
        return scanner;
    }
}
