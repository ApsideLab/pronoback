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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GetContestTest {

    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final LocalDateTime STARTDATE_CONTEST1 = LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00);
    private static final LocalDateTime ENDDATE_CONTEST1 = LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00);
    private static final String LIBELLE_CONTEST2 = "contest 2";
    private static final LocalDateTime STARTDATE_CONTEST2 = LocalDateTime.of(2019, Month.DECEMBER, 5, 15, 30, 00);
    private static final LocalDateTime ENDDATE_CONTEST2 = LocalDateTime.of(2020, Month.JANUARY, 5, 21, 45, 00);
    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllContests() {
        List<ContestEntity> contestEntityList = new ArrayList<>();
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        ContestEntity contestEntity2 = new ContestEntity();
        contestEntity2.setId(2L);
        contestEntity2.setLabel(LIBELLE_CONTEST2);
        contestEntity2.setStartDate(STARTDATE_CONTEST2);
        contestEntity2.setEndDate(ENDDATE_CONTEST2);
        contestEntityList.add(contestEntity);
        contestEntityList.add(contestEntity2);

        when(contestRepository.findAll()).thenReturn(contestEntityList);

        List<ContestEntity> result = contestService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneContest() {
        Optional<ContestEntity> contestEntity = Optional.of(new ContestEntity());
        contestEntity.get().setId(1L);
        contestEntity.get().setLabel(LIBELLE_CONTEST1);
        contestEntity.get().setStartDate(STARTDATE_CONTEST1);
        contestEntity.get().setEndDate(ENDDATE_CONTEST1);

        when(contestRepository.findById(1L)).thenReturn(contestEntity);
        Contest contest = contestService.getContest(1L);
        assertEquals(1L, contest.getId().longValue());
        assertEquals(LIBELLE_CONTEST1, contest.getLabel());
        assertEquals(STARTDATE_CONTEST1, contest.getStartDate());
        assertEquals(ENDDATE_CONTEST1, contest.getEndDate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneContest() throws Exception {
        contestService.getContest(1L);
    }

    @Test
    public void testFindContestByLabel() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        List<ContestEntity> list = new ArrayList<>();
        list.add(contestEntity);
        when(contestRepository.findAll()).thenReturn(list);
        assertEquals(contestEntity.getLabel(), contestService.find(LIBELLE_CONTEST1).getLabel());
    }

    @Test
    public void testFindContestByLabelResultNull() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);

        assertEquals(null, contestService.find(LIBELLE_CONTEST1));
    }
}