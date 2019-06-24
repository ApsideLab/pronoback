package com.apside.prono.controller;

import com.apside.prono.mapper.prono.PronoEntityMapper;
import com.apside.prono.mapper.prono.PronoMapper;
import com.apside.prono.model.PronoEntity;
import com.apside.prono.modelapi.Prono;
import com.apside.prono.service.PronoService;
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
public class PronoController {
    private final Logger log = LoggerFactory.getLogger(PronoController.class);

    @Autowired
    private PronoService pronoService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /prono : get all prono.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prono in body
     */
    @GetMapping("/prono")
    public List<PronoEntity> getAllProno() {
        log.debug(bundle.getString("get_all_pronos"));
        return pronoService.getAll();
    }

    /**
     * GET  /prono/:id : get the "id" prono.
     *
     * @param id the id of the prono to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prono, or with status 404 (Not Found)
     */
    @GetMapping("/prono/{id}")
    public ResponseEntity<?> getProno(@PathVariable("id") long id) {
        String message = bundle.getString("get_prono");
        log.debug(message, id);
        PronoEntity pronoEntity = pronoService.getProno(id);
        return ResponseEntity.ok().body(PronoMapper.INSTANCE.mapProno(pronoEntity));
    }

    /**
     * POST  /prono/ : Create a new prono.
     *
     * @param prono the prono to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prono, or with status 400 (Bad Request) if the prono has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prono")
    public ResponseEntity<?> createProno(@Valid @RequestBody Prono prono) throws URISyntaxException {
        String message = bundle.getString("post_prono");
        log.debug(message, prono);
        PronoEntity pronoEntity = PronoEntityMapper.INSTANCE.mapPronoEntity(prono);
        pronoEntity = pronoService.createProno(pronoEntity);
        return new ResponseEntity<>(PronoMapper.INSTANCE.mapProno(pronoEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /prono/ : update an prono.
     *
     * @param prono the prono to update
     * @return the ResponseEntity with status 201 (Update) and with body the new prono, or with status 400 (Bad Request) if the prono has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prono")
    public ResponseEntity<?> updateProno(@Valid @RequestBody Prono prono) throws URISyntaxException {
        String message = bundle.getString("put_prono");
        log.debug(message, prono);
        PronoEntity pronoEntity = PronoEntityMapper.INSTANCE.mapPronoEntity(prono);
        pronoEntity = pronoService.update(pronoEntity);
        return new ResponseEntity<>(PronoMapper.INSTANCE.mapProno(pronoEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /prono/:id : delete an prono.
     *
     * @param id the prono to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new prono, or with status 400 (Bad Request) if the prono has already an ID
     */
    @DeleteMapping("/prono/{id}")
    public ResponseEntity<?> deleteProno(@PathVariable("id") long id) {
        PronoEntity pronoEntity = pronoService.getProno(id);
        String message = bundle.getString("delete_prono");
        log.debug(message, id);
        pronoService.delete(id);
        return new ResponseEntity<>(PronoMapper.INSTANCE.mapProno(pronoEntity), HttpStatus.ACCEPTED);
    }
}
