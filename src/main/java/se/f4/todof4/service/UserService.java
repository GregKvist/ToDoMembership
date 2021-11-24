package se.f4.todof4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.f4.todof4.entity.User;
import se.f4.todof4.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public void deleteUsers() {
        repository.deleteAll();
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

}
