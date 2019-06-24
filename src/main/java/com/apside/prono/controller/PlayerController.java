package com.apside.prono.controller;


import com.apside.prono.mapper.player.PlayerEntityMapper;
import com.apside.prono.mapper.player.PlayerMapper;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.modelapi.Player;
import com.apside.prono.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/")
public class PlayerController {
    private final Logger log = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /players : get all player.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of players in body
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/players")
    public List<PlayerEntity> getAllPlayers() {
        log.debug(bundle.getString("get_all_players"));
        return playerService.getAll();
    }

    /**
     * GET  /players/:id : get the "id" player.
     *
     * @param id the id of the player to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the player, or with status 404 (Not Found)
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/players/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable("id") long id) {
        String message = bundle.getString("get_player");
        log.debug(message, id);
        PlayerEntity playerEntity = playerService.getPlayer(id);
        return ResponseEntity.ok().body(PlayerMapper.INSTANCE.mapPlayer(playerEntity));
    }

    /**
     * POST  /players/ : Create a new player.
     *
     * @param player the player to create
     * @return the ResponseEntity with status 201 (Created) and with body the new player, or with status 400 (Bad Request) if the player has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@Valid @RequestBody Player player) throws URISyntaxException {
        String message = bundle.getString("post_player");
        log.debug(message, player);
        PlayerEntity playerEntity = PlayerEntityMapper.INSTANCE.mapPlayerEntity(player);
        playerEntity = playerService.createPlayer(playerEntity);
        return new ResponseEntity<>(PlayerMapper.INSTANCE.mapPlayer(playerEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /players/ : update an player.
     *
     * @param player the player to update
     * @return the ResponseEntity with status 201 (Update) and with body the new player, or with status 400 (Bad Request) if the player has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/players")
    public ResponseEntity<?> updatePlayer(@Valid @RequestBody Player player) throws URISyntaxException {
        String message = bundle.getString("put_player");
        log.debug(message, player);
        PlayerEntity playerEntity = PlayerEntityMapper.INSTANCE.mapPlayerEntity(player);
        playerEntity = playerService.update(playerEntity);
        return new ResponseEntity<>(PlayerMapper.INSTANCE.mapPlayer(playerEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /players/:id : delete an player.
     *
     * @param id the player to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new player, or with status 400 (Bad Request) if the player has already an ID
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") long id) {
        PlayerEntity playerEntity = playerService.getPlayer(id);
        String message = bundle.getString("delete_player");
        log.debug(message, id);
        playerService.delete(id);
        return new ResponseEntity<>(PlayerMapper.INSTANCE.mapPlayer(playerEntity), HttpStatus.ACCEPTED);
    }
}
