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
    public User signUp(@RequestBody User user) {
        return service.createUser(user);
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

}
