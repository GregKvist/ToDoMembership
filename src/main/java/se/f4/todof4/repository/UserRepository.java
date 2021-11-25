package se.f4.todof4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import se.f4.todof4.entity.User;

//@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
