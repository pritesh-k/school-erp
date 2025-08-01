package com.schoolerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.schoolerp.repository")
@EntityScan(basePackages = "com.schoolerp.entity")
public class SchoolErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolErpApplication.class, args);
    }
}
