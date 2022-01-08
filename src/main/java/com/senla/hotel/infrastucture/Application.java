package com.senla.hotel.infrastucture;

public class Application {

    public static ApplicationContext run(String packageToSkan) {
        Config config = new JavaConfig(packageToSkan);
        ApplicationContext context = ApplicationContext.getInstance();
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
