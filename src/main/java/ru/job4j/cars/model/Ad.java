package ru.job4j.cars.model;


import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
/**
 * Class represents car selling ad.
 * The hibernate filters are defined for using in AdRepository.
 * For more information on the relationship of the entity, see db/cars_scheme_png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */

@Entity
@Table(name = "ads")

@FilterDef(name="filterCreated", parameters= @ParamDef( name="daysAgo", type="integer" ))
@FilterDef(name="filterPrice", parameters={
        @ParamDef( name="minPrice", type="integer" ),
        @ParamDef( name="maxPrice", type="integer" )
} )
@FilterDef(name="filterMake", parameters= @ParamDef( name="makeId", type="integer" ))
@FilterDef(name="filterModel", parameters= @ParamDef( name="modelId", type="integer" ))
@FilterDef(name="filterEngine", parameters= @ParamDef( name="engineId", type="integer" ))
@FilterDef(name="filterYear", parameters= {
        @ParamDef( name="minYear", type="integer" ),
        @ParamDef( name="maxYear", type="integer" )
})
@FilterDef(name="filterKm", parameters= {
        @ParamDef( name="minKm", type="integer" ),
        @ParamDef( name="maxKm", type="integer" )
})
@FilterDef(name="filterKm", parameters= {
        @ParamDef( name="minKm", type="integer" ),
        @ParamDef( name="maxKm", type="integer" )
})

@Filters({
        @Filter(name="filterCreated", condition="current_date - :daysAgo <= DATE(created)"),
        @Filter(name="filterPrice", condition=":minPrice <= price and :maxPrice >= price"),
        @Filter(name="filterYear", condition=":minYear <= year and :maxYear >= year"),
        @Filter(name="filterKm", condition=":minKm <= km and :maxKm >= km"),
        @Filter(name="filterMake", condition="model_id in (select m.id from models m where m.make_id =:makeId)"),
        @Filter(name="filterModel", condition="model_id =:modelId"),
        @Filter(name="filterEngine", condition="engine_id =:engineId"),
        })
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());
    @Column(nullable = false)
    private boolean sold;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private Body body;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ad_id")
    private List<Photo> photos = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @Column(nullable = false)
    private float engineDisplacement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, name="price")
    private int price;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int km;

    public Ad(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Ad() {
    }

    public Ad(int id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public float getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(float engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
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
        return "Ad{" +
                "id=" + id
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", sold=" + sold
                + ", model=" + model
                + ", body=" + body
                + ", engine=" + engine
                + ", displ" + engineDisplacement
                + ", transmission=" + transmission
                + ", color=" + color
                + ", user=" + user
                + ", price=" + price
                + ", year=" + year
                + ", km=" + km
                + ", photos=" + photos
                + '}';
    }
}
