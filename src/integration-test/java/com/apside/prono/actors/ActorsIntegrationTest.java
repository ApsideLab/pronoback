package com.apside.prono.actors;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.service.ActorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ActorsIntegrationTest {
    private String url = "http://localhost:8086/pronos/actors/";
    private String label = "France";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActorService actorService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);

        ResponseEntity<String> response = restTemplate.postForEntity(url, actorEntity, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(label);
    }

    @Test
    public void putTest() {
        String newLabel = "Italie";
        initialize(label);
        ActorEntity actorEntity = actorService.find(label);
        actorEntity.setLabel(newLabel);

        restTemplate.put(url, actorEntity);

        //Verify request succeed
        assertThat(actorService.find(newLabel) != null).isTrue();
        clean(newLabel);
    }

    @Test
    public void getTest() {
        initialize(label);


        long id = actorService.find(label).getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();

        ActorEntity actorEntity = restTemplate.getForObject(url + id, ActorEntity.class);
        assertThat(actorEntity.getId() == id && actorEntity.getLabel().equals(label)).isTrue();
        clean(label);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        initialize(label);
        long id = actorService.find(label).getId();
        restTemplate.delete(url + id);

        try {
            actorService.getActor(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public void initialize(String label) {
        if (actorService.find(label) == null) {
            ActorEntity actorEntity = new ActorEntity();
            actorEntity.setLabel(label);
            restTemplate.postForEntity(url, actorEntity, String.class);
        }
    }

    public void clean(String label) {
        if (actorService.find(label) != null) {
            long id = actorService.find(label).getId();
            restTemplate.delete(url + id);
        }
    }
}
