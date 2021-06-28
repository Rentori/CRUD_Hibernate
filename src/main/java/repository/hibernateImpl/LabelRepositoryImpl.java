package repository.hibernateImpl;

import model.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import repository.LabelRepository;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    private static LabelRepositoryImpl instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private LabelRepositoryImpl() {
    }

    public static LabelRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LabelRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Label save(Label type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(type);

        transaction.commit();
        session.close();

        return type;
    }

    @Override
    public Label update(Label type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Label label = session.get(Label.class, type.getId());
        label.setName(type.getName());
        session.update(label);

        transaction.commit();
        session.close();

        return type;
    }

    @Override
    public Label getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Label label = session.get(Label.class, aLong);

        transaction.commit();
        session.close();

        return label;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.get(Label.class, aLong));

        transaction.commit();
        session.close();
    }

    @Override
    public List<Label> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List labels = session.createQuery("FROM Label").list();

        transaction.commit();
        session.close();

        return labels;
    }
}
