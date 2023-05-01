package com.hanghae.sharemythingz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShareMyThingZApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareMyThingZApplication.class, args);
    }

}
