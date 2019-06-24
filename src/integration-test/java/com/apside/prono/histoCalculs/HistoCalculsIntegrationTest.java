package com.apside.prono.histoCalculs;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.HistoCalculsEntity;
import com.apside.prono.service.HistoCalculsService;
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
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HistoCalculsIntegrationTest {
    private String url = "http://localhost:8086/pronos/histoCalculs/";
    private String urlContest = "http://localhost:8086/pronos/contest/";
    private Long value = 3L;
    private Date date = new Date();
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HistoCalculsService histoCalculsService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        HistoCalculsEntity histoCalculsEntity = new HistoCalculsEntity();
        histoCalculsEntity.setDateHourCalculs(date);
        histoCalculsEntity.setDateHourEnd(date);
        histoCalculsEntity.setDateHourBegin(date);
        histoCalculsEntity.setRankHistoCalculs(value);
        histoCalculsEntity.setContest(initializeContest());
        ResponseEntity<String> response = restTemplate.postForEntity(url, histoCalculsEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        long id = initialize();

        HistoCalculsEntity histoCalculsEntity = histoCalculsService.getHistoCalculs(id);
        histoCalculsEntity.setDateHourCalculs(date);
        histoCalculsEntity.setDateHourEnd(date);
        histoCalculsEntity.setDateHourBegin(date);
        histoCalculsEntity.setRankHistoCalculs(6L);
        restTemplate.put(url, histoCalculsEntity);

        //Verify request succeed
        assertThat(histoCalculsService.getHistoCalculs(id) != null).isTrue();
        clean(id);
    }

    @Test
    public void getTest() {
        long id = initialize();

        //Verification du code retour
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        HistoCalculsEntity histoCalculsEntity = restTemplate.getForObject(url + id, HistoCalculsEntity.class);
        assertThat(histoCalculsEntity.getId() == id && histoCalculsEntity.getId().equals(id)).isTrue();
        assertThat(histoCalculsEntity.getRankHistoCalculs() == value && histoCalculsEntity.getRankHistoCalculs().equals(value)).isTrue();

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
        if (histoCalculsService.getHistoCalculs(id) != null) {
            ContestEntity contestEntity = histoCalculsService.getHistoCalculs(id).getContest();
            restTemplate.delete(url + id);
            cleanContest(contestEntity);
            try {
                histoCalculsService.getHistoCalculs(id);
            } catch (EntityNotFoundException e) {
                assertThat(true).isTrue();
                return;
            }

        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        HistoCalculsEntity histoCalculsEntity = new HistoCalculsEntity();
        histoCalculsEntity.setRankHistoCalculs(value);
        histoCalculsEntity.setContest(initializeContest());
        histoCalculsEntity.setDateHourBegin(date);
        histoCalculsEntity.setDateHourEnd(date);
        histoCalculsEntity.setDateHourCalculs(date);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, histoCalculsEntity, String.class);
        return getId(responseEntity);
    }

    public ContestEntity initializeContest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setLabel("Ligue 1 - HistoCalcul"+(new Random().nextInt(100)));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlContest, contestEntity, String.class);
        contestEntity.setId(getId(responseEntity));
        return contestEntity;
    }

    public void clean(long id) {
        if (histoCalculsService.getHistoCalculs(id) != null) {
            ContestEntity contestEntity = histoCalculsService.getHistoCalculs(id).getContest();
            restTemplate.delete(url + id);
            cleanContest(contestEntity);
        }
    }
    public void cleanContest(ContestEntity contestEntity) {
        restTemplate.delete(urlContest + contestEntity.getId());
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
