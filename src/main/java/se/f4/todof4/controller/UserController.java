package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService service;

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
            HttpHeaders header = new HttpHeaders();
            header.add("Error message", "User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers( header).build();
        }


    }

    // Ciccis new PostMapping for sign up, not complete yet
    @PostMapping("/signup-email-check")
    public @ResponseBody
    ResponseEntity<User> createUserWithEmailCheck(@RequestBody User user) {

        HttpHeaders header = new HttpHeaders();
        header.add("description", "Register user");


        if (service.createUserEmailCheck(user)) {
            User body = service.getUserById(user.getId());
            return ResponseEntity.status(HttpStatus.OK).headers(header).build();
        } else {
            HttpHeaders errorHeader = new HttpHeaders();
            header.add("Error message", "Given e-mail address is not valid.");// Wrong? Maybe better with: errorHeader.add(...bla bla)???
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(errorHeader).build();
        }
    }


    @GetMapping(value = "/users", produces = {"application/json","application/xml"})
    public @ResponseBody
    ResponseEntity<List> getUsers() {
        return new ResponseEntity<List>(service.getUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users-xml", produces = {"application/xml"})
    public @ResponseBody
    ResponseEntity<List> getUsersXml() {
        return new ResponseEntity<List>(service.getUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteUser(@PathVariable("id") String stringId) {
        int id;
        HttpHeaders httpHeader = new HttpHeaders();
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            httpHeader.add("Error message", "the supplied user id = '" + stringId + "' is not parseable as an integer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeader).build();
        }

        if (service.deleteUser(id).isPresent()) {
            httpHeader.add("description", "the user id = " + stringId + " is successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeader).build();
        } else {
            httpHeader.add("Error message", "the supplied user id = " + stringId + " does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeader).build();
        }
    }

    @DeleteMapping("/delete_all")
    public @ResponseBody
    ResponseEntity<Void> deleteUsers() {
        HttpHeaders httpHeader = new HttpHeaders();
        service.deleteUsers();
        httpHeader.add("description", "all users have successfully been deleted");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeader).build();
    }

    @PutMapping(value = "/update-by-id/{id}", produces = {"application/json", "application/xml"})
    public @ResponseBody
    ResponseEntity<User> updateUser(@PathVariable("id") Integer id,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String password) {

        // Create header for ResponseEntity
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
