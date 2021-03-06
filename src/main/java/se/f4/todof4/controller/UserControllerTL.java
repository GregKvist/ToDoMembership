package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;


@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class UserControllerTL {

    @Autowired
    private UserService service;


    @GetMapping("/signup")
    public String registerUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("title", "Sign up");
        return "addUsers";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("listUsers", service.getUsers());
        return "users";
    }

    @GetMapping("/delete")
    public String deleteUsers(Model model) {
        model.addAttribute("deleteUsers", service.deleteUsers());
        service.deleteUsers();
        return "delete";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(Model model, @PathVariable("id") String stringId) {
        System.out.println("stringId = " + stringId);
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            return "error";
        }
        model.addAttribute("deleteUser", service.deleteUser(id));
        return"redirect:/users";
    }

   @GetMapping("/addUsers")
    public String addUsers(Model model){
       User user = new User();
       model.addAttribute("user", user);
       model.addAttribute("title", "Add a user");
       return "addUsers";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
       //save user to database
        if(service.createUser(user)==null){
            return "redirect:/nonvalid-email";
        }
        return"redirect:/users";
    }
    

    @GetMapping("/nonvalid-email")
    public String nonValidEmail(){
        return "nonvalid-email.html";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFOrmForUpdate(@PathVariable (value= "id") int id, Model model){

            User user = service.getUserById(id);
            model.addAttribute("user", user);
            return "update_user";
    }
}
