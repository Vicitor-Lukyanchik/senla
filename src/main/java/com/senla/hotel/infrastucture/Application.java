package com.senla.hotel.infrastucture;

import com.senla.hotel.exception.FileException;
import com.senla.hotel.serializer.Serializer;

public class Application {

    private Application() {
    }

    public static ApplicationContext run(String packageToSkan) {
        Config config = new JavaConfig(packageToSkan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        serialise(context);
        return context;
    }

    private static void serialise(ApplicationContext context){
        Serializer serializer = context.getObject(Serializer.class);
        try {
            serializer.deserialize();
        } catch (FileException ex) {}
    }
}
