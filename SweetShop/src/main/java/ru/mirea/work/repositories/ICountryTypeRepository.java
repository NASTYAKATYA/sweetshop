package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.CountryType;

import java.util.List;

@Repository
public interface ICountryTypeRepository extends JpaRepository<CountryType, Integer> {
    List<CountryType> findAllByTypesId(int typesId);
}
