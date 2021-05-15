package ru.job4j.cars.store;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Map;
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
            return Optional.ofNullable(rsl);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception: ", e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    public Optional<Ad> saveAd(Ad ad) {
        return this.txFunc(session -> {
            session.save(ad);
            return ad;
        });
    }

    public Optional<User> saveUser(User user) {
        return this.txFunc(session -> {
            session.save(user);
            return user;
        });
    }

    public Optional<Ad> update(Ad ad) {
        return this.txFunc(session -> {
            session.update(ad);
            return ad;
        });
    }

    public Optional<List<Ad>> findTodayAds() {
        return this.txFunc(session -> session.createQuery(
                    "select distinct a from Ad a left join fetch a.model m "
                            + "left join fetch m.make "
                            + "left join fetch a.body left join fetch a.color "
                            + "left join fetch a.engine left join fetch a.transmission "
                            + "left join fetch a.user u left join fetch a.photos "
                            + "left join fetch u.credentials where DATE(a.created) = current_date "
                            + "order by a.created", Ad.class)
                    .list());
    }

    public Optional<List<Ad>> findAdsWithPhoto() {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a left join fetch a.model m "
                        + "left join fetch m.make "
                        + "left join fetch a.body left join fetch a.color "
                        + "left join fetch a.engine left join fetch a.transmission "
                        + "left join fetch a.user u join fetch a.photos "
                        + "left join fetch u.credentials "
                        + "order by a.created", Ad.class).list());
    }

    public Optional<List<Ad>> findAdsByModel(Model model) {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a left join fetch a.model m "
                        + "left join fetch m.make "
                        + "left join fetch a.body left join fetch a.color "
                        + "left join fetch a.engine left join fetch a.transmission "
                        + "left join fetch a.user u left join fetch a.photos "
                        + "left join fetch u.credentials where m = :modelParam "
                        + "order by a.created", Ad.class)
                .setParameter("modelParam", model).list());
    }

    public Optional<List<Model>> findModels() {
        return this.txFunc(session -> session.createQuery(
                    "select distinct m from Model m join fetch m.make", Model.class)
                    .list());
    }

    public Optional<List<Ad>> findAds() {
        return this.txFunc(session -> session.createQuery(
                    "select distinct a from Ad a left join fetch a.model m "
                            + "left join fetch m.make "
                            + "left join fetch a.body left join fetch a.color "
                            + "left join fetch a.engine left join fetch a.transmission "
                            + "left join fetch a.user u left join fetch a.photos "
                            + "left join fetch u.credentials order by a.created"
                    , Ad.class)
                    .list()
        );
    }

    public Optional<List<Ad>> findActiveAds() {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a left join fetch a.model m "
                        + "left join fetch m.make "
                        + "left join fetch a.body left join fetch a.color "
                        + "left join fetch a.engine left join fetch a.transmission "
                        + "left join fetch a.user u left join fetch a.photos "
                        + "left join fetch u.credentials where a.sold != true order by a.created"
                , Ad.class)
                .list()
        );
    }

    public Optional<List<Ad>> filterAds(Map<String, Map<String, Object>> filters) {
        return this.txFunc(session -> {
            filters.forEach((f, p) -> {
                Filter hbmFilter = session.enableFilter(f);
                p.forEach(hbmFilter::setParameter);
            });
            return session.createQuery(
                    "select distinct a from Ad a join fetch a.model m "
                            + "left join fetch m.make "
                            + "left join fetch a.body left join fetch a.color "
                            + "left join fetch a.engine left join fetch a.transmission "
                            + "left join fetch a.user u left join fetch a.photos "
                            + "left join fetch u.credentials order by a.created"
                    , Ad.class)
                    .list();
        });
    }

    public Optional<Ad> findAdById(int id) {
        return this.txFunc(session -> session.createQuery(
                "from Ad where id =: paramId"
                , Ad.class)
                .setParameter("paramId", id)
                .getSingleResult());
    }

    public Optional<List<Ad>> findAdsByUser(User user) {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a left join fetch a.model m "
                        + "left join fetch m.make "
                        + "left join fetch a.body left join fetch a.color "
                        + "left join fetch a.engine left join fetch a.transmission "
                        + "left join fetch a.user u left join fetch a.photos "
                        + "left join fetch u.credentials where a.user =:userParam order by a.created"
                , Ad.class)
                .setParameter("userParam", user)
                .list());
    }

    public Optional<User> findUser(String email) {
        return this.txFunc(session -> session.createQuery(
                "from User u left join fetch u.credentials where u.email = :emailParam", User.class)
                .setParameter("emailParam", email).getSingleResult()
        );
    }

    public Optional<User> findAdOwner(Ad ad) {
        return this.txFunc(session -> session.createQuery(
                "select distinct a from Ad a left join fetch a.user where a.id =: paramId", Ad.class)
                .setParameter("paramId", ad.getId()).getSingleResult().getUser());
    }


    public Optional<Photo> findPhoto(int id) {
        return this.txFunc(session -> {
                    List<Photo> rsl = session.createQuery(
                            "from Photo where id =: paramId", Photo.class)
                            .setParameter("paramId", id)
                            .list();
                    return rsl.isEmpty() ? null : rsl.get(0);
                }
        );
    }


    public Optional<List<Make>> findMakes() {
        return this.txFunc(session -> session.createQuery(
                    "from Make m order by m.name"
                    , Make.class)
                    .list()
        );
    }

    public Optional<List<Model>> findModelsByMake(Make make) {
        return this.txFunc(session -> session.createQuery(
                "select new Model(id, name) from Model m where m.make =:paramMake order by m.name"
                , Model.class)
                .setParameter("paramMake", make)
                .list());
    }

    public Optional<List<Body>> findBodies() {
        return this.txFunc(session -> session.createQuery(
                "from Body b order by b.name"
                , Body.class)
                .list());
    }

    public Optional<List<Color>> findColors() {
        return this.txFunc(session -> session.createQuery(
                "from Color c order by c.name"
                , Color.class)
                .list());
    }

    public Optional<List<Engine>> findEngines() {
        return this.txFunc(session -> session.createQuery(
                "from Engine e order by e.name"
                , Engine.class)
                .list());
    }

    public Optional<List<Transmission>> findTransmissions() {
        return this.txFunc(session -> session.createQuery(
                "from Transmission t order by t.name"
                , Transmission.class)
                .list());
    }


    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}
