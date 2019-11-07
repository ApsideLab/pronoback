package com.apside.prono.controller;

import com.apside.prono.mapper.contest.ContestEntityMapper;
import com.apside.prono.mapper.contest.ContestMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import com.apside.prono.service.ContestService;
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
public class ContestController {
    private final Logger log = LoggerFactory.getLogger(ContestController.class);
    @Autowired
    private ContestService contestService;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesControllerError");

    /**
     * GET  /contest : get all contest.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contest in body
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/contests")
    public List<ContestEntity> getAllContests() {
        log.debug(bundle.getString("get_all_contests"));
        return contestService.getAll();
    }

    /**
     * GET  /contest/:id : get the "id" contest.
     *
     * @param id the id of the contest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contest, or with status 404 (Not Found)
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/contest/{id}")
    public ResponseEntity<?> getContest(@PathVariable("id") long id) {
        String message = bundle.getString("get_contest");
        log.debug(message, id);

        Contest contest = contestService.getContest(id);
        return ResponseEntity.ok().body(contest);
    }

    /**
     * POST  /contest/ : Create a new contest.
     *
     * @param contest the contest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contest, or with status 400 (Bad Request) if the contest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/contests")
    public ResponseEntity<?> createContest(@Valid @RequestBody Contest contest)  {
        String message = bundle.getString("post_contest");
        log.debug(message, contest);

        Contest contestSave = contestService.createContest(contest);
        return new ResponseEntity<>(contestSave, HttpStatus.CREATED);
    }

    /**
     * PUT  /contest/ : update an contest.
     *
     * @param contest the contest to update
     * @return the ResponseEntity with status 201 (Update) and with body the new contest, or with status 400 (Bad Request) if the contest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/contests")
    public ResponseEntity<?> updateContest(@Valid @RequestBody Contest contest) throws URISyntaxException {
        String message = bundle.getString("put_contest");
        log.debug(message, contest);
        ContestEntity contestEntity = ContestEntityMapper.INSTANCE.mapContestEntity(contest);
        contestEntity = contestService.update(contestEntity);
        return new ResponseEntity<>(ContestMapper.INSTANCE.mapContest(contestEntity), HttpStatus.ACCEPTED);
    }


    /**
     * DELETE  /contest/:id : delete an contest.
     *
     * @param id the contest to delete
     * @return the ResponseEntity with status 201 (deleted) and with body the new contest, or with status 400 (Bad Request) if the contest has already an ID
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/contest/{id}")
    public ResponseEntity<?> deleteContest(@Valid @PathVariable("id") long id) {
        String message = bundle.getString("delete_contest");
        log.debug(message, id);
        contestService.delete(id);
        return ResponseEntity.ok().body(id);
    }
}
