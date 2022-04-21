package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alexandr", "нет опыта", 80_000, new VacancyDB());
            Candidate two = Candidate.of("Petr", "опыт 15 лет", 1_000_000, new VacancyDB());
            Candidate three = Candidate.of("Sergey", "опыт 2 года", 550_000, new VacancyDB());

            session.save(one);
            session.save(two);
            session.save(three);

            List<Candidate> allCandidate = session.createQuery("from Candidate").list();

            Candidate candidate = (Candidate) session.createQuery("from Candidate where id = 1").uniqueResult();

            List<Candidate> candidateName = session.createQuery("from Candidate where name = 'Alexandr'").list();

            Query query = session.createQuery(
                    "update Candidate c set c.experience = :newExperience, c.salary = :newSalary where c.id = :id"
            );
            query.setParameter("newExperience", "опыт 1 год");
            query.setParameter("newSalary", 150_000);
            query.setParameter("id", 1);
            query.executeUpdate();

            session.createQuery("delete from Candidate where id = :id")
                    .setParameter("id", 3)
                    .executeUpdate();


            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
