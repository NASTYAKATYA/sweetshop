package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Product;

import java.util.List;


@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
   Product findById(int id);
   List<Product> findAllByTypeId(int typeId);
}
