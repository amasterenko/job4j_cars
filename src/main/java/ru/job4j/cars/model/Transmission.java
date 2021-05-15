package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;
/**
 * Class represents car's transmission type.
 * For more information on the relationship of the entity, see db/cars_scheme_png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */
@Entity
@Table(name = "transmissions")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;

    public Transmission(String name) {
        this.name = name;
    }

    public Transmission() {
    }

    public Transmission(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transmission that = (Transmission) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transmission{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
