package com.apside.prono.service.Contest;

import com.apside.prono.errors.contest.BadRequestCreateContestException;
import com.apside.prono.mapper.contest.ContestEntityMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import com.apside.prono.repository.ContestRepository;
import com.apside.prono.service.ContestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SaveContestTest {
    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final LocalDateTime STARTDATE_CONTEST1 = LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00);
    private static final LocalDateTime ENDDATE_CONTEST1 = LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveContest() {
        //Init
        Contest contestFront = new Contest();
        contestFront.setLabel(LIBELLE_CONTEST1);
        contestFront.setStartDate(STARTDATE_CONTEST1);
        contestFront.setEndDate(ENDDATE_CONTEST1);

        ContestEntity contestSave = new ContestEntity();
        contestSave.setId(1L);
        contestSave.setLabel(LIBELLE_CONTEST1);
        contestSave.setStartDate(STARTDATE_CONTEST1);
        contestSave.setEndDate(ENDDATE_CONTEST1);
        when(contestRepository.save(any(ContestEntity.class))).thenReturn(contestSave);

        //Execute
        Contest contest = contestService.createContest(contestFront);

        // Verif
        assertEquals(1L, contest.getId().longValue());
        assertEquals(contestFront.getLabel(), contest.getLabel());
        assertEquals(contestFront.getStartDate(), contest.getStartDate());
        assertEquals(contestFront.getEndDate(), contest.getEndDate());
    }

    @Test(expected = BadRequestCreateContestException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        contestService.createContest(null);
    }

    @Test(expected = BadRequestCreateContestException.class)
    public void testBadRequestCreateContest() {
        Contest contest = new Contest();
        contest.setId(1L);
        contestService.createContest(contest);
    }
}
