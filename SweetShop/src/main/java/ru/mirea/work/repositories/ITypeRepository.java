package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Type;


@Repository
public interface ITypeRepository extends JpaRepository<Type, Integer> {
    Type findById(int id);
}
