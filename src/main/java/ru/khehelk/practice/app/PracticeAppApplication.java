package ru.khehelk.practice.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "ru.khehelk.practice")
@SpringBootApplication(scanBasePackages = "ru.khehelk.practice")
public class PracticeAppApplication {

    static void main(String[] args) {
        SpringApplication.run(PracticeAppApplication.class, args);
    }

}
