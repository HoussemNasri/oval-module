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
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Definition.class);
        configuration.addAnnotatedClass(AffectedProduct.class);
        configuration.addAnnotatedClass(Reference.class);
        configuration.addAnnotatedClass(CVE.class);
        configuration.addAnnotatedClass(PackageTest.class);
        configuration.addAnnotatedClass(PackageState.class);
        configuration.addAnnotatedClass(PackageObject.class);
        configuration.addAnnotatedClass(PackageEvrStateEntity.class);
        configuration.addAnnotatedClass(PackageArchStateEntity.class);
        configuration.addAnnotatedClass(PackageVersionStateEntity.class);

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
