package com.senla.hotel.service.connection.hibernate;

import com.senla.hotel.annotation.PostConstruct;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Singleton
public class HibernateUtil {
    private SessionFactory sessionFactory;

    @PostConstruct
    private void createSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new DAOException("Can not create session factory");
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
