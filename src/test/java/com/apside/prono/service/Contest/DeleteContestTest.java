package com.apside.prono.service.Contest;//package com.apside.prono.service.Contest;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.contest.BadRequestDeleteContestException;
import com.apside.prono.helpers.TimeFactory;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.repository.ContestRepository;
import com.apside.prono.service.ContestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;


import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteContestTest {

    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final LocalDateTime STARTDATE_CONTEST1 = LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00);
    private static final LocalDateTime ENDDATE_CONTEST1 = LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00);
    private static final LocalDateTime STARTDATE_CONTEST2 = LocalDateTime.of(2019, Month.DECEMBER, 5, 15, 30, 00);
    private static final LocalDateTime ENDDATE_CONTEST2 = LocalDateTime.of(2020, Month.JANUARY, 5, 21, 45, 00);

    @Mock
    private ContestRepository contestRepository;

    @Mock
    private TimeFactory timeFactory;

    @InjectMocks
    private ContestService contestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteContest() throws Exception {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(-1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        contestService.delete(contestEntity.getId());
    }

    @Test
    public void testDeleteContest() {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.OCTOBER, 1, 20, 00, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST2);
        contestEntity.setEndDate(ENDDATE_CONTEST2);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        when(timeFactory.now()).thenReturn(dayDate);
        contestService.delete(contestEntity.getId());
    }

    //TODO
    @Test
    public void verifDate_KO_competitionTerminee() throws BadRequestDeleteContestException {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.OCTOBER, 1, 20, 00, 00);
        LocalDateTime stDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime enDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setStartDate(stDate);
        contestEntity.setEndDate(enDate);

        //Before
        when(timeFactory.now()).thenReturn(dayDate);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        try {
            contestService.verifDatesContest(contestEntity.getId(), Optional.of(contestEntity));
            assert false;
        } catch(BadRequestDeleteContestException e) {
            assert true;
        }

    }

    @Test
    public void verifDate_KO_competitionDejaCommencee_DateJourSuperieurStartDate_Et_DateJourInferieurEndDate() {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.AUGUST, 25, 20, 00, 00);
        LocalDateTime stDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime enDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setStartDate(stDate);
        contestEntity.setEndDate(enDate);

        //Before
        when(timeFactory.now()).thenReturn(dayDate);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        try {
            contestService.verifDatesContest(contestEntity.getId(), Optional.of(contestEntity));
            assert false;
        } catch(BadRequestDeleteContestException e) {
            assert true;
        }
    }

    @Test
    public void verifDate_KO_competitionDejaCommencee_DateJourEgaleStartDate_Et_DateJourInferieurEndDate() {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime stDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime enDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setStartDate(stDate);
        contestEntity.setEndDate(enDate);

        //Before
        when(timeFactory.now()).thenReturn(dayDate);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        try {
            contestService.verifDatesContest(contestEntity.getId(), Optional.of(contestEntity));
            assert false;
        } catch(BadRequestDeleteContestException e) {
            assert true;
        }
    }

    @Test
    public void verifDate_KO_competitionDejaCommencee_DateJourSuperieurStartDate_Et_DateJourEgaleEndDate() {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        LocalDateTime stDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime enDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setStartDate(stDate);
        contestEntity.setEndDate(enDate);

        //Before
        when(timeFactory.now()).thenReturn(dayDate);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        try {
            contestService.verifDatesContest(contestEntity.getId(), Optional.of(contestEntity));
            assert false;
        } catch(BadRequestDeleteContestException e) {
            assert true;
        }
    }

    @Test
    public void verifDate_OK_competitionPasCommencee() {
        LocalDateTime dayDate = LocalDateTime.of(2019, Month.JUNE, 1, 20, 00, 00);
        LocalDateTime stDate = LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 00);
        LocalDateTime enDate = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 18, 20, 00);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setStartDate(stDate);
        contestEntity.setEndDate(enDate);

        //Before
        Mockito.when(timeFactory.now()).thenReturn(dayDate);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        try {
            contestService.verifDatesContest(contestEntity.getId(), Optional.of(contestEntity));
            assert true;
        } catch(BadRequestDeleteContestException e) {
            assert false;
        }
    }
}