package ru.job4j.hibernate.lazy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Models> models = new ArrayList<>();

    public static Brands of(String name) {
        Brands brand = new Brands();
        brand.name = name;
        return brand;
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

    public List<Models> getModels() {
        return models;
    }

    public void setModels(List<Models> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brands brand = (Brands) o;
        return Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void add(Models model) {
        this.models.add(model);
    }

    @Override
    public String toString() {
        return "Brand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
