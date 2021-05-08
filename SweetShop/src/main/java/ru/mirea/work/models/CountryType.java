package ru.mirea.work.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "types_countries")
public class CountryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="types_id")
    private int typesId;
    @Column(name="countries_id")
    private int countriesId;

    @Override
    public String toString() {
        return "CountryType{" +
                "id=" + id +
                ", typesId=" + typesId +
                ", countriesId=" + countriesId +
                '}';
    }
}
