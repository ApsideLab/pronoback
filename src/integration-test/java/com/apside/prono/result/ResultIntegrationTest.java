package com.apside.prono.result;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ResultEntity;
import com.apside.prono.service.ResultService;
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
public class ResultIntegrationTest {
    String url = "http://localhost:8086/pronos/results/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResultService resultService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        String scoreFoot = "2-3 Test";
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(scoreFoot);

        ResponseEntity<String> response = restTemplate.postForEntity(url, resultEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        long id = initialize("0-0 Test");

        String scoreFoot = "2-3 Test";
        ResultEntity resultEntity = resultService.getResult(id);
        resultEntity.setResult(scoreFoot);
        restTemplate.put(url, resultEntity);

        //Verify request succeed
        assertThat(resultService.getResult(id) != null).isTrue();
        clean(id);
    }

    @Test
    public void getTest() {
        String libelle = "2-3 Test";
        long id = initialize(libelle);

        //Verification du code retour
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        ResultEntity resultEntity = restTemplate.getForObject(url + id, ResultEntity.class);
        assertThat(resultEntity.getId() == id && resultEntity.getResult().equals(libelle)).isTrue();
        clean(id);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        String libelle = "2-3 Test";
        long id = initialize(libelle);
        restTemplate.delete(url + id);

        try {
            resultService.getResult(id);
        }catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize(String scoreFoot) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(scoreFoot);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, resultEntity, String.class);
        return getId(responseEntity);
    }

    public void clean(long id) {
        if (resultService.getResult(id) != null) {
            restTemplate.delete(url + id);
        }
    }

    public long getId(ResponseEntity<String> responseEntity) {
        String result = responseEntity.toString();
        if(result.contains("<500,") ) {//Don't initialize
            throw new NullPointerException();
        }
        result = result.substring(result.indexOf("\"id\":")+5, result.indexOf(",\""));
        result = result.replace("\"","");
        return Long.parseLong(result);
    }
}
