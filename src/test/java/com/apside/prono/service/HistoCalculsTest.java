package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.histoCalculs.BadRequestCreateHistoCalculsException;
import com.apside.prono.model.HistoCalculsEntity;
import com.apside.prono.repository.HistoCalculsRepository;
import com.apside.prono.service.HistoCalculsService;
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
public class HistoCalculsTest {

    @Mock
    private HistoCalculsRepository histoCalculsRepository;
    @InjectMocks
    private HistoCalculsService histoCalculsService;
    private Long value = 5L;
    private Date date = new Date();
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllHistoCalculss() {
        List<HistoCalculsEntity> histoCalculsEntityList = new ArrayList<>();
        HistoCalculsEntity histoCalculsEntity = initialize();
        HistoCalculsEntity histoCalculsEntity2 = initialize();
        histoCalculsEntityList.add(histoCalculsEntity);
        histoCalculsEntityList.add(histoCalculsEntity2);

        when(histoCalculsRepository.findAll()).thenReturn(histoCalculsEntityList);

        List<HistoCalculsEntity> result = histoCalculsService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneHistoCalculs() {
        HistoCalculsEntity histoCalculsEntity = initialize();
        histoCalculsEntity.setId(1L);

        when(histoCalculsRepository.findById(1L)).thenReturn(Optional.ofNullable(histoCalculsEntity));
        HistoCalculsEntity result = histoCalculsService.getHistoCalculs(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(histoCalculsEntity.getContest().getId(), Long.valueOf(value));
        assertEquals(histoCalculsEntity.getRankHistoCalculs(), Long.valueOf(value));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneHistoCalculs() throws Exception {
        histoCalculsService.getHistoCalculs(1L);
    }

    @Test(expected = BadRequestCreateHistoCalculsException.class)
    public void testBadRequestCreateHistoCalculs() throws Exception {
        HistoCalculsEntity histoCalculsEntity = new HistoCalculsEntity();
        histoCalculsEntity.setId(1L);
        histoCalculsService.createHistoCalculs(histoCalculsEntity);
    }

    @Test
    public void saveHistoCalculs() {
        HistoCalculsEntity histoCalculsEntity = initialize();
        HistoCalculsEntity histoCalculsSave = initialize();
        histoCalculsSave.setId(1L);
        when(histoCalculsRepository.save(histoCalculsEntity)).thenReturn(histoCalculsSave);
        HistoCalculsEntity histoCalculs = histoCalculsService.createHistoCalculs(histoCalculsEntity);
        assertEquals(Long.valueOf(1L), histoCalculs.getId());
        assertEquals(histoCalculs.getContest().getId(), Long.valueOf(value));
        assertEquals(histoCalculs.getRankHistoCalculs(), value);
    }


    @Test
    public void updateHistoCalculs() {
        HistoCalculsEntity histoCalculsEntity = initialize();
        HistoCalculsEntity histoCalculsUpdate = initialize();
        histoCalculsUpdate.setRankHistoCalculs(6L);
        histoCalculsUpdate.setId(1L);
        histoCalculsEntity.setId(1L);
        when(histoCalculsRepository.findById(1L)).thenReturn(Optional.of(histoCalculsUpdate));
        HistoCalculsEntity histoCalculs = histoCalculsService.update(histoCalculsUpdate);
        assertEquals(Long.valueOf(1L), histoCalculs.getId());
        assertEquals(histoCalculs.getRankHistoCalculs(), Long.valueOf(6L));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateHistoCalculs() throws Exception {
        HistoCalculsEntity histoCalculsEntity = initialize();
        histoCalculsService.update(histoCalculsEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteHistoCalculs() throws Exception {
        histoCalculsService.delete(-1L);
    }

    public HistoCalculsEntity initialize() {
        HistoCalculsEntity histoCalculsEntity = new HistoCalculsEntity();
        histoCalculsEntity.setRankHistoCalculs(value);
        histoCalculsEntity.getContest().setId(value);
        histoCalculsEntity.getContest().setLabel("ligue 3");
        histoCalculsEntity.setDateHourBegin(date);
        histoCalculsEntity.setDateHourEnd(date);
        histoCalculsEntity.setDateHourCalculs(date);

        return histoCalculsEntity;
    }

    @Test
    public void testDeleteContest() {
        HistoCalculsEntity histoCalculsEntity = initialize();
        histoCalculsEntity.setId(1L);
        when(histoCalculsRepository.findById(1L)).thenReturn(Optional.of(histoCalculsEntity));
        histoCalculsService.delete(1L);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorNull() throws Exception {
        histoCalculsService.update(null);
    }
    @Test(expected = BadRequestCreateHistoCalculsException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        histoCalculsService.createHistoCalculs(null);
    }
}
