package com.senla.hotel.infrastucture;

import com.senla.hotel.service.connection.ConnectionPool;

public class Application {

    private Application() {
    }

    public static ApplicationContext run(String packageToSkan) {
        Config config = new JavaConfig(packageToSkan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        createConnectionPool(context);
        return context;
    }

    private static void createConnectionPool(ApplicationContext context) {
        ConnectionPool connectionPool = context.getObject(ConnectionPool.class);
        connectionPool.create();
    }
}
