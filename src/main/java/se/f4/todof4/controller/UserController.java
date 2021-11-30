package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        return service.createUser(user);
    }


    @GetMapping("/users")
    public @ResponseBody
    ResponseEntity<List> getUsers() {
        return new ResponseEntity<List>(service.getUsers(), HttpStatus.OK);
    }

//    @PutMapping("/updateUser")
//    public String updateUser(@RequestBody User user){
//        return service.updateUser(user);
//    }

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

    @PutMapping("/update_by_id/{id}")
    public @ResponseBody
    ResponseEntity<Void> updateUser(@PathVariable("id") Integer id,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String password) {

        if (service.updateUser(id,name, email, password)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
