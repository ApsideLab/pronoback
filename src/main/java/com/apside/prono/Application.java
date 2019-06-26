package com.apside.prono;

import com.apside.prono.model.ActorEntity;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.service.ActorService;
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
    CommandLineRunner init(PlayerService playerService, ActorService actorService) {
        return args -> {
            if (playerService.getAll().size() == 0) {
                for (int i = 0; i < 20; i++) {
                    PlayerEntity player = new PlayerEntity();
                    player.setSubscribeDate(new Date());
                    player.setMail("toto" + i + "@domaine.fr");
                    player.setLastName("toto" + i);
                    player.setFirstName("dupont" + i);
                    playerService.createPlayer(player);
                }
            }
            if (actorService.getAll().size() == 0) {
                String[] countryName = {"France", "Italie", "Corée du Sud", "Portugal", "Angleterre", "Ireland", "Island", "Espagne", "Grece", "Norvege", "Pays-Bas"};
                for (String s : countryName) {
                    ActorEntity actorEntity = new ActorEntity();
                    actorEntity.setLabel(s);
                    actorService.createActor(actorEntity);
                }
            }
        };
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/pronos/players").allowedOrigins("http://localhost:4200");
                registry.addMapping("/pronos/actors").allowedOrigins("http://localhost:4200");
            }
        };
    }
}