package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.resultEvent.BadRequestCreateResultEventException;
import com.apside.prono.model.*;
import com.apside.prono.repository.ResultEventRepository;
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
public class ResultEventTest {

    @Mock
    private ResultEventRepository resultEventRepository;
    @InjectMocks
    private ResultEventService resultEventService;
    private Long value = 5L;
    private String label = "testResultEvent";
    private String result = "0-1";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllResultEvents() {
        List<ResultEventEntity> resultEventEntityList = new ArrayList<>();
        ResultEventEntity resultEventEntity = initialize();
        ResultEventEntity resultEventEntity2 = initialize();
        resultEventEntityList.add(resultEventEntity);
        resultEventEntityList.add(resultEventEntity2);

        when(resultEventRepository.findAll()).thenReturn(resultEventEntityList);

        List<ResultEventEntity> result = resultEventService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneResultEvent() {
        ResultEventEntity resultEventEntity = initialize();
        resultEventEntity.setId(1L);

        when(resultEventRepository.findById(1L)).thenReturn(Optional.ofNullable(resultEventEntity));
        ResultEventEntity result = resultEventService.getResultEvent(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(resultEventEntity.getResultDetail().getResult(), "0-1");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneResultEvent() throws Exception {
        resultEventService.getResultEvent(1L);
    }

    @Test(expected = BadRequestCreateResultEventException.class)
    public void testBadRequestCreateResultEvent() throws Exception {
        ResultEventEntity resultEventEntity = new ResultEventEntity();
        resultEventEntity.setId(1L);
        resultEventService.createResultEvent(resultEventEntity);
    }

    @Test
    public void saveResultEvent() {
        ResultEventEntity resultEventEntity = initialize();
        ResultEventEntity resultEventSave = initialize();
        resultEventSave.setId(1L);
        when(resultEventRepository.save(resultEventEntity)).thenReturn(resultEventSave);
        ResultEventEntity resultEvent = resultEventService.createResultEvent(resultEventEntity);
        assertEquals(Long.valueOf(1L), resultEvent.getId());
        assertEquals(resultEvent.getResultDetail().getResult(), result);
    }


    @Test
    public void updateResultEvent() {
        ResultEventEntity resultEventEntity = initialize();
        ResultEventEntity resultEventUpdate = initialize();
        resultEventUpdate.getResultDetail().setResult("0-2");
        resultEventUpdate.setId(1L);
        when(resultEventRepository.findById(1L)).thenReturn(Optional.of(resultEventUpdate));
        ResultEventEntity resultEvent = resultEventService.update(resultEventUpdate);
        assertEquals(Long.valueOf(1L), resultEvent.getId());
        assertEquals(resultEvent.getResultDetail().getResult(), "0-2");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResultEvent() throws Exception {
        ResultEventEntity resultEventEntity = initialize();
        resultEventService.update(resultEventEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteResultEvent() throws Exception {
        resultEventService.delete(-1L);
    }

    public ResultEventEntity initialize() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);

        EvenementEntity evenementEntity = new EvenementEntity();
        evenementEntity.setDateOuverture(new Date());
        evenementEntity.setDateFermeture(new Date());
        evenementEntity.setDateEvenement(new Date());
        evenementEntity.setCoeff(value);
        evenementEntity.setLibelle(label);

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(result);

        ResultEventEntity resultEventEntity = new ResultEventEntity();
        resultEventEntity.setActor(actorEntity);
        resultEventEntity.setEvenement(evenementEntity);
        resultEventEntity.setType(typeEntity);
        resultEventEntity.setResultDetail(resultEntity);
        resultEventEntity.setDate(new Date());
        return resultEventEntity;
    }
    @Test
    public void testDeleteResultEvent() {
        ResultEventEntity resultEventEntity = initialize();
        resultEventEntity.setId(1L);
        when(resultEventRepository.findById(1L)).thenReturn(Optional.of(resultEventEntity));
        resultEventService.delete(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResult() throws Exception {
        ResultEventEntity resultEventEntity  = initialize();
        resultEventEntity.setId(1L);
        resultEventService.update(resultEventEntity);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResultNull() throws Exception {
        resultEventService.update(null);
    }
    @Test(expected = BadRequestCreateResultEventException.class)
    public void testEntityNotFoundCreateResultNull() throws Exception {
        resultEventService.createResultEvent(null);
    }
}
