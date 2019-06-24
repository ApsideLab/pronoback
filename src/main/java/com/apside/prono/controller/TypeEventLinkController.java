package com.apside.prono.controller;

import com.apside.prono.mapper.TypeEventLink.TypeEventLinkEntityMapper;
import com.apside.prono.mapper.TypeEventLink.TypeEventLinkMapper;
import com.apside.prono.model.TypeEventLinkEntity;
import com.apside.prono.modelapi.TypeEventLink;
import com.apside.prono.service.TypeEventLinkService;
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
public class TypeEventLinkController {
    private final Logger log = LoggerFactory.getLogger(TypeEventLinkController.class);

    @Autowired
    private TypeEventLinkService typeEventLinkService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /typeEventLink : get all typeEventLink.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeEventLink in body
     */
    @GetMapping("/typeEventLink")
    public List<TypeEventLinkEntity> getAllTypeEventLink() {
        log.debug(bundle.getString("get_all_type_event_link"));
        return typeEventLinkService.getAll();
    }

    /**
     * GET  /typeEventLink/:id : get the "id" typeEventLink.
     *
     * @param id the id of the typeEventLink to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEventLink, or with status 404 (Not Found)
     */
    @GetMapping("/typeEventLink/{id}")
    public ResponseEntity<?> getTypeEventLink(@PathVariable("id") long id) {
        String message = bundle.getString("get_type_event_link");
        log.debug(message, id);
        TypeEventLinkEntity typeEventLinkEntity = typeEventLinkService.getTypeEventLink(id);
        return ResponseEntity.ok().body(TypeEventLinkMapper.INSTANCE.mapTypeEventLink(typeEventLinkEntity));
    }

    /**
     * POST  /typeEventLink/ : Create a new typeEventLink.
     *
     * @param typeEventLink the typeEventLink to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEventLink, or with status 400 (Bad Request) if the typeEventLink has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/typeEventLink")
    public ResponseEntity<?> createTypeEventLink(@Valid @RequestBody TypeEventLink typeEventLink) throws URISyntaxException {
        String message = bundle.getString("post_type_event_link");
        log.debug(message, typeEventLink);
        TypeEventLinkEntity typeEventLinkEntity = TypeEventLinkEntityMapper.INSTANCE.mapTypeEventLinkEntity(typeEventLink);
        typeEventLinkEntity = typeEventLinkService.createTypeEventLink(typeEventLinkEntity);
        return new ResponseEntity<>(TypeEventLinkMapper.INSTANCE.mapTypeEventLink(typeEventLinkEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /typeEventLink/ : update an typeEventLink.
     *
     * @param typeEventLink the typeEventLink to update
     * @return the ResponseEntity with status 201 (Update) and with body the new typeEventLink, or with status 400 (Bad Request) if the typeEventLink has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/typeEventLink")
    public ResponseEntity<?> updateTypeEventLink(@Valid @RequestBody TypeEventLink typeEventLink) throws URISyntaxException {
        String message = bundle.getString("put_type_event_link");
        log.debug(message, typeEventLink);
        TypeEventLinkEntity typeEventLinkEntity = TypeEventLinkEntityMapper.INSTANCE.mapTypeEventLinkEntity(typeEventLink);
        typeEventLinkEntity = typeEventLinkService.update(typeEventLinkEntity);
        return new ResponseEntity<>(TypeEventLinkMapper.INSTANCE.mapTypeEventLink(typeEventLinkEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /typeEventLink/:id : delete an typeEventLink.
     *
     * @param id the typeEventLink to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new typeEventLink, or with status 400 (Bad Request) if the typeEventLink has already an ID
     */
    @DeleteMapping("/typeEventLink/{id}")
    public ResponseEntity<?> deleteTypeEventLink(@PathVariable("id") long id) {
        TypeEventLinkEntity typeEventLinkEntity = typeEventLinkService.getTypeEventLink(id);
        String message = bundle.getString("delete_type_event_link");
        log.debug(message, id);
        typeEventLinkService.delete(id);
        return new ResponseEntity<>(TypeEventLinkMapper.INSTANCE.mapTypeEventLink(typeEventLinkEntity), HttpStatus.ACCEPTED);
    }
}
