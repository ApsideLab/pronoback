package com.apside.prono.service.Contest;//package com.apside.prono.service.Contest;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ContestEntity;
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

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteContestTest {

    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final String STARTDATE_CONTEST1 = "05/10/2019";
    private static final String ENDDATE_CONTEST1 = "05/11/2019";

    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
}