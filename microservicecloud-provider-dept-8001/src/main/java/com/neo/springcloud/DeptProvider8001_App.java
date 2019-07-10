package com.neo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@AutoConfigureJdbc
public class DeptProvider8001_App {

    public static void main(String[] args) {

        SpringApplication.run(DeptProvider8001_App.class,args);
    }
}