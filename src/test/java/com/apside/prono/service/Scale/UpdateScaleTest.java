package com.apside.prono.service.Scale;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.mapper.scale.ScaleEntityMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateScaleTest {
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    private static final String LIBELLE_SCALE = "Barème Coupe du Monde de Rugby 2019";
    private static final long PTSBONRESULTAT = 5;
    private static final long PTSNBBUTS = 4;
    private static final long PTSPUNCHINGBALL = 3;
    private static final long PTSPATATOR = 3;
    private static final long PTSVAINQUEURFINAL = 8;
    private static final Boolean ISACTIVE = true;
    private static final ContestEntity CONTEST = new ContestEntity();

    private static final String LIBELLE_SCALE2 = "Barème 2 Coupe du Monde de Rugby 2019";
    private static final long PTSBONRESULTAT2 = 3;
    private static final long PTSNBBUTS2 = 5;
    private static final long PTSPUNCHINGBALL2 = 6;
    private static final long PTSPATATOR2 = 2;
    private static final long PTSVAINQUEURFINAL2 = 5;
    private static final Boolean ISACTIVE2 = false;
    private static final ContestEntity CONTEST2 = new ContestEntity();
    
    @Mock
    private ScaleRepository scaleRepository;
    @InjectMocks
    private ScaleService scaleService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateScale() {
        ScaleEntity scaleInBdd = new ScaleEntity();
        scaleInBdd.setId(1L);
        scaleInBdd.setLabel(LIBELLE_SCALE);
        scaleInBdd.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleInBdd.setPtsPatator(PTSPATATOR);
        scaleInBdd.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleInBdd.setPtsNbButs(PTSNBBUTS);
        scaleInBdd.setPtsBonResultat(PTSBONRESULTAT);
        scaleInBdd.setActive(ISACTIVE);
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleInBdd.setContest(CONTEST);

        Scale scaleToUpdate = new Scale();
        scaleToUpdate.setId(2L);
        scaleToUpdate.setLabel(LIBELLE_SCALE2);
        scaleToUpdate.setPtsVainqueurFinal((int) PTSVAINQUEURFINAL2);
        scaleToUpdate.setPtsPatator((int) PTSPATATOR2);
        scaleToUpdate.setPtsPunchingball((int) PTSPUNCHINGBALL2);
        scaleToUpdate.setPtsNbButs((int) PTSNBBUTS2);
        scaleToUpdate.setPtsBonResultat((int) PTSBONRESULTAT2);
        scaleToUpdate.setActive(ISACTIVE2);
        CONTEST2.setId(1L);
        CONTEST2.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST2.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST2.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleToUpdate.setContestId(CONTEST2.getId());

        ScaleEntity scaleAfterSave = ScaleEntityMapper.INSTANCE.mapScaleEntity(scaleToUpdate);
        scaleAfterSave.setId(1L);

        when(scaleRepository.findById(2L)).thenReturn(Optional.of(scaleInBdd));
        when(scaleRepository.save(any(ScaleEntity.class))).thenReturn(scaleAfterSave);

        ScaleEntity scaleUpdated = scaleService.update(scaleToUpdate);

        assertEquals(Long.valueOf(1L), scaleUpdated.getId());
        assertEquals(LIBELLE_SCALE2, scaleUpdated.getLabel());
        assertEquals(ISACTIVE2, scaleUpdated.isActive());
        assertEquals(PTSBONRESULTAT2, scaleUpdated.getPtsBonResultat());
        assertEquals(PTSNBBUTS2, scaleUpdated.getPtsNbButs());
        assertEquals(PTSPATATOR2, scaleUpdated.getPtsPatator());
        assertEquals(PTSPUNCHINGBALL2, scaleUpdated.getPtsPunchingball());
        assertEquals(PTSVAINQUEURFINAL2, scaleUpdated.getPtsVainqueurFinal());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateScale() {
        Scale scale = new Scale();
        scale.setId(null);
        scale.setLabel(LIBELLE_SCALE2);
        scale.setPtsVainqueurFinal((int) PTSVAINQUEURFINAL2);
        scale.setPtsPatator((int) PTSPATATOR2);
        scale.setPtsPunchingball((int) PTSPUNCHINGBALL2);
        scale.setPtsNbButs((int) PTSNBBUTS2);
        scale.setPtsBonResultat((int) PTSBONRESULTAT2);
        scale.setActive(ISACTIVE2);
        CONTEST2.setId(1L);
        CONTEST2.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST2.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST2.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scale.setContestId(CONTEST2.getId());

        scaleService.update(scale);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateScaleNotExistInBDD() throws Exception {
        Scale scale = new Scale();
        scale.setId(1L);
        scale.setLabel(LIBELLE_SCALE2);
        scale.setPtsVainqueurFinal((int) PTSVAINQUEURFINAL2);
        scale.setPtsPatator((int) PTSPATATOR2);
        scale.setPtsPunchingball((int) PTSPUNCHINGBALL2);
        scale.setPtsNbButs((int) PTSNBBUTS2);
        scale.setPtsBonResultat((int) PTSBONRESULTAT2);
        scale.setActive(ISACTIVE2);
        CONTEST2.setId(1L);
        CONTEST2.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST2.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST2.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scale.setContestId(CONTEST2.getId());

        when(scaleRepository.findById(1L)).thenReturn(Optional.empty());

        scaleService.update(scale);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateContestNull() { scaleService.update(null);}
}
