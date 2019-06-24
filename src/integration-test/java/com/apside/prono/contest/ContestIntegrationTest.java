package com.apside.prono.contest;

import com.apside.prono.model.ContestEntity;
import com.apside.prono.service.ContestService;
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
public class ContestIntegrationTest {
    private String url = "http://localhost:8086/pronos/contest/";
    private String label = "ContestTest";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContestService contestService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setLabel(label);

        ResponseEntity<String> response = restTemplate.postForEntity(url, contestEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(label);
    }

    @Test
    public void putTest() {
        initialize(label);

        String newLabel = "Contest3Test";
        ContestEntity contestEntity = contestService.find(label);
        contestEntity.setLabel(newLabel);
        restTemplate.put(url, contestEntity);

        //Verify request succeed
        assertThat(contestService.find(newLabel) != null).isTrue();
        clean(newLabel);
    }

    @Test
    public void getTest() {
        initialize(label);

        //Verification du code retour
        long id = contestService.find(label).getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        ContestEntity contestEntity = restTemplate.getForObject(url + id, ContestEntity.class);
        assertThat(contestEntity.getId() == id && contestEntity.getLabel().equals(label)).isTrue();
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
        long id = contestService.find(label).getId();
        restTemplate.delete(url + id);

        assertThat(contestService.find(label) == null).isTrue();
    }

    public void initialize(String label) {
        if (contestService.find(label) == null) {
            ContestEntity contestEntity = new ContestEntity();
            contestEntity.setLabel(label);
            restTemplate.postForEntity(url, contestEntity, String.class);
        }
    }

    public void clean(String label) {
        if (contestService.find(label) != null) {
            long id = contestService.find(label).getId();
            restTemplate.delete(url + id);
        }
    }
}
