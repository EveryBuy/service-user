package ua.everybuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class UserDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDataServiceApplication.class, args);
    }

}
