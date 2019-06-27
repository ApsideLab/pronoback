package com.apside.prono.controller;


import com.apside.prono.mapper.EvenementEntityMapper;
import com.apside.prono.mapper.EvenementMapper;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.modelapi.Evenement;
import com.apside.prono.service.EvenementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;

    private final Logger log = LoggerFactory.getLogger(EvenementController.class);

    /**
     * GET  /evenements : get all événements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evenements in body
     */
    //TODO : Changer EvenementEntity en Evenement
    @GetMapping("/evenements")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EvenementEntity> getAllEvenements() {
        log.debug("REST requete pour récupérer tous les événements");
        return evenementService.getAll();
    }

    /**
     * GET  /evenements/:id : get the "id" evenement.
     *
     * @param id the id of the evenement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evenement, or with status 404 (Not Found)
     */
    @GetMapping("/evenements/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Evenement> getEvenement(@PathVariable Long id) {
        log.debug("REST request to get EvenementEntity : {}", id);
        EvenementEntity evenementEntity = evenementService.getEvenement(id);
        return ResponseEntity.ok().body(EvenementMapper.INSTANCE.mapEvenement(evenementEntity));
    }

    /**
     * POST  /evenement : Create a new evenement.
     *
     * @param evenement the evenement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evenement, or with status 400 (Bad Request) if the evenement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evenements")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Evenement> createEvenement(@Valid @RequestBody Evenement evenement){
        log.debug("REST request to save Evenement : {}", evenement);
        EvenementEntity evenementEntity = EvenementEntityMapper.INSTANCE.mapEvenementEntity(evenement);
        evenementEntity = evenementService.createEvent(evenementEntity);
        return new ResponseEntity<>(EvenementMapper.INSTANCE.mapEvenement(evenementEntity), HttpStatus.CREATED);
    }
}
