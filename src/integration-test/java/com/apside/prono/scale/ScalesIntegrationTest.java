package com.apside.prono.scale;

import com.apside.prono.model.ScaleEntity;
import com.apside.prono.service.ScaleService;
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
public class ScalesIntegrationTest {
    String url = "http://localhost:8086/pronos/scales/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScaleService scaleService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        String label = "Scale 1";

        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setPtsBonusUnScoreExactResultatOK(1);
        scaleEntity.setLabel(label);
        scaleEntity.setPtsBonusUnScoreExactResultatKO(2);
        scaleEntity.setPtsBonusEcartButs(3);
        scaleEntity.setPtsBonusDeuxScoresExacts(4);
        scaleEntity.setPtsBonRésultat(5);
        scaleEntity.setDateFinValidite(new Date());
        scaleEntity.setDateDebutValidite(new Date());

        ResponseEntity<String> response = restTemplate.postForEntity(url, scaleEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(label);
    }

    @Test
    public void putTest() {
        initialize("Scale 2");
        String label = "Scale 3";

        Date newDate = new Date();
        newDate.setTime(0);

        ScaleEntity scaleEntity = scaleService.find("Scale 2");
        scaleEntity.setLabel(label);
        restTemplate.put(url, scaleEntity);

        //Verify request succeed
        assertThat(scaleService.find(label) != null).isTrue();
        clean(label);
    }

    @Test
    public void getTest() {
        String label = "Scale 4";
        initialize(label);

        //Verification du code retour
        long id = scaleService.find(label).getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        ScaleEntity scaleEntity = restTemplate.getForObject(url + id, ScaleEntity.class);
        assertThat(scaleEntity.getId() == id && scaleEntity.getLabel().equals(label)).isTrue();
        clean(label);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        String label = "Scale 5";
        initialize(label);

        long id = scaleService.find(label).getId();
        restTemplate.delete(url + id);

        assertThat(scaleService.find(label) == null).isTrue();
    }

    public void initialize(String label) {
        if (scaleService.find(label) == null) {
            ScaleEntity scaleEntity = new ScaleEntity();
            scaleEntity.setPtsBonusUnScoreExactResultatOK(1);
            scaleEntity.setPtsBonusUnScoreExactResultatKO(2);
            scaleEntity.setPtsBonusEcartButs(3);
            scaleEntity.setPtsBonusDeuxScoresExacts(4);
            scaleEntity.setPtsBonRésultat(5);
            scaleEntity.setDateFinValidite(new Date());
            scaleEntity.setDateDebutValidite(new Date());

            scaleEntity.setLabel(label);
            restTemplate.postForEntity(url, scaleEntity, String.class);
        }
    }

    public void clean(String mail) {
        if (scaleService.find(mail) != null) {
            long id = scaleService.find(mail).getId();
            restTemplate.delete(url + id);
        }
    }
}
