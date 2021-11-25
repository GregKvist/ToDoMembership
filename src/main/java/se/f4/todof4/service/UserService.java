package se.f4.todof4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.f4.todof4.entity.User;
import se.f4.todof4.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public boolean deleteUser(int id) {
        Optional<User> isFound = repository.findById(id);

        if (isFound.isPresent()) {
            // The id exists
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

//    Enklaste updateUser utan HTTP status, låt stå/Cicci
//    public String updateUser(User user){
//        // Check first if user exist, if yes: change userFound to "true" and update user.
//        // If not, userFound will stay "false" and trigger an error message
//        boolean userFound = false;
//
//        for (User currentUser: getUsers()) {
//            if(currentUser.getId() == user.getId()){
//                userFound = true;
//                currentUser.setName(user.getName());
//                currentUser.setEmail(user.getEmail());
//                currentUser.setPassword(user.getPassword());
//
//                // Invoke create user to write the updates to database
//                createUser(currentUser);
//            }
//        }
//
//        // Check final status of userFound
//        if(!userFound){
//            return "No such user exist, check entered id number.";
//        }else {
//            return "User updated successfully.";
//        }
//    }

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

                }// End brackets for password control

            return true;
        }else{
            return false;
        }
    }// End bracket for updateUser()


}//End bracket for UserService
