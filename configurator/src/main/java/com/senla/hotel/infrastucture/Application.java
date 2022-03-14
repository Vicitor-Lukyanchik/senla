package com.senla.hotel.infrastucture;

public class Application {

    private Application() {
    }

    public static ApplicationContext run(String packageToSkan) {
        Config config = new JavaConfig(packageToSkan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
