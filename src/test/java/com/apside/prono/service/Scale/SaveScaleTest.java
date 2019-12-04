package com.apside.prono.service.Scale;

import com.apside.prono.errors.scale.BadRequestCreateScaleException;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
import com.apside.prono.repository.ContestRepository;
import com.apside.prono.repository.ScaleRepository;
import com.apside.prono.service.ContestService;
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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SaveScaleTest {
    @Mock
    private ScaleRepository scaleRepository;

    @Mock
    private ContestRepository contestRepository;

    @InjectMocks
    private ScaleService scaleService;

    @Mock
    private ContestService contestService;

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

    @Before
    public void setup()  {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveScale() {
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));

        Scale scaleFront = new Scale();
        scaleFront.setActive(ISACTIVE);
        scaleFront.setContestId(1L);
        scaleFront.setId(null);
        scaleFront.setLabel(LIBELLE_SCALE);
        scaleFront.setPtsBonResultat((int) PTSBONRESULTAT);
        scaleFront.setPtsNbButs((int) PTSNBBUTS);
        scaleFront.setPtsPunchingball((int) PTSPUNCHINGBALL);
        scaleFront.setPtsPatator((int) PTSPATATOR);
        scaleFront.setPtsVainqueurFinal((int) PTSVAINQUEURFINAL);

        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setActive(ISACTIVE);
        scaleEntity.setContest(CONTEST);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);

        ScaleEntity scaleEntity2 = new ScaleEntity();
        scaleEntity2.setId(1L);
        scaleEntity2.setActive(ISACTIVE2);
        scaleEntity2.setContest(CONTEST);
        scaleEntity2.setLabel(LIBELLE_SCALE2);
        scaleEntity2.setPtsBonResultat(PTSBONRESULTAT2);
        scaleEntity2.setPtsNbButs(PTSNBBUTS2);
        scaleEntity2.setPtsPunchingball(PTSPUNCHINGBALL2);
        scaleEntity2.setPtsPatator(PTSPATATOR2);
        scaleEntity2.setPtsVainqueurFinal(PTSVAINQUEURFINAL2);

        Set<ScaleEntity> scaleEntitiesByContest = new HashSet<>();
        scaleEntitiesByContest.add(scaleEntity);
        scaleEntitiesByContest.add(scaleEntity2);

        when(scaleRepository.save(any(ScaleEntity.class))).thenReturn(scaleEntity);
        when(contestRepository.findById(1L)).thenReturn(Optional.of(CONTEST));
        when(contestService.findScales(CONTEST)).thenReturn(scaleEntitiesByContest);
        Scale scale = scaleService.createScale(scaleFront);

        assertEquals(Optional.of(1L), Optional.of(scale.getId()));
        assertEquals(scaleFront.getLabel(), scale.getLabel());
        assertEquals(scaleFront.isActive(), scale.isActive());
        assertEquals(scaleFront.getContestId(), scale.getContestId());
        assertEquals(scaleFront.getPtsBonResultat(), scale.getPtsBonResultat());
        assertEquals(scaleFront.getPtsNbButs(), scale.getPtsNbButs());
        assertEquals(scaleFront.getPtsPatator(), scale.getPtsPatator());
        assertEquals(scaleFront.getPtsPunchingball(), scale.getPtsPunchingball());
        assertEquals(scaleFront.getPtsVainqueurFinal(), scale.getPtsVainqueurFinal());
    }

    @Test (expected = BadRequestCreateScaleException.class)
    public void testEntityNotFoundCreateScaleNull() throws Exception {
        scaleService.createScale(null);
    }

    @Test(expected = BadRequestCreateScaleException.class)
    public void testBadRequestCreateScale() {
        Scale scale = new Scale();
        scale.setId(1L);
        scaleService.createScale(scale);
    }
 }
