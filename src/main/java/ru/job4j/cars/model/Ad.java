package ru.job4j.cars.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());
    @Column(nullable = false)
    private boolean sold;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",  foreignKey = @ForeignKey(name = "MODEL_ID_FK"))
    private Model model;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id", foreignKey = @ForeignKey(name = "BODY_ID_FK"))
    private Body body;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "ads_photos",
            joinColumns = {@JoinColumn(
                    name = "ad_id", nullable = false,
                    foreignKey = @ForeignKey(name = "AD_ID_FK"))
            },
            inverseJoinColumns = {@JoinColumn(
                    name = "photo_id", nullable = false,
                    foreignKey = @ForeignKey(name = "PHOTO_ID_FK"))
            })
    private List<Photo> photos = new LinkedList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    public Ad(String description) {
        this.description = description;
    }

    public Ad() {
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return id == ad.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ad{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", sold=" + sold
                + ", model=" + model
                + ", body=" + body
                + ", photos=" + photos
                + ", user=" + user
                + '}';
    }
}
