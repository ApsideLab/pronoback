package com.apside.prono.service;

import com.apside.prono.errors.HistoRanking.BadRequestCreateHistoRankingException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.HistoRankingEntity;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.repository.HistoRankingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class HistoRankingTest {

    String label = "Contest1Test";
    String lastName = "Monkey D.";
    String firstName = "Luffy";
    String mail = "one@piece.jp";
    Date subscribeDate = new Date();
    @Mock
    private HistoRankingRepository histoRankingRepository;
    @InjectMocks
    private HistoRankingService histoRankingService;
    private Long value = 5L;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllHistoRankings() {
        List<HistoRankingEntity> histoRankingEntityList = new ArrayList<>();
        HistoRankingEntity histoRankingEntity = initialize();
        HistoRankingEntity histoRankingEntity2 = initialize();
        histoRankingEntityList.add(histoRankingEntity);
        histoRankingEntityList.add(histoRankingEntity2);

        when(histoRankingRepository.findAll()).thenReturn(histoRankingEntityList);

        List<HistoRankingEntity> result = histoRankingService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneHistoRanking() {
        HistoRankingEntity histoRankingEntity = initialize();
        histoRankingEntity.setId(1L);

        when(histoRankingRepository.findById(1L)).thenReturn(Optional.ofNullable(histoRankingEntity));
        HistoRankingEntity result = histoRankingService.getHistoRanking(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(histoRankingEntity.getBonus(), Long.valueOf(value));
        assertEquals(histoRankingEntity.getPlayer().getFirstName(), firstName);
        assertEquals(histoRankingEntity.getPlayer().getLastName(), lastName);
        assertEquals(histoRankingEntity.getPlayer().getMail(), mail);
        assertEquals(histoRankingEntity.getContest().getLabel(), label);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneHistoRanking() throws Exception {
        histoRankingService.getHistoRanking(1L);
    }

    @Test(expected = BadRequestCreateHistoRankingException.class)
    public void testBadRequestCreateHistoRanking() throws Exception {
        HistoRankingEntity histoRankingEntity = new HistoRankingEntity();
        histoRankingEntity.setId(1L);
        histoRankingService.createHistoRanking(histoRankingEntity);
    }

    @Test
    public void saveHistoRanking() {
        HistoRankingEntity histoRankingEntity = initialize();
        HistoRankingEntity histoRankingSave = initialize();
        histoRankingSave.setId(1L);
        when(histoRankingRepository.save(histoRankingEntity)).thenReturn(histoRankingSave);
        HistoRankingEntity histoRanking = histoRankingService.createHistoRanking(histoRankingEntity);
        assertEquals(Long.valueOf(1L), histoRanking.getId());
        assertEquals(histoRanking.getBonus(), Long.valueOf(value));
        assertEquals(histoRanking.getPlayer().getMail(), mail);
        assertEquals(histoRanking.getPlayer().getFirstName(), firstName);
        assertEquals(histoRanking.getPlayer().getLastName(), lastName);
        assertEquals(histoRanking.getContest().getLabel(), label);
    }


    @Test
    public void updateHistoRanking() {
        HistoRankingEntity histoRankingEntity = initialize();
        HistoRankingEntity histoRankingUpdate = initialize();
        histoRankingUpdate.setRank(6L);
        histoRankingUpdate.setId(1L);
        when(histoRankingRepository.findById(1L)).thenReturn(Optional.of(histoRankingUpdate));
        HistoRankingEntity histoRanking = histoRankingService.update(histoRankingUpdate);
        assertEquals(Long.valueOf(1L), histoRanking.getId());
        assertEquals(histoRanking.getRank(), Long.valueOf(6L));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateHistoRanking() throws Exception {
        HistoRankingEntity histoRankingEntity = initialize();
        histoRankingService.update(histoRankingEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteHistoRanking() throws Exception {
        histoRankingService.delete(-1L);
    }

    public HistoRankingEntity initialize() {
        PlayerEntity player = new PlayerEntity();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setMail(mail);
        player.setSubscribeDate(subscribeDate);

        ContestEntity contest = new ContestEntity();
        contest.setLabel(label);

        HistoRankingEntity histoRankingEntity = new HistoRankingEntity();
        histoRankingEntity.setRank(value);
        histoRankingEntity.setScoreOkRugby(value);
        histoRankingEntity.setPosition(value);
        histoRankingEntity.setPoints(value);
        histoRankingEntity.setNbTryOkRugby(value);
        histoRankingEntity.setPlayer(player);
        histoRankingEntity.setGoodProno(value);
        histoRankingEntity.setDifferencePointsRugby(value);
        histoRankingEntity.setBonus(value);
        histoRankingEntity.setContest(contest);
        histoRankingEntity.setDate(new Date());
        return histoRankingEntity;
    }
    @Test
    public void testDeleteContest() {
        HistoRankingEntity histoRankingEntity = initialize();
        histoRankingEntity.setId(1L);
        when(histoRankingRepository.findById(1L)).thenReturn(Optional.of(histoRankingEntity));
        histoRankingService.delete(1L);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorNull() throws Exception {
        histoRankingService.update(null);
    }
    @Test(expected = BadRequestCreateHistoRankingException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        histoRankingService.createHistoRanking(null);
    }
}
