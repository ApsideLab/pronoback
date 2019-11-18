package com.apside.prono.service.Contest;//package com.apside.prono.service.Contest;

import com.apside.prono.controller.ContestController;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.contest.BadRequestDeleteContestException;
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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class verifDatesContestTest {

    private static final String LIBELLE_CONTEST1 = "contest 1";
    private static final String STARTDATE_CONTEST1 = "2020-05-20 08:00:00";
    private static final String ENDDATE_CONTEST1 = "2020-06-12 21:00:00";

    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verifDatesContestTest() {
        final Logger log = LoggerFactory.getLogger(ContestController.class);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setStartDate(STARTDATE_CONTEST1);
        contestEntity.setEndDate(ENDDATE_CONTEST1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        Date date = new Date();
        Date stDate = new Date();
        Date enDate = new Date();

        try {
            stDate = format.parse(contestEntity.getStartDate());
            enDate = format.parse(contestEntity.getEndDate());
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }

        if(!(stDate.after(date) && enDate.after(date))) {
            log.debug("concours terminé");
        } else if(stDate.before(date) && enDate.after(date)) {
            log.debug("concours commencé");
        }
    }
}