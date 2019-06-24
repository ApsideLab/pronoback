package com.apside.prono.pronoPlayer;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.*;
import com.apside.prono.service.PronoPlayerService;
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
public class PronoPlayerIntegrationTest {
    private String url = "http://localhost:8086/pronos/pronoPlayer/";
    private Long value = 3L;
    private String lastName = "Monkey D.";
    private String firstName = "Luffy";
    private String mail = "one@piece.jp";
    private Date subscribeDate = new Date();
    private String label = "testPronoPlayer";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PronoPlayerService pronoPlayerService;
    private String urlPlayer = "http://localhost:8086/pronos/players/";
    private String urlProno = "http://localhost:8086/pronos/prono/";
    private String urlActor = "http://localhost:8086/pronos/actors/";
    private String urlType = "http://localhost:8086/pronos/types/";
    private String urlEvent = "http://localhost:8086/pronos/evenements/";


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        PronoPlayerEntity pronoPlayerEntity = new PronoPlayerEntity();
        pronoPlayerEntity.setType(initializeType());
        pronoPlayerEntity.setActor(initializeActor());
        pronoPlayerEntity.setEvent(initializaEvent());
        pronoPlayerEntity.setPlayer(initializePlayerEntity());
        pronoPlayerEntity.setPronoDetail(initializeProno());
        pronoPlayerEntity.setDate(new Date());

        ResponseEntity<String> response = restTemplate.postForEntity(url, pronoPlayerEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        String newLabel = "trololotestPronoPlayer";

        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(newLabel);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlActor, actorEntity, String.class);
        actorEntity.setId(getId(responseEntity));


        long id = initialize();
        PronoPlayerEntity pronoPlayer = pronoPlayerService.getPronoPlayer(id);

        Long idActorOld = pronoPlayer.getActor().getId();
        pronoPlayer.setActor(actorEntity);

        restTemplate.put(url, pronoPlayer);

        //Verify request succeed
        assertThat(pronoPlayerService.getPronoPlayer(id) != null).isTrue();
        assertThat(restTemplate.getForObject(url + id, PronoPlayerEntity.class).getActor().getLabel().equals(newLabel)).isTrue();

        restTemplate.delete(urlActor + idActorOld);
        clean(id);
    }

    @Test
    public void getTest() {
        long id = initialize();

        //Verification du code retour
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        PronoPlayerEntity pronoPlayerEntity =  pronoPlayerService.getPronoPlayer(id);
        clean(id);
        assertThat(pronoPlayerEntity.getId() == id && pronoPlayerEntity.getId().equals(id)).isTrue();
        assertThat(pronoPlayerEntity.getPlayer().getMail().equals(mail)).isTrue();
        assertThat(pronoPlayerEntity.getEvent().getLibelle().equals(label)).isTrue();
        assertThat(pronoPlayerEntity.getActor().getLabel().equals(label)).isTrue();
        assertThat(pronoPlayerEntity.getType().getLabel().equals(label)).isTrue();
        assertThat(pronoPlayerEntity.getPronoDetail().getScore().equals(String.valueOf(value))).isTrue();
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        long id = initialize();
        PronoPlayerEntity pronoPlayerEntity = pronoPlayerService.getPronoPlayer(id);
        restTemplate.delete(url + id);
        restTemplate.delete(urlActor + pronoPlayerEntity.getActor().getId());
        restTemplate.delete(urlType + pronoPlayerEntity.getType().getId());
        restTemplate.delete(urlEvent + pronoPlayerEntity.getEvent().getId());
        restTemplate.delete(urlProno + pronoPlayerEntity.getPronoDetail().getId());
        restTemplate.delete(urlPlayer + pronoPlayerEntity.getPlayer().getId());

        try {
            pronoPlayerService.getPronoPlayer(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        PronoPlayerEntity pronoPlayerEntity = new PronoPlayerEntity();
        pronoPlayerEntity.setType(initializeType());
        pronoPlayerEntity.setActor(initializeActor());
        pronoPlayerEntity.setEvent(initializaEvent());
        pronoPlayerEntity.setPlayer(initializePlayerEntity());
        pronoPlayerEntity.setPronoDetail(initializeProno());
        pronoPlayerEntity.setDate(new Date());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, pronoPlayerEntity, String.class);
        return getId(responseEntity);
    }

    public ActorEntity initializeActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlActor, actorEntity, String.class);
        actorEntity.setId(getId(responseEntity));
        return actorEntity;
    }

    public TypeEntity initializeType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlType, typeEntity, String.class);
        typeEntity.setId(getId(responseEntity));
        return typeEntity;
    }

    public EvenementEntity initializaEvent() {
        EvenementEntity eventEntity = new EvenementEntity();
        eventEntity.setLibelle(label);
        eventEntity.setCoeff(value);
        eventEntity.setDateEvenement(new Date());
        eventEntity.setDateFermeture(new Date());
        eventEntity.setDateOuverture(new Date());
        eventEntity.setId(1L);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlEvent, eventEntity, String.class);
        eventEntity.setId(getId(responseEntity));
        return eventEntity;
    }

    public PlayerEntity initializePlayerEntity() {
        PlayerEntity player = new PlayerEntity();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setMail(mail);
        player.setSubscribeDate(subscribeDate);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlPlayer, player, String.class);
        player.setId(getId(responseEntity));
        return player;
    }

    public PronoEntity initializeProno() {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setScore(String.valueOf(value));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlProno, pronoEntity, String.class);
        pronoEntity.setId(getId(responseEntity));
        return pronoEntity;
    }

    public void clean(long id) {
        if (pronoPlayerService.getPronoPlayer(id) != null) {
            PronoPlayerEntity pronoPlayerEntity = pronoPlayerService.getPronoPlayer(id);
            try {
                restTemplate.delete(url + id);
                restTemplate.delete(urlActor + pronoPlayerEntity.getActor().getId());
                restTemplate.delete(urlType + pronoPlayerEntity.getType().getId());
                restTemplate.delete(urlEvent + pronoPlayerEntity.getEvent().getId());
                restTemplate.delete(urlProno + pronoPlayerEntity.getPronoDetail().getId());
                restTemplate.delete(urlPlayer + pronoPlayerEntity.getPlayer().getId());
            }catch (Exception e){
                throw new NullPointerException();
            }

        }
    }

    public long getId(ResponseEntity<String> responseEntity) {
        String result = responseEntity.toString();
        if (result.contains("<500,")) {//Don't initialize
            throw new NullPointerException();
        }
        result = result.substring(result.indexOf("\"id\":") + 5, result.indexOf(",\""));
        result = result.replace("\"", "");
        return Long.parseLong(result);
    }
}
