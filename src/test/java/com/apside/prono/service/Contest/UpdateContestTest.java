package com.apside.prono.service.Contest;//package com.apside.prono.service.Contest;

import com.apside.prono.errors.common.EntityNotFoundException;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateContestTest {

    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final String STARTDATE_CONTEST1 = "05/10/2019";
    private static final String ENDDATE_CONTEST1 = "05/11/2019";
    private static final String LIBELLE_CONTEST2 = "contest 2";
    private static final String STARTDATE_CONTEST2 = "05/12/2019";
    private static final String ENDDATE_CONTEST2 = "05/01/2020";
    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateContest() {
        //Init
        ContestEntity contestInBdd = new ContestEntity();
        contestInBdd.setId(1L);
        contestInBdd.setLabel(LIBELLE_CONTEST1);
        contestInBdd.setStartDate(STARTDATE_CONTEST1);
        contestInBdd.setEndDate(ENDDATE_CONTEST1);

        Contest contestToUpdate = new Contest();
        contestToUpdate.setId("1");
        contestToUpdate.setLabel(LIBELLE_CONTEST2);
        contestToUpdate.setStartDate(STARTDATE_CONTEST2);
        contestToUpdate.setEndDate(ENDDATE_CONTEST2);

        ContestEntity contestAfterSave = ContestEntityMapper.INSTANCE.mapContestEntity(contestToUpdate);
        contestAfterSave.setId(1L);

        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestInBdd));
        when(contestRepository.save(any(ContestEntity.class))).thenReturn(contestAfterSave);

        //Execute
        ContestEntity contestUpdated = contestService.update(contestToUpdate);

        //Test
        assertEquals(Long.valueOf(1L), contestUpdated.getId());
        assertEquals(LIBELLE_CONTEST2, contestUpdated.getLabel());
        assertEquals(STARTDATE_CONTEST2, contestUpdated.getStartDate());
        assertEquals(ENDDATE_CONTEST2, contestUpdated.getEndDate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateContest() {
        Contest contest = new Contest();
        contest.setId(null);
        contest.setLabel(LIBELLE_CONTEST2);
        contest.setStartDate(STARTDATE_CONTEST2);
        contest.setEndDate(ENDDATE_CONTEST2);
        contestService.update(contest);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateContestNotExistInBDD() throws Exception {
        Contest contest = new Contest();
        contest.setId("1");
        contest.setLabel(LIBELLE_CONTEST2);
        contest.setStartDate(STARTDATE_CONTEST2);
        contest.setEndDate(ENDDATE_CONTEST2);
        contestService.update(contest);

        when(contestRepository.findById(1L)).thenReturn(Optional.empty());

        contestService.update(contest);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateContestNull() {
        contestService.update(null);
    }
}