package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbnRunFetch {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf;
            sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Vacancy junior = Vacancy.of("Junior");
            Vacancy middle = Vacancy.of("Middle");
            Vacancy senior = Vacancy.of("Senior");

            VacancyDB date = new VacancyDB();
            date.addVacancy(junior);
            date.addVacancy(middle);
            date.addVacancy(senior);
            session.save(date);

            Candidate candidate = Candidate.of("Alexandr", "нет опыта", 80_000, date);
            session.save(candidate);

            Candidate candidateDB = session.createQuery(
                    "select distinct st from Candidate st "
                            + "join fetch st.date a "
                            + "join fetch a.vacancies b "
                            + "where st.id = :sId", Candidate.class
            ).setParameter("sId", 1).uniqueResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
