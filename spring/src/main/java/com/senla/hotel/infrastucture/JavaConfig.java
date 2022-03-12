package com.senla.hotel.infrastucture;

import com.senla.hotel.exception.ConfigException;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

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

    public <T> Class<? extends T> getImplClass(Class<T> ifc, Class impl) {
        Set<Class<? extends T>> implClasses = scanner.getSubTypesOf(ifc);
        if (implClasses.size() != 1) {
            Set<Class<? extends T>> simpleClasses = new HashSet<>();
            for (Class<? extends T> implClass : implClasses) {
                if(!Modifier.isAbstract(implClass.getModifiers())) {
                    simpleClasses.add(implClass);
                }
            }

            if(simpleClasses.size() != 1){
                for (Class<? extends T> simpleClass : simpleClasses) {
                    if(simpleClass == impl) {
                        return impl;
                    }
                }

            } else {
                return simpleClasses.iterator().next();
            }
        } else {
            return implClasses.iterator().next();
        }
        return impl;
    }

    public Reflections getScanner() {
        return scanner;
    }
}
