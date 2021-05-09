package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer> {
    Country findById(int id);
    Long deleteById(int id);
}
