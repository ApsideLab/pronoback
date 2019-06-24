package com.apside.prono.histoRanking;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.HistoRankingEntity;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.service.HistoRankingService;
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
public class HistoRankingIntegrationTest {
    private String url = "http://localhost:8086/pronos/histoRanking/";
    private String urlPlayer = "http://localhost:8086/pronos/players/";
    private String urlContest = "http://localhost:8086/pronos/contest/";
    private Long value = 3L;
    private String label = "Contest1Test";
    private String lastName = "Monkey D.";
    private String firstName = "Luffy";
    private String mail = "one@piece.jp";
    private Date subscribeDate = new Date();

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HistoRankingService histoRankingService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        HistoRankingEntity histoRankingEntity = new HistoRankingEntity();
        histoRankingEntity.setRank(value);
        histoRankingEntity.setScoreOkRugby(value);
        histoRankingEntity.setPosition(value);
        histoRankingEntity.setPoints(value);
        histoRankingEntity.setNbTryOkRugby(value);
        histoRankingEntity.setGoodProno(value);
        histoRankingEntity.setDifferencePointsRugby(value);
        histoRankingEntity.setBonus(value);
        histoRankingEntity.setDate(new Date());
        histoRankingEntity.setPlayer(initializePlayer());
        histoRankingEntity.setContest(initializeContest());

        ResponseEntity<String> response = restTemplate.postForEntity(url, histoRankingEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(getId(response));
    }

    @Test
    public void putTest() {
        long id = initialize();

        HistoRankingEntity histoRankingEntity = histoRankingService.getHistoRanking(id);
        histoRankingEntity.setPosition(56L);
        restTemplate.put(url, histoRankingEntity);

        //Verify request succeed
        assertThat(histoRankingService.getHistoRanking(id) != null).isTrue();
        assertThat(histoRankingService.getHistoRanking(id).getPosition() == 56L).isTrue();
        clean(id);
    }

    @Test
    public void getTest() {
        long id = initialize();

        //Verification du code retour
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        HistoRankingEntity histoRankingEntity = restTemplate.getForObject(url + id, HistoRankingEntity.class);
        assertThat(histoRankingEntity.getId() == id && histoRankingEntity.getId().equals(id)).isTrue();
        assertThat(histoRankingEntity.getBonus().equals(value)).isTrue();
        assertThat(histoRankingEntity.getContest().getLabel().equals(label)).isTrue();
        assertThat(histoRankingEntity.getPlayer().getMail().equals(mail)).isTrue();
        assertThat(histoRankingEntity.getPlayer().getFirstName().equals(firstName)).isTrue();
        assertThat(histoRankingEntity.getPlayer().getLastName().equals(lastName)).isTrue();
        assertThat(histoRankingEntity.getRank().equals(value)).isTrue();
        assertThat(histoRankingEntity.getDifferencePointsRugby().equals(value)).isTrue();
        assertThat(histoRankingEntity.getGoodProno().equals(value)).isTrue();
        assertThat(histoRankingEntity.getNbTryOkRugby().equals(value)).isTrue();
        assertThat(histoRankingEntity.getPoints().equals(value)).isTrue();
        assertThat(histoRankingEntity.getPosition().equals(value)).isTrue();
        assertThat(histoRankingEntity.getScoreOkRugby().equals(value)).isTrue();
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
        HistoRankingEntity histoRankingEntity = histoRankingService.getHistoRanking(id);
        restTemplate.delete(url + id);
        restTemplate.delete(urlPlayer + histoRankingEntity.getPlayer().getId());
        restTemplate.delete(urlContest + histoRankingEntity.getContest().getId());

        try {
            histoRankingService.getHistoRanking(id);
        } catch (EntityNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }
        assertThat(false).isTrue();
    }

    public long initialize() {
        HistoRankingEntity histoRankingEntity = new HistoRankingEntity();
        histoRankingEntity.setRank(value);
        histoRankingEntity.setScoreOkRugby(value);
        histoRankingEntity.setPosition(value);
        histoRankingEntity.setPoints(value);
        histoRankingEntity.setNbTryOkRugby(value);
        histoRankingEntity.setGoodProno(value);
        histoRankingEntity.setDifferencePointsRugby(value);
        histoRankingEntity.setBonus(value);
        histoRankingEntity.setDate(new Date());
        histoRankingEntity.setContest(initializeContest());
        histoRankingEntity.setPlayer(initializePlayer());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, histoRankingEntity, String.class);
        return getId(responseEntity);
    }


    public PlayerEntity initializePlayer() {
        PlayerEntity player = new PlayerEntity();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setMail(mail);
        player.setSubscribeDate(subscribeDate);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlPlayer, player, String.class);
        player.setId(getId(responseEntity));
        return player;
    }

    public ContestEntity initializeContest() {
        ContestEntity contest = new ContestEntity();
        contest.setLabel(label);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlContest, contest, String.class);
        contest.setId(getId(responseEntity));
        return contest;
    }

    public void clean(long id) {
        if (histoRankingService.getHistoRanking(id) != null) {
            HistoRankingEntity histoRankingEntity = histoRankingService.getHistoRanking(id);
            restTemplate.delete(url + id);
            restTemplate.delete(urlPlayer + histoRankingEntity.getPlayer().getId());
            restTemplate.delete(urlContest + histoRankingEntity.getContest().getId());
        }
    }

    public long getId(ResponseEntity<String> responseEntity) {
        String result = responseEntity.toString();
        if (result.contains("<500,") || result.contains("<404,")) {//Don't initialize
            throw new NullPointerException();
        }
        result = result.substring(result.indexOf("\"id\":") + 5, result.indexOf(",\""));
        result = result.replace("\"", "");
        return Long.parseLong(result);
    }
}
