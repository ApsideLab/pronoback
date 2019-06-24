package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.player.BadRequestCreatePlayerException;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.repository.PlayerRepository;
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
public class PlayersTest {

    private static final String LIBELLE_PLAYER1 = "player1@player.com";
    private static final String LIBELLE_PLAYER2 = "player2@player.com";
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPlayers() {
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setMail(LIBELLE_PLAYER1);
        PlayerEntity playerEntity2 = new PlayerEntity();
        playerEntity2.setId(2L);
        playerEntity2.setMail(LIBELLE_PLAYER2);
        playerEntityList.add(playerEntity);
        playerEntityList.add(playerEntity2);

        when(playerRepository.findAll()).thenReturn(playerEntityList);

        List<PlayerEntity> result = playerService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOnePlayer() {
        Optional<PlayerEntity> playerEntity = Optional.of(new PlayerEntity());
        playerEntity.get().setId(1L);
        playerEntity.get().setMail(LIBELLE_PLAYER1);

        when(playerRepository.findById(1L)).thenReturn(playerEntity);
        PlayerEntity result = playerService.getPlayer(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_PLAYER1, result.getMail());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOnePlayer() throws Exception {
        playerService.getPlayer(1L);
    }

    @Test(expected = BadRequestCreatePlayerException.class)
    public void testBadRequestCreatePlayer() throws Exception {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerService.createPlayer(playerEntity);
    }

    @Test
    public void savePlayer() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setMail(LIBELLE_PLAYER1);
        PlayerEntity playerSave = new PlayerEntity();
        playerSave.setId(1L);
        playerSave.setMail(LIBELLE_PLAYER1);
        when(playerRepository.save(playerEntity)).thenReturn(playerSave);
        PlayerEntity player = playerService.createPlayer(playerEntity);
        assertEquals(Long.valueOf(1L), player.getId());
        assertEquals(LIBELLE_PLAYER1, player.getMail());
    }


    @Test
    public void updatePlayer() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setMail(LIBELLE_PLAYER1);
        playerEntity.setId(1L);
        PlayerEntity playerUpdate = new PlayerEntity();
        playerUpdate.setId(1L);
        playerUpdate.setMail(LIBELLE_PLAYER2);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(playerUpdate));
        PlayerEntity player = playerService.update(playerUpdate);
        assertEquals(Long.valueOf(1L), player.getId());
        assertEquals(LIBELLE_PLAYER2, player.getMail());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdatePlayer() throws Exception {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setMail(LIBELLE_PLAYER2);
        playerService.update(playerEntity);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdatePlayerEmpty() throws Exception {
        PlayerEntity playerEntity = null;
        playerService.update(playerEntity);
    }
    @Test(expected = BadRequestCreatePlayerException.class)
    public void testEntityNotFoundCreatePlayerEmpty() throws Exception {
        PlayerEntity playerEntity = null;
        playerService.createPlayer(playerEntity);
    }
    
    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeletePlayer() throws Exception {
        playerService.delete(-1L);
    }

    @Test
    public void testDeletePlayer() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setMail(LIBELLE_PLAYER1);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(playerEntity));
        playerService.delete(1L);
    }

    @Test
    public void testFindPlayerByLabel() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setMail(LIBELLE_PLAYER1);
        List<PlayerEntity> list = new ArrayList<>();
        list.add(playerEntity);
        when(playerRepository.findAll()).thenReturn(list);
        assertEquals(playerEntity.getMail(), playerService.find(LIBELLE_PLAYER1).getMail());
    }

    @Test
    public void testFindPlayerByLabelResultNull() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setMail(LIBELLE_PLAYER1);

        assertEquals(null, playerService.find(LIBELLE_PLAYER1));
    }
}
