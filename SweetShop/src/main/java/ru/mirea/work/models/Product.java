package ru.mirea.work.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="types_id")
    private int typesId;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="price")
    private int price;
    @Column(name="weight")
    private int weight;
    @Column(name="description")
    private String description;
    @Column(name="countries_id")
    private int countriesId;

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", types_id='" + typesId + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                ", description=" + description +
                ", countries_id=" + countriesId +
                '}';
    }
}
