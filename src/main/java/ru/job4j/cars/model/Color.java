package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;
/**
 * Class represents car's body color.
 * For more information about relationships of the entity, see db/cars_scheme.png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */
@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;

    public Color(String name) {
        this.name = name;
    }

    public Color() {
    }

    public Color(int id) {
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
        Color color = (Color) o;
        return id == color.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Color{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
