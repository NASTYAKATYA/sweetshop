package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.Purchase;

import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findByUserIdAndProductId(int userId, int productId);
    List<Purchase> findAllByUserId(int userId);
    Purchase findById(int id);
    Long deleteById(int id);
    @Transactional
    Long deleteAllByUserId(int userId);
}
