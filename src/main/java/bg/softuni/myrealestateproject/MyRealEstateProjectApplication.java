package bg.softuni.myrealestateproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyRealEstateProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRealEstateProjectApplication.class, args);
    }

}
