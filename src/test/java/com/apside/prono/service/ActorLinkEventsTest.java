package com.apside.prono.service;

import com.apside.prono.errors.actorLinkEvent.BadRequestCreateActorLinkEventException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.repository.ActorLinkEventRepository;
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
public class ActorLinkEventsTest {

    String label = "test";
    @Mock
    private ActorLinkEventRepository actorLinkEventRepository;
    @InjectMocks
    private ActorLinkEventService actorLinkEventService;
    private Long value = 5L;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllActorLinkEvents() {
        List<ActorLinkEventEntity> actorLinkEventEntityList = new ArrayList<>();
        ActorLinkEventEntity actorLinkEventEntity = initialize();
        ActorLinkEventEntity actorLinkEventEntity2 = initialize();
        actorLinkEventEntityList.add(actorLinkEventEntity);
        actorLinkEventEntityList.add(actorLinkEventEntity2);

        when(actorLinkEventRepository.findAll()).thenReturn(actorLinkEventEntityList);

        List<ActorLinkEventEntity> result = actorLinkEventService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneActorLinkEvent() {
        ActorLinkEventEntity actorLinkEventEntity = initialize();
        actorLinkEventEntity.setId(1L);

        when(actorLinkEventRepository.findById(1L)).thenReturn(Optional.ofNullable(actorLinkEventEntity));
        ActorLinkEventEntity result = actorLinkEventService.getActorLinkEvent(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(actorLinkEventEntity.getActor().getLabel(), label);
        assertEquals(actorLinkEventEntity.getEvent().getLibelle(), label);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneActorLinkEvent() throws Exception {
        actorLinkEventService.getActorLinkEvent(1L);
    }

    @Test(expected = BadRequestCreateActorLinkEventException.class)
    public void testBadRequestCreateActorLinkEvent() throws Exception {
        ActorLinkEventEntity actorLinkEventEntity = new ActorLinkEventEntity();
        actorLinkEventEntity.setId(1L);
        actorLinkEventService.createActorLinkEvent(actorLinkEventEntity);
    }

    @Test
    public void saveActorLinkEvent() {
        ActorLinkEventEntity actorLinkEventEntity = initialize();
        ActorLinkEventEntity actorLinkEventSave = initialize();
        actorLinkEventSave.setId(1L);
        when(actorLinkEventRepository.save(actorLinkEventEntity)).thenReturn(actorLinkEventSave);
        ActorLinkEventEntity actorLinkEvent = actorLinkEventService.createActorLinkEvent(actorLinkEventEntity);
        assertEquals(Long.valueOf(1L), actorLinkEvent.getId());
        assertEquals(actorLinkEvent.getActor().getLabel(), label);
        assertEquals(actorLinkEvent.getEvent().getLibelle(), label);
    }


    @Test
    public void updateActorLinkEvent() {
        ActorLinkEventEntity actorLinkEventUpdate = initialize();
        actorLinkEventUpdate.setActor(initializeActor());
        actorLinkEventUpdate.setId(1L);
        when(actorLinkEventRepository.findById(1L)).thenReturn(Optional.of(actorLinkEventUpdate));
        ActorLinkEventEntity actorLinkEvent = actorLinkEventService.update(actorLinkEventUpdate);
        assertEquals(Long.valueOf(1L), actorLinkEvent.getId());
        assertEquals(actorLinkEvent.getActor().getLabel(), label);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorLinkEvent() throws Exception {
        ActorLinkEventEntity actorLinkEventEntity = initialize();
        actorLinkEventService.update(actorLinkEventEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorLinkEventNull() throws Exception {
        actorLinkEventService.update(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteActorLinkEvent() throws Exception {
        actorLinkEventService.delete(-1L);
    }

    @Test
    public void testDeleteActorLinkEvent() {
        ActorLinkEventEntity actorLinkEvent = initialize();
        actorLinkEvent.setId(1L);
        when(actorLinkEventRepository.findById(1L)).thenReturn(Optional.of(actorLinkEvent));
        actorLinkEventService.delete(1L);
    }

    public ActorLinkEventEntity initialize() {
        ActorLinkEventEntity actorLinkEventEntity = new ActorLinkEventEntity();
        actorLinkEventEntity.setActor(initializeActor());
        actorLinkEventEntity.setEvent(initializeEvent());
        return actorLinkEventEntity;
    }

    public ActorEntity initializeActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);
        actorEntity.setId(1L);
        return actorEntity;
    }

    public EvenementEntity initializeEvent() {
        EvenementEntity eventEntity = new EvenementEntity();
        eventEntity.setLibelle(label);
        eventEntity.setCoeff(value);
        eventEntity.setDateEvenement(new Date());
        eventEntity.setDateFermeture(new Date());
        eventEntity.setDateOuverture(new Date());
        eventEntity.setId(1L);
        return eventEntity;
    }

    @Test(expected = BadRequestCreateActorLinkEventException.class)
    public void testEntityNotFoundCreatePlayerEmpty() throws Exception {
        ActorLinkEventEntity actorLinkEventEntity = null;
        actorLinkEventService.createActorLinkEvent(actorLinkEventEntity);
    }
}
