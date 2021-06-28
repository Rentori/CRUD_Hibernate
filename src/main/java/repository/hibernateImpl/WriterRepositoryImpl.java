package repository.hibernateImpl;

import model.Post;
import model.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import repository.WriterRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private static WriterRepository instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private WriterRepositoryImpl() {
    }

    public static WriterRepository getInstance() {
        if (instance == null) {
            instance = new WriterRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Writer save(Writer type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Post post = null;

        try {
            Query query = session.createQuery("FROM Post where id =: id");
            query.setParameter("id", type.getPostId());
            post = (Post) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Label not found");
        }

        type.setPosts(new ArrayList<>(Arrays.asList(post)));
        session.save(type);

        transaction.commit();
        session.close();
        return type;
    }

    @Override
    public Writer update(Writer type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, type.getId());
        writer.setFirstName(type.getFirstName());
        writer.setLastName(type.getLastName());
        session.update(writer);

        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public Writer getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Writer writer = null;

        Query query = session.createQuery("FROM Writer WHERE id =: id");
        query.setParameter("id", aLong);
        try {
            Object queryResult = query.getSingleResult();
            writer = (Writer) queryResult;
        } catch (NoResultException e) {
            System.out.println("Entity not found");
        }

        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, aLong);
        session.delete(writer);

        transaction.commit();
        session.close();
    }

    @Override
    public List<Writer> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List writers = session.createQuery("FROM Writer ").list();

        transaction.commit();
        session.close();

        return writers;
    }
}
