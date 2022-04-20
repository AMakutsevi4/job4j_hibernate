package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Brand;
import ru.job4j.hibernate.model.Model;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model aqua = Model.of("aqua");
            session.save(aqua);
            Model rav = Model.of("Rav4");
            session.save(rav);
            Model mark = Model.of("Mark2");
            session.save(mark);
            Model landCruiser = Model.of("landCruiser200");
            session.save(landCruiser);

            Brand toyota = Brand.of("toyota");
            toyota.add(session.load(Model.class, 1));
            session.save(toyota);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
