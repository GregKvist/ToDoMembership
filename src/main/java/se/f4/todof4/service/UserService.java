package se.f4.todof4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.f4.todof4.entity.User;
import se.f4.todof4.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    private void createDefaultUser() {
        User exist = repository.findByEmail("admin@g4.com");
        if(exist == null) {
            System.out.println("------- Creating a default user ------");
            User user = new User("admin", "admin@g4.com", "password");
            user = encodedPassword(user);
            repository.save(user);
        }else {
            System.out.println("------- Default user exist ------");
            System.out.println("username: admin@g4.com\n password: password");
        }
    }

    public User encodedPassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }


    public User createUser(User user) {
       if(createUserEmailCheck(user)) {
           user = encodedPassword(user);
           return repository.save(user);
        }else {
            return null;
        }
    }

    //e-mail check to avoid signing up with already existing email.
    public boolean createUserEmailCheck(User user){
        //            Check to see if email is already in use by other user
        Optional<User> userOptional = repository.findUserByEmail(user.getEmail());

        User checkUser = getUserById(user.getId());
        if(checkUser == null) {
            if (userOptional.isPresent()) {
                System.out.println("email taken");
                return false;
            }else{
                return true;
            }

        } else if(userOptional.isPresent() && !(checkUser.getEmail().equals(user.getEmail()))) {
            System.out.println("email taken");
            return false;
        }else{
            return true;
        }
        }


    public List<User> getUsers() {
        return repository.findAll();
    }

    public List<User> deleteUsers() {
        List<User> usersToDelete = repository.findAll();
        repository.deleteAll();
        return usersToDelete;
    }

    public User getUserById(int id){
        if(id == 0) {
        return null;
        }else {
            User user = repository.findById(id).get();
                return user;
        }
    }

    public Optional<User> deleteUser(int id) {
        Optional<User> isFound = repository.findById(id);
        if (isFound.isPresent()) {
            // The id exists
            repository.deleteById(id);
        }
        return isFound;
    }

    //update user by ID
    @Transactional
    public boolean updateUser(Integer id, String name , String email, String password) {

        // Check if passed id from client exist
        Optional<User> userFound = repository.findById(id);
        if (userFound.isPresent()) {
            // The id exists, proceeding with getting the correct user to update
            User user = repository.getById(id);

            // Check if a name has been passed from client (longer than null/0) and that the passed name
            // is NOT equal to existing name. If so: set username to the passed name from client.
            if (name != null && name.length() > 0 &&
                !Objects.equals(user.getName(), name)) {
                user.setName(name);
            }

            // Check if passed email from client is valid
            if (email != null && email.length() > 0 &&
                !Objects.equals(user.getEmail(), email)) {

//            Check to see if email is already in use by other user
                Optional<User> userOptional = repository.findUserByEmail(email);
                if (userOptional.isPresent()) {
                    System.out.println("email taken");
                    return false;
                }
                // Set the new email
                user.setEmail(email);

            }// End brackets for e-mail control

            // Check if passed password from client is valid
            if (password != null && password.length() > 0 &&
                !Objects.equals(user.getPassword(), password)) {

                // Set the new password
                user.setPassword(password);

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                }// End brackets for password control

            return true;
        }else{
            return false;
        }
    }// End bracket for updateUser()


}//End bracket for UserService
