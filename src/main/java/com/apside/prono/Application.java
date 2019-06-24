package com.apside.prono;

import com.apside.prono.model.PlayerEntity;
import com.apside.prono.service.PlayerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(PlayerService playerService) {
        if(playerService.getAll().size() ==0) {
            return args -> {
                for (int i = 0; i < 5; i++) {
                    PlayerEntity player = new PlayerEntity();
                    player.setSubscribeDate(new Date());
                    player.setMail("toto" + i + "@domaine.fr");
                    player.setLastName("toto" + i);
                    player.setFirstName("dupont" + i);
                    playerService.createPlayer(player);
                }
            };
        }
        return null;
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/pronos/players").allowedOrigins("http://localhost:4200");
            }
        };
    }
}