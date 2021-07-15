package com.glacier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author glacier
 * @version 1.0
 * date 2019-12-23 10:31
 */
@EnableConfigurationProperties
@SpringBootApplication
public class EvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvoApplication.class, args);
    }

}
