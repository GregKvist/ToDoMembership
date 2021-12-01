package se.f4.todof4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.f4.todof4.entity.User;
import se.f4.todof4.service.UserService;

import java.util.List;

@Controller
public class UserControllerTL {

    @Autowired
    private UserService service;


    @GetMapping("/signup")
    public String registerUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
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

        return "delete-user";
    }

   @GetMapping("/addUsers")
    public String addUsers(Model model){
       User user = new User();
       model.addAttribute("user", user);
       return "addUsers";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
       //save user to database
        service.createUser(user);
        return"redirect:/users";
    }

    // Testar POST med email check/Cicci
    @PostMapping("/saveUserEmailCheck")
    public String saveUserWithEmailCheck(@ModelAttribute("user") User user){
        //save user to database
        if(service.createUserEmailCheck(user)){
        return"redirect:/users";
        }else{
            return "redirect:/nonvalid-email";
        }
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
