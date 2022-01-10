package com.senla.hotel.infrastucture;

import com.senla.hotel.serializer.Serializer;

public class Application {

    private Application() {
    }

    public static ApplicationContext run(String packageToSkan) {
        Config config = new JavaConfig(packageToSkan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);

        Serializer serializer = context.getObject(Serializer.class);
        serializer.deserialize();

        return context;
    }
}
