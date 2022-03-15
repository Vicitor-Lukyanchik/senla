package com.senla.hotel.service.connection.hibernate;

import com.senla.hotel.service.exception.ServiceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Scope("singleton")
public class HibernateUtil {
    private SessionFactory sessionFactory;

    @PostConstruct
    private void createSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ServiceException("Can not create session factory");
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
