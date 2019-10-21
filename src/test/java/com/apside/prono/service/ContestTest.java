package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.contest.BadRequestCreateContestException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.repository.ContestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContestTest {

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
        ContestEntity result = contestService.getContest(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_CONTEST1, result.getLabel());
        assertEquals(STARTDATE_CONTEST1, result.getStartDate());
        assertEquals(ENDDATE_CONTEST1, result.getEndDate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneContest() throws Exception {
        contestService.getContest(1L);
    }

    @Test(expected = BadRequestCreateContestException.class)
    public void testBadRequestCreateContest() throws Exception {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestService.createContest(contestEntity);
    }

    @Test
    public void saveContest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        ContestEntity contestSave = new ContestEntity();
        contestSave.setId(1L);
        contestSave.setLabel(LIBELLE_CONTEST1);
        contestSave.setStartDate(STARTDATE_CONTEST1);
        contestSave.setEndDate(ENDDATE_CONTEST1);
        when(contestRepository.save(contestEntity)).thenReturn(contestSave);
        ContestEntity contest = contestService.createContest(contestEntity);
        assertEquals(Long.valueOf(1L), contest.getId());
        assertEquals(LIBELLE_CONTEST1, contest.getLabel());
        assertEquals(STARTDATE_CONTEST1, contest.getStartDate());
        assertEquals(ENDDATE_CONTEST1, contest.getEndDate());
    }

    @Test
    public void updateContest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        ContestEntity contestUpdate = new ContestEntity();
        contestUpdate.setId(1L);
        contestUpdate.setLabel(LIBELLE_CONTEST2);
        contestUpdate.setStartDate(STARTDATE_CONTEST2);
        contestUpdate.setEndDate(ENDDATE_CONTEST2);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestUpdate));
        ContestEntity contest = contestService.update(contestUpdate);
        assertEquals(Long.valueOf(1L), contest.getId());
        assertEquals(LIBELLE_CONTEST2, contest.getLabel());
        assertEquals(STARTDATE_CONTEST2, contest.getStartDate());
        assertEquals(ENDDATE_CONTEST2, contest.getEndDate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateContest() throws Exception {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST2);
        contestEntity.setStartDate(STARTDATE_CONTEST2);
        contestEntity.setEndDate(ENDDATE_CONTEST2);
        contestService.update(contestEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteContest() throws Exception {
        contestService.delete(-1L);
    }

    @Test
    public void testDeleteContest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        contestService.delete(1L);
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

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateActorNull() throws Exception {
        contestService.update(null);
    }

    @Test(expected = BadRequestCreateContestException.class)
    public void testEntityNotFoundCreateActorNull() throws Exception {
        contestService.createContest(null);
    }
}