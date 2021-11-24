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

    public String updateUser(User user){
        // Check first if user exist, if yes: change userFound to "true" and update user.
        // If not, userFound will stay "false" and trigger an error message
        boolean userFound = false;

        for (User currentUser: getUsers()) {
            if(currentUser.getId() == user.getId()){
                userFound = true;
                currentUser.setName(user.getName());
                currentUser.setEmail(user.getEmail());
                currentUser.setPassword(user.getPassword());
                createUser(currentUser);
            }
        }

        // Check final status of userFound
        if(!userFound){
            return "No such user exist, check entered id number.";
        }else {
            return "User updated successfully.";
        }
    }

}
