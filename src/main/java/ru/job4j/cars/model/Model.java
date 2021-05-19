package ru.job4j.cars.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import java.util.Objects;
/**
 * Class represents car's model.
 * For more information about relationships of the entity, see db/cars_scheme.png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */
@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "make_id",  foreignKey = @ForeignKey(name = "MAKE_ID_FK"))
    private Make make;

    public Model(String name) {
        this.name = name;
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Model(int id) {
        this.id = id;
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Model{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", make=" + make
                + '}';
    }
}
