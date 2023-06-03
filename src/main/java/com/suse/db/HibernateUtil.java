package com.suse.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static Session getSession() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(OvalFileEntity.class);
        configuration.addAnnotatedClass(ProductEntity.class);
        configuration.addAnnotatedClass(DefinitionEntity.class);
        configuration.addAnnotatedClass(AffectedProductEntity.class);

        configuration.configure("hibernate.cfg.xml");

        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory.openSession();
        } catch (Exception e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


}
