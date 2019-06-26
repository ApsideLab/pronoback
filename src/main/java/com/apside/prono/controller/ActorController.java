package com.apside.prono.controller;

import com.apside.prono.mapper.actor.ActorEntityMapper;
import com.apside.prono.mapper.actor.ActorMapper;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.modelapi.Actor;
import com.apside.prono.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
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
public class ActorController {
    private final Logger log = LoggerFactory.getLogger(ActorController.class);
    @Autowired
    private ActorService actorService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /actors : get all actor.
     *
     * @return the Response with status 200 (OK) and the list of actors in body
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/actors")
    public List<ActorEntity> getAllActors() {
        log.debug(bundle.getString("get_all_actors"));
        return actorService.getAll();
    }

    /**
     * GET  /actors/:id : get the "id" actor.
     *
     * @param id the id of the actor to retrieve
     * @return the Response with status 200 (OK) and with body the actor, or with status 404 (Not Found)
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/actors/{id}")
    public ResponseEntity<?> getActor(@PathVariable("id") long id) {
        String message = bundle.getString("get_actor");
        log.debug(message, id);

        ActorEntity actor = actorService.getActor(id);
        return ResponseEntity.ok().body(ActorMapper.INSTANCE.mapActor(actor));
    }

    /**
     * POST  /actors/ : Create a new actor.
     *
     * @param actor the actor to create
     * @return the Response with status 201 (Created) and with body the new actor, or with status 400 (Bad Request) if the actor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/actors")
    public ResponseEntity<?> createActor(@Valid @RequestBody Actor actor) throws URISyntaxException {
        String message = bundle.getString("post_actor");
        log.debug(message, actor);

        ActorEntity actorEntity = ActorEntityMapper.INSTANCE.mapActorEntity(actor);
        actorEntity = actorService.createActor(actorEntity);
        return new ResponseEntity<>(ActorMapper.INSTANCE.mapActor(actorEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /actors/ : update an actor.
     *
     * @param actor the actor to update
     * @return the Response with status 201 (Update) and with body the new actor, or with status 400 (Bad Request) if the actor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/actors")
    public ResponseEntity<?> updateActor(@Valid @RequestBody Actor actor) throws URISyntaxException {
        String message = bundle.getString("put_actor");
        log.debug(message, actor);

        ActorEntity actorEntity = ActorEntityMapper.INSTANCE.mapActorEntity(actor);
        actorEntity = actorService.update(actorEntity);
        return new ResponseEntity<>(ActorMapper.INSTANCE.mapActor(actorEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /actors/:id : delete an actor.
     *
     * @param id the actor to delete
     * @return the Response with status 201 (deleted) and with body the new actor, or with status 400 (Bad Request) if the actor has already an ID
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/actors/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable("id") long id) {
        ActorEntity actorEntity = actorService.getActor(id);

        String message = bundle.getString("delete_actor");
        log.debug(message, id);

        actorService.delete(id);
        return new ResponseEntity<>(ActorMapper.INSTANCE.mapActor(actorEntity), HttpStatus.ACCEPTED);
    }
}
