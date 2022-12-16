package com.epam;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


//@SpringBootApplication
@Configuration
@ComponentScan({"com.epam"})
public class Main extends WebMvcConfigurerAdapter {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }
}
