package se.f4.todof4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Todof4Application {

    public static void main(String[] args) {
        System.out.println("Application started);
        SpringApplication.run(Todof4Application.class, args);
    }

    @GetMapping("/hello")
    public	String sayHello(@RequestParam(value = "myName", defaultValue = "Testing GetMapping") String name){
        return String.format("Hello hello hello %s!", name);
    }
}
