package se.f4.todof4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Todof4Application {

    public static void main(String[] args) {
        System.out.println("main is called");
        SpringApplication.run(Todof4Application.class, args);
    }

    @GetMapping("/todo")
    public	String sayHello(@RequestParam(value = "myName", defaultValue = "GetMapping") String name){
        return String.format("Hello %s! Welcome to the Todo application!", name);

    }
}
