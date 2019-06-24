package com.apside.prono.actorLinkEvent;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.service.ActorLinkEventService;
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
public class ActorLinkEventIntegrationTest {
    private String url = "http://localhost:8086/pronos/actorLinkEvent/";
    private String urlActor = "http://localhost:8086/pronos/actors/";
    private String urlEvent = "http://localhost:8086/pronos/evenements/";
    private Long value = 3L;
    private String label = "testActorLinkEvent";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActorLinkEventService actorLinkEventService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        ActorLinkEventEntity actorLinkEventEntity = new ActorLinkEventEntity();
        actorLinkEventEntity.setActor(initializeActor());
        actorLinkEventEntity.setEvent(initializeEvent());

        ResponseEntity<String> response = restTemplate.postForEntity(url, actorLinkEventEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        String newLabel = "trololotestActorLinkEvent";
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(newLabel);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlActor, actorEntity, String.class);
        actorEntity.setId(getId(responseEntity));


        long id = initialize();
        ActorLinkEventEntity actorLinkEventEntity = actorLinkEventService.getActorLinkEvent(id);
        Long idActorOld = actorLinkEventEntity.getActor().getId();
        actorLinkEventEntity.setActor(actorEntity);

        restTemplate.put(url, actorLinkEventEntity);

        //Verify request succeed
        assertThat(actorLinkEventService.getActorLinkEvent(id) != null).isTrue();
        assertThat(restTemplate.getForObject(url + id, ActorLinkEventEntity.class).getActor().getLabel().equals(newLabel)).isTrue();

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
        ActorLinkEventEntity actorLinkEventEntity = restTemplate.getForObject(url + id, ActorLinkEventEntity.class);
        assertThat(actorLinkEventEntity.getId() == id && actorLinkEventEntity.getId().equals(id)).isTrue();
        assertThat(actorLinkEventEntity.getActor().getLabel().equals(label)).isTrue();
        assertThat(actorLinkEventEntity.getEvent().getLibelle().equals(label)).isTrue();

        clean(id);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        long id = initialize();

        ActorLinkEventEntity actorLinkEventEntity = actorLinkEventService.getActorLinkEvent(id);
        ActorEntity actorEntity = actorLinkEventEntity.getActor();
        EvenementEntity evenementEntity = actorLinkEventEntity.getEvent();
        restTemplate.delete(url + id);
        restTemplate.delete(urlActor + actorEntity.getId());
        restTemplate.delete(urlEvent + evenementEntity.getId());
        try {
            actorLinkEventService.getActorLinkEvent(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        ActorLinkEventEntity actorLinkEventEntity = new ActorLinkEventEntity();
        actorLinkEventEntity.setActor(initializeActor());
        actorLinkEventEntity.setEvent(initializeEvent());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, actorLinkEventEntity, String.class);
        return getId(responseEntity);
    }

    public ActorEntity initializeActor() {
        ActorEntity actor = new ActorEntity();
        actor.setLabel(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlActor, actor, String.class);
        actor.setId(getId(responseEntity));
        return actor;
    }

    public EvenementEntity initializeEvent() {
        EvenementEntity eventEntity = new EvenementEntity();
        eventEntity.setLibelle(label);
        eventEntity.setCoeff(value);
        eventEntity.setDateEvenement(new Date());
        eventEntity.setDateFermeture(new Date());
        eventEntity.setDateOuverture(new Date());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlEvent, eventEntity, String.class);
        eventEntity.setId(getId(responseEntity));
        return eventEntity;
    }

    public void clean(long id) {
        if (actorLinkEventService.getActorLinkEvent(id) != null) {
            ActorLinkEventEntity actorLinkEventEntity = actorLinkEventService.getActorLinkEvent(id);
            ActorEntity actorEntity = actorLinkEventEntity.getActor();
            EvenementEntity evenementEntity = actorLinkEventEntity.getEvent();
            try {
                restTemplate.delete(url + id);
                restTemplate.delete(urlActor + actorEntity.getId());
                restTemplate.delete(urlEvent + evenementEntity.getId());
            } catch (Exception e) {
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
