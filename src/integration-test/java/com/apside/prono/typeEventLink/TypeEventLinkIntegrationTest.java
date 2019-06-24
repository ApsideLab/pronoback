package com.apside.prono.typeEventLink;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.model.TypeEntity;
import com.apside.prono.model.TypeEventLinkEntity;
import com.apside.prono.service.TypeEventLinkService;
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
public class TypeEventLinkIntegrationTest {
    String url = "http://localhost:8086/pronos/typeEventLink/";
    String urlType = "http://localhost:8086/pronos/types/";
    String urlEvent = "http://localhost:8086/pronos/evenements/";
    Long value = 3L;
    String label = "test";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TypeEventLinkService typeEventLinkService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        TypeEventLinkEntity typeEventLinkEntity = new TypeEventLinkEntity();
        typeEventLinkEntity.setType(initializeType());
        typeEventLinkEntity.setEvent(initializeEvent());

        ResponseEntity<String> response = restTemplate.postForEntity(url, typeEventLinkEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        String newLabel = "trololo";
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(newLabel);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlType, typeEntity, String.class);
        typeEntity.setId(getId(responseEntity));


        long id = initialize();
        TypeEventLinkEntity typeEventLinkEntity = typeEventLinkService.getTypeEventLink(id);
        Long idTypeOld = typeEventLinkEntity.getType().getId();
        typeEventLinkEntity.setType(typeEntity);

        restTemplate.put(url, typeEventLinkEntity);

        //Verify request succeed
        assertThat(typeEventLinkService.getTypeEventLink(id) != null).isTrue();
        assertThat(restTemplate.getForObject(url + id, TypeEventLinkEntity.class).getType().getLabel().equals(newLabel)).isTrue();

        restTemplate.delete(urlType + idTypeOld);
        clean(id);
    }

    @Test
    public void getTest() {
        long id = initialize();

        //Verification du code retour
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        TypeEventLinkEntity typeEventLinkEntity = restTemplate.getForObject(url + id, TypeEventLinkEntity.class);
        assertThat(typeEventLinkEntity.getId() == id && typeEventLinkEntity.getId().equals(id)).isTrue();
        assertThat(typeEventLinkEntity.getType().getLabel().equals(label)).isTrue();
        assertThat(typeEventLinkEntity.getEvent().getLibelle().equals(label)).isTrue();

        clean(id);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }
//TODO a finir, la methode delete de evenements
    @Test
    public void deleteTest() {
        long id = initialize();

        TypeEventLinkEntity typeEventLinkEntity = typeEventLinkService.getTypeEventLink(id);
        TypeEntity type = typeEventLinkEntity.getType();
        EvenementEntity evenementEntity = typeEventLinkEntity.getEvent();

        restTemplate.delete(url + id);

        restTemplate.delete(urlType + type.getId());
        restTemplate.delete(urlEvent + evenementEntity.getId());

        try {
            typeEventLinkService.getTypeEventLink(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        TypeEventLinkEntity typeEventLinkEntity = new TypeEventLinkEntity();
        typeEventLinkEntity.setType(initializeType());
        typeEventLinkEntity.setEvent(initializeEvent());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, typeEventLinkEntity, String.class);
        return getId(responseEntity);
    }

    public TypeEntity initializeType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlType, typeEntity, String.class);
        typeEntity.setId(getId(responseEntity));
        return typeEntity;
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
        if (typeEventLinkService.getTypeEventLink(id) != null) {
            TypeEventLinkEntity typeEventLinkEntity = typeEventLinkService.getTypeEventLink(id);
            TypeEntity type = typeEventLinkEntity.getType();
            EvenementEntity evenementEntity = typeEventLinkEntity.getEvent();
            try {
                restTemplate.delete(url + id);
                restTemplate.delete(urlType + type.getId());
                restTemplate.delete(urlEvent + evenementEntity.getId());
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
