package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/tl")
public class UserControllerTL {

    @Autowired
    private UserService service;

@PostMapping("/signup")
public User signUp(@RequestBody User user) {return service.createUser(user);}





    @GetMapping("/users")
    public @ResponseBody
    ResponseEntity<List> getUsers() {
        return new ResponseEntity<List>(service.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/delete_all")
    public @ResponseBody ResponseEntity<Void> deleteUsers() {
        service.deleteUsers();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
