package com.apside.prono;

import com.apside.prono.model.ActorEntity;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.service.ActorService;
import com.apside.prono.service.PlayerService;
import com.apside.prono.service.ScaleService;
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
    CommandLineRunner init(PlayerService playerService, ActorService actorService, ScaleService scaleService) {
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
//            if (scaleService.getAll().size() == 0) {
//                for (int i = 0; i < 20; i++) {
//                    ScaleEntity scaleEntity = new ScaleEntity();
//                    scaleEntity.setLabel("toto"+i);
//                    scaleEntity.setDateDebutValidite(new Date());
//                    scaleEntity.setDateFinValidite(new Date());
//                    scaleEntity.setPtsBonResultat(1);
//                    scaleEntity.setPtsBonusEcartButs(2);
//                    scaleEntity.setPtsBonusDeuxScoresExacts(3);
//                    scaleEntity.setPtsBonusUnScoreExactResultatOK(4);
//                    scaleEntity.setPtsBonusUnScoreExactResultatKO(5);
//                    scaleService.createScale(scaleEntity);
//                }
//            }
        };
    }
}