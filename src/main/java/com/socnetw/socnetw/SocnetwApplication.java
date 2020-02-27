package com.socnetw.socnetw;

import com.socnetw.socnetw.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EntityScan("com.socnetw.socnetw.model")
public class SocnetwApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SocnetwApplication.class, args);
    }
}
