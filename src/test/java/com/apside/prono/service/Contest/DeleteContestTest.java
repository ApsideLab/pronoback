package com.apside.prono.service.Contest;//package com.apside.prono.service.Contest;

import com.apside.prono.controller.ContestController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(-1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        contestService.delete(contestEntity.getId());
    }

    @Test
    public void testDeleteContest() {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
        contestService.delete(contestEntity.getId());
    }

    @Test
    public void testControleDate() {
        final Logger log = LoggerFactory.getLogger(ContestController.class);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setId(1L);
        contestEntity.setLabel(LIBELLE_CONTEST1);
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);

        Date date = new Date();
        String startDate = contestEntity.getStartDate();
        String endDate = contestEntity.getEndDate();
        Date stDate = new Date();
        Date enDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            stDate = format.parse(startDate);
            enDate = format.parse(endDate);
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }

        if(stDate.after(date) && enDate.after(date)) {
            when(contestRepository.findById(1L)).thenReturn(Optional.of(contestEntity));
            if (contestEntity == null) {
                log.debug("concours n'existe pas");
            }
            log.debug("ok pour supprimer");
        } else if(stDate.before(date) && enDate.after(date)) {
            log.debug("concours en cours");
        } else {
            log.debug("concours terminé");
        }
    }
}