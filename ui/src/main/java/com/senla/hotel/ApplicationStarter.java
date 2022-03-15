package com.senla.hotel;

import com.senla.hotel.ui.MenuController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationStarter {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getBean("menuControllerImpl", MenuController.class).run();
    }
}
