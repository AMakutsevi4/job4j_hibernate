package ru.job4j.hibernate.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.manyToMany.model.Author;
import ru.job4j.hibernate.manyToMany.model.Book;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book shark = Book.of("Акула");
            Book hares = Book.of("Зайцы");
            Book wolf = Book.of("Волки");
            Book bear = Book.of("Медведи");

            Author tolstoy = Author.of("Лев Толстой");
            tolstoy.getBooks().add(shark);
            tolstoy.getBooks().add(hares);

            Author pushkin = Author.of("Александр Пушкин");
            pushkin.getBooks().add(shark);
            pushkin.getBooks().add(wolf);
            pushkin.getBooks().add(bear);

            session.persist(tolstoy);
            session.persist(pushkin);

            Author author = session.get(Author.class, 1);
            session.remove(author);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
