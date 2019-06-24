package com.apside.prono.controller;

import com.apside.prono.mapper.histoCalculs.HistoCalculsEntityMapper;
import com.apside.prono.mapper.histoCalculs.HistoCalculsMapper;
import com.apside.prono.model.HistoCalculsEntity;
import com.apside.prono.modelapi.HistoCalculs;
import com.apside.prono.service.HistoCalculsService;
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
public class HistoCalculsController {
    private final Logger log = LoggerFactory.getLogger(HistoCalculsController.class);

    @Autowired
    private HistoCalculsService histoCalculsService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /histoCalculs : get all histoCalculs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of histoCalculs in body
     */
    @GetMapping("/histoCalculs")
    public List<HistoCalculsEntity> getAllHistoCalculs() {
        log.debug(bundle.getString("get_all_histo_calculs"));
        return histoCalculsService.getAll();
    }

    /**
     * GET  /histoCalculs/:id : get the "id" histoCalculs.
     *
     * @param id the id of the histoCalculs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the histoCalculs, or with status 404 (Not Found)
     */
    @GetMapping("/histoCalculs/{id}")
    public ResponseEntity<?> getHistoCalculs(@PathVariable("id") long id) {
        String message = bundle.getString("get_histo_calculs");
        log.debug(message, id);
        HistoCalculsEntity histoCalculsEntity = histoCalculsService.getHistoCalculs(id);
        return ResponseEntity.ok().body(HistoCalculsMapper.INSTANCE.mapHistoCalculs(histoCalculsEntity));
    }

    /**
     * POST  /histoCalculs/ : Create a new histoCalculs.
     *
     * @param histoCalculs the histoCalculs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new histoCalculs, or with status 400 (Bad Request) if the histoCalculs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/histoCalculs")
    public ResponseEntity<?> createHistoCalculs(@Valid @RequestBody HistoCalculs histoCalculs) throws URISyntaxException {
        String message = bundle.getString("post_histo_calculs");
        log.debug(message, histoCalculs);
        HistoCalculsEntity histoCalculsEntity = HistoCalculsEntityMapper.INSTANCE.mapHistoCalculsEntity(histoCalculs);
        histoCalculsEntity = histoCalculsService.createHistoCalculs(histoCalculsEntity);
        return new ResponseEntity<>(HistoCalculsMapper.INSTANCE.mapHistoCalculs(histoCalculsEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /histoCalculs/ : update an histoCalculs.
     *
     * @param histoCalculs the histoCalculs to update
     * @return the ResponseEntity with status 201 (Update) and with body the new histoCalculs, or with status 400 (Bad Request) if the histoCalculs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/histoCalculs")
    public ResponseEntity<?> updateHistoCalculs(@Valid @RequestBody HistoCalculs histoCalculs) throws URISyntaxException {
        String message = bundle.getString("put_histo_calculs");
        log.debug(message, histoCalculs);
        HistoCalculsEntity histoCalculsEntity = HistoCalculsEntityMapper.INSTANCE.mapHistoCalculsEntity(histoCalculs);
        histoCalculsEntity = histoCalculsService.update(histoCalculsEntity);
        return new ResponseEntity<>(HistoCalculsMapper.INSTANCE.mapHistoCalculs(histoCalculsEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /histoCalculs/:id : delete an histoCalculs.
     *
     * @param id the histoCalculs to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new histoCalculs, or with status 400 (Bad Request) if the histoCalculs has already an ID
     */
    @DeleteMapping("/histoCalculs/{id}")
    public ResponseEntity<?> deleteHistoCalculs(@PathVariable("id") long id) {
        HistoCalculsEntity histoCalculsEntity = histoCalculsService.getHistoCalculs(id);

        String message = bundle.getString("delete_histo_calculs");
        log.debug(message, id);

        histoCalculsService.delete(id);
        return new ResponseEntity<>(HistoCalculsMapper.INSTANCE.mapHistoCalculs(histoCalculsEntity), HttpStatus.ACCEPTED);
    }
}
