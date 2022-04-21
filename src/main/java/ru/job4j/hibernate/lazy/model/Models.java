package ru.job4j.hibernate.lazy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "model")
public class Models {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brand;

    public Models() {
    }

    public static Models of(String name, Brands brand) {
        Models model = new Models();
        model.name = name;
        model.brand = brand;
        return model;
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

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Models model = (Models) o;
        return Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Models{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", brand=" + brand
                + '}';
    }
}
