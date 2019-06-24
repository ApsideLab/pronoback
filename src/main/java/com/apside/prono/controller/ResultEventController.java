package com.apside.prono.controller;

import com.apside.prono.mapper.resultEvent.ResultEventEntityMapper;
import com.apside.prono.mapper.resultEvent.ResultEventMapper;
import com.apside.prono.model.ResultEventEntity;
import com.apside.prono.modelapi.ResultEvent;
import com.apside.prono.service.ResultEventService;
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
public class ResultEventController {
    private final Logger log = LoggerFactory.getLogger(ResultEventController.class);

    @Autowired
    private ResultEventService resultEventService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /resultEvent : get all resultEvent.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resultEvent in body
     */
    @GetMapping("/resultEvent")
    public List<ResultEventEntity> getAllResultEvent() {
        log.debug(bundle.getString("get_all_result_event"));
        return resultEventService.getAll();
    }

    /**
     * GET  /resultEvent/:id : get the "id" resultEvent.
     *
     * @param id the id of the resultEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultEvent, or with status 404 (Not Found)
     */
    @GetMapping("/resultEvent/{id}")
    public ResponseEntity<?> getResultEvent(@PathVariable("id") long id) {
        String message = bundle.getString("get_result_event");
        log.debug(message, id);
        ResultEventEntity resultEventEntity = resultEventService.getResultEvent(id);
        return ResponseEntity.ok().body(ResultEventMapper.INSTANCE.mapResultEvent(resultEventEntity));
    }

    /**
     * POST  /resultEvent/ : Create a new resultEvent.
     *
     * @param resultEvent the resultEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultEvent, or with status 400 (Bad Request) if the resultEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultEvent")
    public ResponseEntity<?> createResultEvent(@Valid @RequestBody ResultEvent resultEvent) throws URISyntaxException {
        String message = bundle.getString("post_result_event");
        log.debug(message, resultEvent);
        ResultEventEntity resultEventEntity = ResultEventEntityMapper.INSTANCE.mapResultEventEntity(resultEvent);
        resultEventEntity = resultEventService.createResultEvent(resultEventEntity);
        return new ResponseEntity<>(ResultEventMapper.INSTANCE.mapResultEvent(resultEventEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /resultEvent/ : update an resultEvent.
     *
     * @param resultEvent the resultEvent to update
     * @return the ResponseEntity with status 201 (Update) and with body the new resultEvent, or with status 400 (Bad Request) if the resultEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultEvent")
    public ResponseEntity<?> updateResultEvent(@Valid @RequestBody ResultEvent resultEvent) throws URISyntaxException {
        String message = bundle.getString("put_result_event");
        log.debug(message, resultEvent);
        ResultEventEntity resultEventEntity = ResultEventEntityMapper.INSTANCE.mapResultEventEntity(resultEvent);
        resultEventEntity = resultEventService.update(resultEventEntity);
        return new ResponseEntity<>(ResultEventMapper.INSTANCE.mapResultEvent(resultEventEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /resultEvent/:id : delete an resultEvent.
     *
     * @param id the resultEvent to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new resultEvent, or with status 400 (Bad Request) if the resultEvent has already an ID
     */
    @DeleteMapping("/resultEvent/{id}")
    public ResponseEntity<?> deleteResultEvent(@PathVariable("id") long id) {
        ResultEventEntity resultEventEntity = resultEventService.getResultEvent(id);
        String message = bundle.getString("delete_result_event");
        log.debug(message, resultEventEntity);
        resultEventService.delete(id);
        return new ResponseEntity<>(ResultEventMapper.INSTANCE.mapResultEvent(resultEventEntity), HttpStatus.ACCEPTED);
    }
}
