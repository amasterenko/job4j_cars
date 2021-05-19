package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;
/**
 * Class represents car's make.
 * For more information about relationships of the entity, see db/cars_scheme.png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */
@Entity
@Table(name = "makes")
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;

    public Make(String name) {
        this.name = name;
    }

    public Make(int id) {
        this.id = id;
    }

    public Make() {
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
        Make make = (Make) o;
        return id == make.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Make{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
