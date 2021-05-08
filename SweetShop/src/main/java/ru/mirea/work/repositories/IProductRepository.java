package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Product;

import java.util.List;


@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByTypesId(int typesId);
    List<Product> findAllByTypesIdAndCountriesId(int typesId, int countriesId);
    Product findById(int id);
}
