package se.f4.todof4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Todof4Application {

    public static void main(String[] args) {
        System.out.println("main is called");
        SpringApplication.run(Todof4Application.class, args);
    }
}
