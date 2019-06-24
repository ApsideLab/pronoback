package com.apside.prono.players;

import com.apside.prono.model.PlayerEntity;
import com.apside.prono.service.PlayerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayersIntegrationTest {
    String url = "http://localhost:8086/pronos/players/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlayerService playerService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        String lastName = "Monkey D.";
        String firstName = "Luffy";
        String mail = "one@piece.jp";
        Date subscribeDate = new Date();

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setFirstName(firstName);
        playerEntity.setLastName(lastName);
        playerEntity.setMail(mail);
        playerEntity.setSubscribeDate(subscribeDate);

        ResponseEntity<String> response = restTemplate.postForEntity(url, playerEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(mail);
    }

    @Test
    public void putTest() {
        initialize("Kazuma", "Kiryu", "yakuza@kiwami.jp");

        String lastName = "Kiryu-chan";
        String firstName = "Kazuma-Kun";
        String mail = "yakuza@6.jp";

        Date newDate = new Date();
        newDate.setTime(0);

        PlayerEntity playerEntity = playerService.find("yakuza@kiwami.jp");
        playerEntity.setLastName(lastName);
        playerEntity.setFirstName(firstName);
        playerEntity.setSubscribeDate(newDate);
        playerEntity.setMail(mail);
        restTemplate.put(url, playerEntity);

        //Verify request succeed
        assertThat(playerService.find(mail) != null).isTrue();
        clean(mail);
    }

    @Test
    public void getTest() {
        initialize("Phoenix", "Wright", "ace@attorney.jp");

        //Verification du code retour
        long id = playerService.find("ace@attorney.jp").getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        PlayerEntity playerEntity = restTemplate.getForObject(url + id, PlayerEntity.class);
        assertThat(playerEntity.getId() == id && playerEntity.getMail().equals("ace@attorney.jp")).isTrue();
        clean("ace@attorney.jp");
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        initialize("Dead", "Pool", "capcom@marvel.jp");
        long id = playerService.find("capcom@marvel.jp").getId();
        restTemplate.delete(url + id);

        assertThat(playerService.find("capcom@marvel.jp") == null).isTrue();
    }

    public void initialize(String firstName, String lastName, String mail) {
        if (playerService.find(mail) == null) {
            Date subscribeDate = new Date();

            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setFirstName(firstName);
            playerEntity.setLastName(lastName);
            playerEntity.setMail(mail);
            playerEntity.setSubscribeDate(subscribeDate);
            restTemplate.postForEntity(url, playerEntity, String.class);
        }
    }

    public void clean(String mail) {
        if (playerService.find(mail) != null) {
            long id = playerService.find(mail).getId();
            restTemplate.delete(url + id);
        }
    }
}
