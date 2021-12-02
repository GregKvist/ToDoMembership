package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService service;

   // @PostMapping("/signup")
    //public User signUp(@RequestBody User user) {
      //  return service.createUser(user);
    //}

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.OK);
    }
    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        try{
            User body = service.getUserById(userId);
            HttpHeaders header = new HttpHeaders();
            header.add("description", "get user by id");

            return ResponseEntity.status(HttpStatus.OK).headers(header).body(body);


        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/users")
    public @ResponseBody
    ResponseEntity<List> getUsers() {
        return new ResponseEntity<List>(service.getUsers(), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteUser(@PathVariable("id") String stringId) {
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (service.deleteUser(id).isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete_all")
    public @ResponseBody
    ResponseEntity<Void> deleteUsers() {
        service.deleteUsers();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/update-by-id/{id}")
    public @ResponseBody
    ResponseEntity<User> updateUser(@PathVariable("id") Integer id,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String password) {

        HttpHeaders header = new HttpHeaders();
        header.add("description", "Update user");


        if (service.updateUser(id,name, email, password)) {
            User body = service.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
