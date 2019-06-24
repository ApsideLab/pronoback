package com.apside.prono.controller;

import com.apside.prono.mapper.pronoPlayer.PronoPlayerEntityMapper;
import com.apside.prono.mapper.pronoPlayer.PronoPlayerMapper;
import com.apside.prono.model.PronoPlayerEntity;
import com.apside.prono.modelapi.PronoPlayer;
import com.apside.prono.service.PronoPlayerService;
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
public class PronoPlayerController {
    private final Logger log = LoggerFactory.getLogger(PronoPlayerController.class);

    @Autowired
    private PronoPlayerService pronoPlayerService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /pronoPlayer : get all pronoPlayer.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pronoPlayer in body
     */
    @GetMapping("/pronoPlayer")
    public List<PronoPlayerEntity> getAllPronoPlayer() {
        log.debug(bundle.getString("get_all_pronos_players"));
        return pronoPlayerService.getAll();
    }

    /**
     * GET  /pronoPlayer/:id : get the "id" pronoPlayer.
     *
     * @param id the id of the pronoPlayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pronoPlayer, or with status 404 (Not Found)
     */
    @GetMapping("/pronoPlayer/{id}")
    public ResponseEntity<?> getPronoPlayer(@PathVariable("id") long id) {
        String message = bundle.getString("get_prono_players");
        log.debug(message, id);
        PronoPlayerEntity pronoPlayerEntity = pronoPlayerService.getPronoPlayer(id);
        return ResponseEntity.ok().body(PronoPlayerMapper.INSTANCE.mapPronoPlayer(pronoPlayerEntity));
    }

    /**
     * POST  /pronoPlayer/ : Create a new pronoPlayer.
     *
     * @param pronoPlayer the pronoPlayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pronoPlayer, or with status 400 (Bad Request) if the pronoPlayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pronoPlayer")
    public ResponseEntity<?> createPronoPlayer(@Valid @RequestBody PronoPlayer pronoPlayer) throws URISyntaxException {
        String message = bundle.getString("post_prono_players");
        log.debug(message, pronoPlayer);
        PronoPlayerEntity pronoPlayerEntity = PronoPlayerEntityMapper.INSTANCE.mapPronoPlayerEntity(pronoPlayer);
        pronoPlayerEntity = pronoPlayerService.createPronoPlayer(pronoPlayerEntity);
        return new ResponseEntity<>(PronoPlayerMapper.INSTANCE.mapPronoPlayer(pronoPlayerEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /pronoPlayer/ : update an pronoPlayer.
     *
     * @param pronoPlayer the pronoPlayer to update
     * @return the ResponseEntity with status 201 (Update) and with body the new pronoPlayer, or with status 400 (Bad Request) if the pronoPlayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pronoPlayer")
    public ResponseEntity<?> updatePronoPlayer(@Valid @RequestBody PronoPlayer pronoPlayer) throws URISyntaxException {
        String message = bundle.getString("put_prono_players");
        log.debug(message, pronoPlayer);
        PronoPlayerEntity pronoPlayerEntity = PronoPlayerEntityMapper.INSTANCE.mapPronoPlayerEntity(pronoPlayer);
        pronoPlayerEntity = pronoPlayerService.update(pronoPlayerEntity);
        return new ResponseEntity<>(PronoPlayerMapper.INSTANCE.mapPronoPlayer(pronoPlayerEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /pronoPlayer/:id : delete an pronoPlayer.
     *
     * @param id the pronoPlayer to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new pronoPlayer, or with status 400 (Bad Request) if the pronoPlayer has already an ID
     */
    @DeleteMapping("/pronoPlayer/{id}")
    public ResponseEntity<?> deletePronoPlayer(@PathVariable("id") long id) {
        PronoPlayerEntity pronoPlayerEntity = pronoPlayerService.getPronoPlayer(id);
        String message = bundle.getString("delete_prono_players");
        log.debug(message, id);
        pronoPlayerService.delete(id);
        return new ResponseEntity<>(PronoPlayerMapper.INSTANCE.mapPronoPlayer(pronoPlayerEntity), HttpStatus.ACCEPTED);
    }
}
