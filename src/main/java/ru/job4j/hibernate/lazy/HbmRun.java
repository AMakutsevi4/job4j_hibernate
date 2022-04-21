package ru.job4j.hibernate.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.lazy.model.Brands;
import ru.job4j.hibernate.lazy.model.Models;
import ru.job4j.hibernate.toMany.model.Model;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        List<Brands> brands = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Brands toyota = Brands.of("Toyota");
            session.save(toyota);

            Models aqua = Models.of("aqua", toyota);
            session.save(aqua);
            Models rav = Models.of("Rav4", toyota);
            session.save(rav);
            Models mark = Models.of("Mark2", toyota);
            session.save(mark);
            Models landCruiser = Models.of("landCruiser200", toyota);
            session.save(landCruiser);

            brands = session.createQuery("select distinct b from Brands b join fetch b.models").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Models model : brands.get(0).getModels()) {
            System.out.println(model);
        }
    }
}
