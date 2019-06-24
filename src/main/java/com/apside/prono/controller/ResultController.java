package com.apside.prono.controller;

import com.apside.prono.mapper.result.ResultEntityMapper;
import com.apside.prono.mapper.result.ResultMapper;
import com.apside.prono.model.ResultEntity;
import com.apside.prono.modelapi.Result;
import com.apside.prono.service.ResultService;
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
public class ResultController {
    private final Logger log = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");
    /**
     * GET  /result : get all result.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of result in body
     */
    @GetMapping("/results")
    public List<ResultEntity> getAllResult() {
        log.debug(bundle.getString("get_all_results"));
        return resultService.getAll();
    }

    /**
     * GET  /result/:id : get the "id" result.
     *
     * @param id the id of the result to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the result, or with status 404 (Not Found)
     */
    @GetMapping("/results/{id}")
    public ResponseEntity<?> getResult(@PathVariable("id") long id) {
        String message = bundle.getString("get_result");
        log.debug(message, id);
        ResultEntity resultEntity = resultService.getResult(id);
        return ResponseEntity.ok().body(ResultMapper.INSTANCE.mapResult(resultEntity));
    }

    /**
     * POST  /results/ : Create a new result.
     *
     * @param result the result to create
     * @return the ResponseEntity with status 201 (Created) and with body the new result, or with status 400 (Bad Request) if the result has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/results")
    public ResponseEntity<?> createResult(@Valid @RequestBody Result result) throws URISyntaxException {
        String message = bundle.getString("post_result");
        log.debug(message, result);
        ResultEntity resultEntity = ResultEntityMapper.INSTANCE.mapResultEntity(result);
        resultEntity = resultService.createResult(resultEntity);
        return new ResponseEntity<>(ResultMapper.INSTANCE.mapResult(resultEntity), HttpStatus.CREATED);
    }

    /**
     * PUT  /results/ : update an result.
     *
     * @param result the result to update
     * @return the ResponseEntity with status 201 (Update) and with body the new result, or with status 400 (Bad Request) if the result has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/results")
    public ResponseEntity<?> updateResult(@Valid @RequestBody Result result) throws URISyntaxException {
        String message = bundle.getString("put_result");
        log.debug(message, result);
        ResultEntity resultEntity = ResultEntityMapper.INSTANCE.mapResultEntity(result);
        resultEntity = resultService.update(resultEntity);
        return new ResponseEntity<>(ResultMapper.INSTANCE.mapResult(resultEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /result/:id : delete an result.
     *
     * @param id the result to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new result, or with status 400 (Bad Request) if the result has already an ID
     */
    @DeleteMapping("/results/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable("id") long id) {
        ResultEntity resultEntity = resultService.getResult(id);
        String message = bundle.getString("delete_result");
        log.debug(message, id);
        resultService.delete(id);
        return new ResponseEntity<>(ResultMapper.INSTANCE.mapResult(resultEntity), HttpStatus.ACCEPTED);
    }
}
