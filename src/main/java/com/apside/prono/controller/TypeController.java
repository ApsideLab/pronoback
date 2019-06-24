package com.apside.prono.controller;


import com.apside.prono.mapper.type.TypeEntityMapper;
import com.apside.prono.mapper.type.TypeMapper;
import com.apside.prono.model.TypeEntity;
import com.apside.prono.modelapi.Type;
import com.apside.prono.service.TypeService;
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
public class TypeController {
    private final Logger log = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    private TypeService typeService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /types : get all type.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of types in body
     */
    @GetMapping("/types")
    public List<TypeEntity> getAllTypes() {
        log.debug(bundle.getString("get_all_types"));
        return typeService.getAll();
    }

    /**
     * GET  /types/:id : get the "id" type.
     *
     * @param id the id of the type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the type, or with status 404 (Not Found)
     */
    @GetMapping("/types/{id}")
    public ResponseEntity<?> getType(@PathVariable("id") long id) {
        String message = bundle.getString("get_type");
        log.debug(message, id);

        TypeEntity typeEntity = typeService.getType(id);
        return ResponseEntity.ok().body(TypeMapper.INSTANCE.mapType(typeEntity));
    }

    /**
     * POST  /types/ : Create a new type.
     *
     * @param type the type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new type, or with status 400 (Bad Request) if the type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/types")
    public ResponseEntity<?> createType(@Valid @RequestBody Type type) throws URISyntaxException {
        String message = bundle.getString("post_type");
        log.debug(message, type);
        TypeEntity typeEntity = TypeEntityMapper.INSTANCE.mapTypeEntity(type);
        typeEntity = typeService.createType(typeEntity);
        return new ResponseEntity<>(TypeMapper.INSTANCE.mapType(typeEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /types/ : update an type.
     *
     * @param type the type to update
     * @return the ResponseEntity with status 201 (Update) and with body the new type, or with status 400 (Bad Request) if the type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/types")
    public ResponseEntity<?> updateType(@Valid @RequestBody Type type) throws URISyntaxException {
        String message = bundle.getString("put_type");
        log.debug(message, type);
        TypeEntity typeEntity = TypeEntityMapper.INSTANCE.mapTypeEntity(type);
        typeEntity = typeService.update(typeEntity);
        return new ResponseEntity<>(TypeMapper.INSTANCE.mapType(typeEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /types/:id : delete an type.
     *
     * @param id the type to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new type, or with status 400 (Bad Request) if the type has already an ID
     */
    @DeleteMapping("/types/{id}")
    public ResponseEntity<?> deleteType(@PathVariable("id") long id) {
        TypeEntity typeEntity = typeService.getType(id);
        String message = bundle.getString("delete_type");
        log.debug(message, typeEntity);
        typeService.delete(id);
        return new ResponseEntity<>(TypeMapper.INSTANCE.mapType(typeEntity), HttpStatus.ACCEPTED);
    }
}
