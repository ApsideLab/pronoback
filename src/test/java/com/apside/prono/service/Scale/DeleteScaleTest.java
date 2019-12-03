package com.apside.prono.service.Scale;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.repository.ScaleRepository;
import com.apside.prono.service.ScaleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteScaleTest {

    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    private static final String LIBELLE_SCALE = "Barème Coupe du Monde de Rugby 2019";
    private static final long PTSBONRESULTAT = 5;
    private static final long PTSNBBUTS = 4;
    private static final long PTSPUNCHINGBALL = 3;
    private static final long PTSPATATOR = 3;
    private static final long PTSVAINQUEURFINAL = 8;
    private static final Boolean ISACTIVE = true;
    private static final ContestEntity CONTEST = new ContestEntity();


    @Mock
    private ScaleRepository scaleRepository;

    @InjectMocks
    private ScaleService scaleService;

    @Before
    public void setup()  {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteScale() {
        ScaleEntity scaleEntity = new ScaleEntity();
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.setContest(CONTEST);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setId(1L);
        scaleEntity.setActive(ISACTIVE);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        when(scaleRepository.findById(1L)).thenReturn(Optional.of(scaleEntity));
        scaleService.delete(scaleEntity.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteScale() throws Exception {
        ScaleEntity scaleEntity = new ScaleEntity();
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.setContest(CONTEST);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setId(-1L);
        scaleEntity.setActive(ISACTIVE);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleService.delete(scaleEntity.getId());
    }
}
