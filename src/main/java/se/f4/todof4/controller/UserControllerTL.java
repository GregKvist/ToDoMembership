package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

@Controller

public class UserControllerTL {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("listUsers", service.getUsers());
        return "users";
    }
/*
    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }
*/
    @DeleteMapping("/delete_all")
    public @ResponseBody
    ResponseEntity<Void> deleteUsers() {
        service.deleteUsers();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
   /* @GetMapping("/users")
    public String viewHomePage (Model model){
    model.addAttribute("listUser", service.getUsers());
    return "index";
    }*/
   @GetMapping("/addUsers")
    public String addUsers(Model model){
       User user = new User();
       model.addAttribute("user", user);
       return "addUsers";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
       //save user to database
        service.saveUser(user);
        return"redirect:/users";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFOrmForUpdate(@PathVariable (value= "id") int id, Model model){

            User user = service.getUserById(id);
            model.addAttribute("user", user);
            return "update_user";
    }






}
