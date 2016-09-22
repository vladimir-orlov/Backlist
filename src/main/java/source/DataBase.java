package source;

import core.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class DataBase  {
    public static List<Book> read() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Book> books = session.createQuery("FROM Book").list();
        session.close();
        System.out.println("Found " + books.size() + " Employees");
        return books;

    }

    public static void update(Book e) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Book em = (Book) session.load(Book.class, e.getId());
        em.setSubscriber(e.getSubscriber());
        em.setDate(e.getDate());
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully updated " + e.toString());

    }

    public static Book findByID(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Book e = (Book) session.get(Book.class, id);
        session.close();
        return e;
    }
}
