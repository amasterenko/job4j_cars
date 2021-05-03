package ru.job4j.cars.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AdRepository {
    private final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory SF = new MetadataSources(REGISTRY)
            .buildMetadata().buildSessionFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AdRepository.class.getName());
    public static AdRepository instOf() {
        return Lazy.INST;
    }

    private static final class Lazy {
        private final static AdRepository INST = new AdRepository();
    }

    private <T> Optional<T> txFunc(final Function<Session, T> command) {
        final Session session = SF.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return Optional.of(rsl);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception: ", e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    public Optional<List<Ad>> findTodayAds() {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a join fetch a.body"
        + " join fetch a.model join fetch a.user left join fetch a.photos"
        + " where DATE(a.created) = current_date ", Ad.class).list());
    }

    public Optional<List<Ad>> findAdsWithPhoto() {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a join fetch a.body"
                        + " join fetch a.model join fetch a.user join fetch a.photos p"
                , Ad.class).list());
    }

    public Optional<List<Ad>> findAdsByModel(Model model) {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a join fetch a.body"
                        + " join fetch a.model m join fetch a.user" +
                        " left join fetch a.photos where m = :modelParam", Ad.class)
                .setParameter("modelParam", model).list());
    }

    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}
