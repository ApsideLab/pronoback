package com.apside.prono.controller;


import com.apside.prono.mapper.scale.ScaleEntityMapper;
import com.apside.prono.mapper.scale.ScaleMapper;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
import com.apside.prono.service.ScaleService;
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
public class ScaleController {
    private final Logger log = LoggerFactory.getLogger(ScaleController.class);

    @Autowired
    private ScaleService scaleService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /scales : get all scale.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scales in body
     */
    @GetMapping("/scales")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ScaleEntity> getAllScales() {
        log.debug(bundle.getString("get_all_scales"));
        return scaleService.getAll();
    }

    /**
     * GET  /scales/:id : get the "id" scale.
     *
     * @param id the id of the scale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scale, or with status 404 (Not Found)
     */
    @GetMapping("/scales/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> getScale(@PathVariable("id") long id) {
        String message = bundle.getString("get_scale");
        log.debug(message, id);

        Scale scale = scaleService.getScale(id);
        return ResponseEntity.ok().body(scale);
    }

    /**
     * POST  /scales/ : Create a new scale.
     *
     * @param scale the scale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scale, or with status 400 (Bad Request) if the scale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scales")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> createScale(@Valid @RequestBody Scale scale){
        String message = bundle.getString("post_scale");
        log.debug(message, scale);

        Scale scaleSave = scaleService.createScale(scale);
        return new ResponseEntity<>(scaleSave, HttpStatus.CREATED);
    }

    /**
     * PUT  /scales/ : update an scale.
     *
     * @param scale the scale to update
     * @return the ResponseEntity with status 201 (Update) and with body the new scale, or with status 400 (Bad Request) if the scale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scales")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> updateScale(@Valid @RequestBody Scale scale) throws URISyntaxException {
        String message = bundle.getString("put_scale");
        log.debug(message, scale);

        ScaleEntity scaleEntity = scaleService.update(scale);
        return new ResponseEntity<>(ScaleMapper.INSTANCE.mapScale(scaleEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /scales/:id : delete an scale.
     *
     * @param id the scale to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new scale, or with status 400 (Bad Request) if the scale has already an ID
     */
    @DeleteMapping("/scales/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> deleteScale(@PathVariable("id") long id) {
        String message = bundle.getString("delete_scale");
        log.debug(message, id);
        scaleService.delete(id);
        return ResponseEntity.ok().body(id);
    }
}
