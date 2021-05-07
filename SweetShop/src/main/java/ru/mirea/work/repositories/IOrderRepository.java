package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
   Order findById(int id);
}
