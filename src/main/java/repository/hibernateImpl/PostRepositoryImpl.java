package repository.hibernateImpl;

import model.Label;
import model.Post;
import model.PostStatus;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import repository.PostRepository;
import utilities.DateFormatter;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private static PostRepositoryImpl instance;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private PostRepositoryImpl() {
    }

    public static PostRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PostRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Post save(Post type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Label label = null;

        try {
            Query query = session.createQuery("FROM Label where id =: id");
            query.setParameter("id", type.getLabelId());
            label = (Label) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Label not found");
        }

        type.setUpdated(DateFormatter.getCurrentDate());
        type.setCreated(DateFormatter.getCurrentDate());
        type.setPostStatus(PostStatus.ACTIVE);
        type.setLabels(new ArrayList<>(Collections.singletonList(label)));
        session.save(type);

        transaction.commit();
        session.close();
        return type;
    }

    @Override
    public Post update(Post type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Post post = session.get(Post.class, type.getId());
        post.setPostStatus(PostStatus.UNDER_REVIEW);
        post.setContent(type.getContent());
        post.setUpdated(DateFormatter.getCurrentDate());
        session.update(post);

        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public Post getById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Post WHERE id =: id");
        query.setParameter("id", aLong);
        Object queryResult = query.getSingleResult();
        Post post = (Post) queryResult;

        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Post post = session.get(Post.class, aLong);
            post.setPostStatus(PostStatus.DELETED);
        } catch (NullPointerException e) {
            System.out.println("Post not fount");
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<Post> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List posts = session.createQuery("FROM Post").list();

        transaction.commit();
        session.close();

        return posts;
    }
}
