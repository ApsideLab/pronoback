package com.apside.prono.controller;

import com.apside.prono.mapper.histoRanking.HistoRankingEntityMapper;
import com.apside.prono.mapper.histoRanking.HistoRankingMapper;
import com.apside.prono.model.HistoRankingEntity;
import com.apside.prono.modelapi.HistoRanking;
import com.apside.prono.service.HistoRankingService;
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
public class HistoRankingController {
    private final Logger log = LoggerFactory.getLogger(HistoRankingController.class);

    @Autowired
    private HistoRankingService histoRankingService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");
    /**
     * GET  /histoRanking : get all histoRanking.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of histoRanking in body
     */
    @GetMapping("/histoRanking")
    public List<HistoRankingEntity> getAllHistoRanking() {
        log.debug(bundle.getString("get_all_histo_ranking"));
        return histoRankingService.getAll();
    }

    /**
     * GET  /histoRanking/:id : get the "id" histoRanking.
     *
     * @param id the id of the histoRanking to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the histoRanking, or with status 404 (Not Found)
     */
    @GetMapping("/histoRanking/{id}")
    public ResponseEntity<?> getHistoRanking(@PathVariable("id") long id) {
        String message = bundle.getString("get_histo_ranking");
        log.debug(message, id);
        HistoRankingEntity histoRankingEntity = histoRankingService.getHistoRanking(id);
        return ResponseEntity.ok().body(HistoRankingMapper.INSTANCE.mapHistoRanking(histoRankingEntity));
    }

    /**
     * POST  /histoRanking/ : Create a new histoRanking.
     *
     * @param histoRanking the histoRanking to create
     * @return the ResponseEntity with status 201 (Created) and with body the new histoRanking, or with status 400 (Bad Request) if the histoRanking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/histoRanking")
    public ResponseEntity<?> createHistoRanking(@Valid @RequestBody HistoRanking histoRanking) throws URISyntaxException {
        String message = bundle.getString("post_histo_ranking");
        log.debug(message, histoRanking);
        HistoRankingEntity histoRankingEntity = HistoRankingEntityMapper.INSTANCE.mapHistoRankingEntity(histoRanking);
        histoRankingEntity = histoRankingService.createHistoRanking(histoRankingEntity);
        return new ResponseEntity<>(HistoRankingMapper.INSTANCE.mapHistoRanking(histoRankingEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /histoRanking/ : update an histoRanking.
     *
     * @param histoRanking the histoRanking to update
     * @return the ResponseEntity with status 201 (Update) and with body the new histoRanking, or with status 400 (Bad Request) if the histoRanking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/histoRanking")
    public ResponseEntity<?> updateHistoRanking(@Valid @RequestBody HistoRanking histoRanking) throws URISyntaxException {
        String message = bundle.getString("put_histo_ranking");
        log.debug(message, histoRanking);
        HistoRankingEntity histoRankingEntity = HistoRankingEntityMapper.INSTANCE.mapHistoRankingEntity(histoRanking);
        histoRankingEntity = histoRankingService.update(histoRankingEntity);
        return new ResponseEntity<>(HistoRankingMapper.INSTANCE.mapHistoRanking(histoRankingEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /histoRanking/:id : delete an histoRanking.
     *
     * @param id the histoRanking to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new histoRanking, or with status 400 (Bad Request) if the histoRanking has already an ID
     */
    @DeleteMapping("/histoRanking/{id}")
    public ResponseEntity<?> deleteHistoRanking(@PathVariable("id") long id) {
        HistoRankingEntity histoRankingEntity = histoRankingService.getHistoRanking(id);
        String message = bundle.getString("delete_histo_ranking");
        log.debug(message, id);
        histoRankingService.delete(id);
        return new ResponseEntity<>(HistoRankingMapper.INSTANCE.mapHistoRanking(histoRankingEntity), HttpStatus.ACCEPTED);
    }
}
