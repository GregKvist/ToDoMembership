package se.f4.todof4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.f4.todof4.service.UserService;

@SpringBootApplication
@RestController
public class Todof4Application {

    public static void main(String[] args) {
        System.out.println("Application started!");
        SpringApplication.run(Todof4Application.class, args);

    }

    @GetMapping("/todo")
    public	String sayHello(@RequestParam(value = "myName", defaultValue = "GetMapping") String name){
        return String.format("Hello %s! Welcome to the Todo application!", name);

    }

}