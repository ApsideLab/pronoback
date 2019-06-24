package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.pronoPlayer.BadRequestCreatePronoPlayerException;
import com.apside.prono.model.*;
import com.apside.prono.repository.PronoPlayerRepository;
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
public class PronoPlayerTest {

    String lastName = "Monkey D.";
    String firstName = "Luffy";
    String mail = "one@piece.jp";
    Date subscribeDate = new Date();
    @Mock
    private PronoPlayerRepository pronoPlayerRepository;
    @InjectMocks
    private PronoPlayerService pronoPlayerService;
    private Long value = 5L;
    private String label = "testPronoPlayer";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPronoPlayers() {
        List<PronoPlayerEntity> pronoPlayerEntityList = new ArrayList<>();
        PronoPlayerEntity pronoPlayerEntity = initialize();
        PronoPlayerEntity pronoPlayerEntity2 = initialize();
        pronoPlayerEntityList.add(pronoPlayerEntity);
        pronoPlayerEntityList.add(pronoPlayerEntity2);

        when(pronoPlayerRepository.findAll()).thenReturn(pronoPlayerEntityList);

        List<PronoPlayerEntity> result = pronoPlayerService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOnePronoPlayer() {
        PronoPlayerEntity pronoPlayerEntity = initialize();
        pronoPlayerEntity.setId(1L);

        when(pronoPlayerRepository.findById(1L)).thenReturn(Optional.ofNullable(pronoPlayerEntity));
        PronoPlayerEntity result = pronoPlayerService.getPronoPlayer(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(pronoPlayerEntity.getActor().getLabel(), label);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOnePronoPlayer() throws Exception {
        pronoPlayerService.getPronoPlayer(1L);
    }

    @Test(expected = BadRequestCreatePronoPlayerException.class)
    public void testBadRequestCreatePronoPlayer() throws Exception {
        PronoPlayerEntity pronoPlayerEntity = new PronoPlayerEntity();
        pronoPlayerEntity.setId(1L);
        pronoPlayerService.createPronoPlayer(pronoPlayerEntity);
    }

    @Test
    public void savePronoPlayer() {
        PronoPlayerEntity pronoPlayerEntity = initialize();
        PronoPlayerEntity pronoPlayerSave = initialize();
        pronoPlayerSave.setId(1L);
        when(pronoPlayerRepository.save(pronoPlayerEntity)).thenReturn(pronoPlayerSave);
        PronoPlayerEntity pronoPlayer = pronoPlayerService.createPronoPlayer(pronoPlayerEntity);
        assertEquals(Long.valueOf(1L), pronoPlayer.getId());
        assertEquals(pronoPlayer.getActor().getLabel(), label);
    }


    @Test
    public void updatePronoPlayer() {
        PronoPlayerEntity pronoPlayerUpdate = initialize();
        //pronoPlayerUpdate.setActor(ini);
        pronoPlayerUpdate.setId(1L);
        when(pronoPlayerRepository.findById(1L)).thenReturn(Optional.of(pronoPlayerUpdate));
        PronoPlayerEntity pronoPlayer = pronoPlayerService.update(pronoPlayerUpdate);
        assertEquals(Long.valueOf(1L), pronoPlayer.getId());
        assertEquals(pronoPlayer.getActor().getLabel(), label);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdatePronoPlayer() throws Exception {
        PronoPlayerEntity pronoPlayerEntity = initialize();
        pronoPlayerService.update(pronoPlayerEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeletePronoPlayer() throws Exception {
        pronoPlayerService.delete(-1L);
    }

    public PronoPlayerEntity initialize() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);
        typeEntity.setId(1L);

        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setLabel(label);
        actorEntity.setId(1L);

        EvenementEntity eventEntity = new EvenementEntity();
        eventEntity.setLibelle(label);
        eventEntity.setCoeff(value);
        eventEntity.setDateEvenement(new Date());
        eventEntity.setDateFermeture(new Date());
        eventEntity.setDateOuverture(new Date());
        eventEntity.setId(1L);
        eventEntity.setId(1L);

        PlayerEntity player = new PlayerEntity();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setMail(mail);
        player.setSubscribeDate(subscribeDate);
        player.setId(1L);

        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setScore(String.valueOf(value));
        pronoEntity.setId(1L);


        PronoPlayerEntity pronoPlayerEntity = new PronoPlayerEntity();
        pronoPlayerEntity.setType(typeEntity);
        pronoPlayerEntity.setActor(actorEntity);
        pronoPlayerEntity.setEvent(eventEntity);
        pronoPlayerEntity.setPlayer(player);
        pronoPlayerEntity.setPronoDetail(pronoEntity);
        pronoPlayerEntity.setDate(new Date());
        return pronoPlayerEntity;
    }

    @Test
    public void testDeletePlayer() {
        PronoPlayerEntity pronoPlayerEntity = initialize();
        pronoPlayerEntity.setId(1L);
        when(pronoPlayerRepository.findById(1L)).thenReturn(Optional.of(pronoPlayerEntity));
        pronoPlayerService.delete(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorNull() throws Exception {
        pronoPlayerService.update(null);
    }

    @Test(expected = BadRequestCreatePronoPlayerException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        pronoPlayerService.createPronoPlayer(null);
    }
}
