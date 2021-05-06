package ru.mirea.work.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="price")
    private int price;
    @Column(name="weight")
    private int weight;
    @Column(name="description")
    private String description;
    @Column(name="types_id")
    private int types_id;
    @Column(name="countries_id")
    private int countries_id;

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", types_id='" + types_id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                ", description=" + description +
                ", countries_id=" + countries_id +
                '}';
    }
}
