package com.fc8.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Fc8ProjectBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fc8ProjectBoardApplication.class, args);
    }

}
