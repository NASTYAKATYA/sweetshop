package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.User;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
