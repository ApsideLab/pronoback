package com.apside.prono.resultEvent;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.*;
import com.apside.prono.service.ResultEventService;
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
public class ResultEventIntegrationTest {
    private String url = "http://localhost:8086/pronos/resultEvent/";
    private String urlActor = "http://localhost:8086/pronos/actors/";
    private String urlType = "http://localhost:8086/pronos/types/";
    private String urlEvent = "http://localhost:8086/pronos/evenements/";
    private String label = "TestResultEvent";
    private Date date = new Date();
    private String urlResult = "http://localhost:8086/pronos/results/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResultEventService resultEventService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        ResultEventEntity resultEventEntity = new ResultEventEntity();
        resultEventEntity.setType(initializeType());
        resultEventEntity.setActor(initializeActor());
        resultEventEntity.setEvenement(initializeEvenement());
        resultEventEntity.setResultDetail(initializeResult());
        resultEventEntity.setDate(new Date());

        ResponseEntity<String> response = restTemplate.postForEntity(url, resultEventEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        String newLabel = "trololoTestResultEvent";
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(newLabel);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlType, typeEntity, String.class);
        typeEntity.setId(getId(responseEntity));


        long id = initialize();
        ResultEventEntity resultEventEntity = resultEventService.getResultEvent(id);
        Long idTypeOld = resultEventEntity.getType().getId();
        resultEventEntity.setType(typeEntity);

        restTemplate.put(url, resultEventEntity);

        //Verify request succeed
        assertThat(resultEventService.getResultEvent(id) != null).isTrue();
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
        ResultEventEntity resultEventEntity = restTemplate.getForObject(url + id, ResultEventEntity.class);
        assertThat(resultEventEntity.getId() == id && resultEventEntity.getId().equals(id)).isTrue();
        assertThat(resultEventEntity.getResultDetail().getResult().equals(label)).isTrue();
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
        ResultEventEntity resultEventEntity = restTemplate.getForObject(url + id, ResultEventEntity.class);
        restTemplate.delete(url + id);
        restTemplate.delete(urlActor + resultEventEntity.getActor().getId());
        restTemplate.delete(urlType + resultEventEntity.getType().getId());
        restTemplate.delete(urlEvent + resultEventEntity.getEvenement().getId());
        restTemplate.delete(urlResult + resultEventEntity.getResultDetail().getId());
        try {
            resultEventService.getResultEvent(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        ResultEventEntity resultEventEntity = new ResultEventEntity();
        resultEventEntity.setActor(initializeActor());
        resultEventEntity.setEvenement(initializeEvenement());
        resultEventEntity.setType(initializeType());
        resultEventEntity.setResultDetail(initializeResult());
        resultEventEntity.setDate(new Date());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, resultEventEntity, String.class);
        return getId(responseEntity);
    }


    public EvenementEntity initializeEvenement() {
        EvenementEntity evenementEntity = new EvenementEntity();
        evenementEntity.setLibelle(label);
        long value = 3L;
        evenementEntity.setCoeff(value);
        evenementEntity.setDateEvenement(date);
        evenementEntity.setDateFermeture(date);
        evenementEntity.setDateOuverture(date);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlEvent, evenementEntity, String.class);
        evenementEntity.setId(getId(responseEntity));
        return evenementEntity;
    }

    public TypeEntity initializeType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlType, typeEntity, String.class);
        typeEntity.setId(getId(responseEntity));
        return typeEntity;
    }

    public ActorEntity initializeActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlActor, actorEntity, String.class);
        actorEntity.setId(getId(responseEntity));
        return actorEntity;
    }

    public ResultEntity initializeResult() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlResult, resultEntity, String.class);
        resultEntity.setId(getId(responseEntity));
        return resultEntity;
    }

    public void clean(long id) {
        ResultEventEntity resultEventEntity = restTemplate.getForObject(url + id, ResultEventEntity.class);
        try {
            restTemplate.delete(url + id);
            restTemplate.delete(urlActor + resultEventEntity.getActor().getId());
            restTemplate.delete(urlType + resultEventEntity.getType().getId());
            restTemplate.delete(urlEvent + resultEventEntity.getEvenement().getId());
            restTemplate.delete(urlResult + resultEventEntity.getResultDetail().getId());
        } catch (Exception e) {
            throw new NullPointerException();
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
