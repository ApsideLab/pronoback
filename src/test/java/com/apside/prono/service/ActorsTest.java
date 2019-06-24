package com.apside.prono.service;

import com.apside.prono.errors.actor.BadRequestCreateActorException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.repository.ActorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActorsTest {

    private static final String LIBELLE_ACTOR1 = "actor 1";
    private static final String LIBELLE_ACTOR2 = "actor 2";
    @Mock
    private ActorRepository actorRepository;
    @InjectMocks
    private ActorService actorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllActors() {
        List<ActorEntity> actorEntityList = new ArrayList<>();
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorEntity.setLabel(LIBELLE_ACTOR1);
        ActorEntity actorEntity2 = new ActorEntity();
        actorEntity2.setId(2L);
        actorEntity2.setLabel(LIBELLE_ACTOR2);
        actorEntityList.add(actorEntity);
        actorEntityList.add(actorEntity2);

        when(actorRepository.findAll()).thenReturn(actorEntityList);

        List<ActorEntity> result = actorService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneActor() {
        Optional<ActorEntity> actorEntity = Optional.of(new ActorEntity());
        actorEntity.get().setId(1L);
        actorEntity.get().setLabel(LIBELLE_ACTOR1);

        when(actorRepository.findById(1L)).thenReturn(actorEntity);
        ActorEntity result = actorService.getActor(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_ACTOR1, result.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneActor() throws Exception {
        actorService.getActor(1L);
    }

    @Test(expected = BadRequestCreateActorException.class)
    public void testBadRequestCreateActor() throws Exception {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorService.createActor(actorEntity);
    }

    @Test
    public void saveActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(LIBELLE_ACTOR1);
        ActorEntity actorSave = new ActorEntity();
        actorSave.setId(1L);
        actorSave.setLabel(LIBELLE_ACTOR1);
        when(actorRepository.save(actorEntity)).thenReturn(actorSave);
        ActorEntity actor = actorService.createActor(actorEntity);
        assertEquals(Long.valueOf(1L), actor.getId());
        assertEquals(LIBELLE_ACTOR1, actor.getLabel());
    }

    @Test
    public void updateActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(LIBELLE_ACTOR1);
        ActorEntity actorUpdate = new ActorEntity();
        actorUpdate.setId(1L);
        actorUpdate.setLabel(LIBELLE_ACTOR2);
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actorUpdate));
        ActorEntity actor = actorService.update(actorUpdate);
        assertEquals(Long.valueOf(1L), actor.getId());
        assertEquals(LIBELLE_ACTOR2, actor.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActor() throws Exception {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorEntity.setLabel(LIBELLE_ACTOR2);
        actorService.update(actorEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteActor() throws Exception {
        actorService.delete(-1L);
    }

    @Test
    public void testDeleteActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorEntity.setLabel(LIBELLE_ACTOR1);
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actorEntity));
        actorService.delete(1L);
    }

    @Test
    public void testFindActorByLabel() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorEntity.setLabel(LIBELLE_ACTOR1);
        List<ActorEntity> list = new ArrayList<>();
        list.add(actorEntity);
        when(actorRepository.findAll()).thenReturn(list);
        assertEquals(actorEntity.getLabel(), actorService.find(LIBELLE_ACTOR1).getLabel());
    }

    @Test
    public void testFindActorByLabelResultNull() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(1L);
        actorEntity.setLabel(LIBELLE_ACTOR1);

        assertEquals(null, actorService.find(LIBELLE_ACTOR1));
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorNull() throws Exception {
        actorService.update(null);
    }
    @Test(expected = BadRequestCreateActorException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        actorService.createActor(null);
    }
}