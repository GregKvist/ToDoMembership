package se.f4.todof4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.f4.todof4.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findUserByEmail(String email);
     User findByEmail(String email);
}
