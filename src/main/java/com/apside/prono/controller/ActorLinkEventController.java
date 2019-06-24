package com.apside.prono.controller;

import com.apside.prono.mapper.actorLinkEvent.ActorLinkEventEntityMapper;
import com.apside.prono.mapper.actorLinkEvent.ActorLinkEventMapper;
import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.modelapi.ActorLinkEvent;
import com.apside.prono.service.ActorLinkEventService;
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
public class ActorLinkEventController {
    private final Logger log = LoggerFactory.getLogger(ActorLinkEventController.class);

    @Autowired
    private ActorLinkEventService actorLinkEventService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /actorLinkEvent : get all actorLinkEvent.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actorLinkEvent in body
     */
    @GetMapping("/actorLinkEvent")
    public List<ActorLinkEventEntity> getAllActorLinkEvent() {
        log.debug(bundle.getString("get_all_actor_link_event"));
        return actorLinkEventService.getAll();
    }

    /**
     * GET  /actorLinkEvent/:id : get the "id" actorLinkEvent.
     *
     * @param id the id of the actorLinkEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actorLinkEvent, or with status 404 (Not Found)
     */
    @GetMapping("/actorLinkEvent/{id}")
    public ResponseEntity<?> getActorLinkEvent(@PathVariable("id") long id) {
        String message = bundle.getString("get_actor_link_event");
        log.debug(message, id);

        ActorLinkEventEntity actorLinkEventEntity = actorLinkEventService.getActorLinkEvent(id);
        return ResponseEntity.ok().body(ActorLinkEventMapper.INSTANCE.mapActorLinkEvent(actorLinkEventEntity));
    }

    /**
     * POST  /actorLinkEvent/ : Create a new actorLinkEvent.
     *
     * @param actorLinkEvent the actorLinkEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actorLinkEvent, or with status 400 (Bad Request) if the actorLinkEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actorLinkEvent")
    public ResponseEntity<ActorLinkEvent> createActorLinkEvent(@Valid @RequestBody ActorLinkEvent actorLinkEvent){
        String message = bundle.getString("post_actor_link_event");
        log.debug(message, actorLinkEvent);

        ActorLinkEventEntity actorLinkEventEntity = ActorLinkEventEntityMapper.INSTANCE.mapActorLinkEventEntity(actorLinkEvent);
        actorLinkEventEntity = actorLinkEventService.createActorLinkEvent(actorLinkEventEntity);
        return new ResponseEntity<>(ActorLinkEventMapper.INSTANCE.mapActorLinkEvent(actorLinkEventEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /actorLinkEvent/ : update an actorLinkEvent.
     *
     * @param actorLinkEvent the actorLinkEvent to update
     * @return the ResponseEntity with status 201 (Update) and with body the new actorLinkEvent, or with status 400 (Bad Request) if the actorLinkEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actorLinkEvent")
    public ResponseEntity<?> updateActorLinkEvent(@Valid @RequestBody ActorLinkEvent actorLinkEvent) throws URISyntaxException {
        String message = bundle.getString("put_actor_link_event");
        log.debug(message, actorLinkEvent);
        ActorLinkEventEntity actorLinkEventEntity = ActorLinkEventEntityMapper.INSTANCE.mapActorLinkEventEntity(actorLinkEvent);
        actorLinkEventEntity = actorLinkEventService.update(actorLinkEventEntity);
        return new ResponseEntity<>(ActorLinkEventMapper.INSTANCE.mapActorLinkEvent(actorLinkEventEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /actorLinkEvent/:id : delete an actorLinkEvent.
     *
     * @param id the actorLinkEvent to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new actorLinkEvent, or with status 400 (Bad Request) if the actorLinkEvent has already an ID
     */
    @DeleteMapping("/actorLinkEvent/{id}")
    public ResponseEntity<?> deleteActorLinkEvent(@PathVariable("id") long id) {
        ActorLinkEventEntity actorLinkEvent = actorLinkEventService.getActorLinkEvent(id);

        String message = bundle.getString("delete_actor_link_event");
        log.debug(message, id);

        actorLinkEventService.delete(id);
        return new ResponseEntity<>(ActorLinkEventMapper.INSTANCE.mapActorLinkEvent(actorLinkEvent), HttpStatus.ACCEPTED);
    }
}
