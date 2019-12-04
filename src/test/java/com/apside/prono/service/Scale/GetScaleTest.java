package com.apside.prono.service.Scale;

import com.apside.prono.errors.common.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GetScaleTest {
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
    public void setup()  {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetAllScales() {
        List<ScaleEntity> scaleEntityList = new ArrayList<>();
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setActive(ISACTIVE);
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.setContest(CONTEST);

        ScaleEntity scaleEntity2 = new ScaleEntity();
        scaleEntity2.setId(2L);
        scaleEntity2.setLabel(LIBELLE_SCALE2);
        scaleEntity2.setPtsVainqueurFinal(PTSVAINQUEURFINAL2);
        scaleEntity2.setPtsPatator(PTSPATATOR2);
        scaleEntity2.setPtsPunchingball(PTSPUNCHINGBALL2);
        scaleEntity2.setPtsNbButs(PTSNBBUTS2);
        scaleEntity2.setPtsBonResultat(PTSBONRESULTAT2);
        scaleEntity2.setActive(ISACTIVE2);
        CONTEST2.setId(1L);
        CONTEST2.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST2.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST2.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity2.setContest(CONTEST2);

        scaleEntityList.add(scaleEntity);
        scaleEntityList.add(scaleEntity2);

        when(scaleRepository.findAll()).thenReturn(scaleEntityList);

        List<ScaleEntity> result = scaleService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneScale() {
        Optional<ScaleEntity> scaleEntity = Optional.of(new ScaleEntity());
        scaleEntity.get().setId(1L);
        scaleEntity.get().setLabel(LIBELLE_SCALE);
        scaleEntity.get().setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleEntity.get().setPtsPatator(PTSPATATOR);
        scaleEntity.get().setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.get().setPtsNbButs(PTSNBBUTS);
        scaleEntity.get().setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.get().setActive(ISACTIVE);
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.get().setContest(CONTEST);

        when(scaleRepository.findById(1L)).thenReturn(scaleEntity);
        Scale scale = scaleService.getScale(1L);
        assertEquals(Optional.of(Long.valueOf(1L).longValue()), Optional.of(scale.getId()));
        assertEquals(LIBELLE_SCALE, scale.getLabel());


        assertEquals(Optional.of((int)PTSVAINQUEURFINAL), Optional.of(scale.getPtsVainqueurFinal()));
        assertEquals(Optional.of((int)PTSPATATOR), Optional.of(scale.getPtsPatator()));
        assertEquals(Optional.of((int)PTSPUNCHINGBALL), Optional.of(scale.getPtsPunchingball()));
        assertEquals(Optional.of((int)PTSNBBUTS), Optional.of(scale.getPtsNbButs()));
        assertEquals(Optional.of((int)PTSBONRESULTAT), Optional.of(scale.getPtsBonResultat()));
        assertEquals(ISACTIVE, scale.isActive());
        assertEquals(CONTEST.getId(), scale.getContestId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundExceptionGetOneScale() throws Exception {
        scaleService.getScale(1L);
    }

    @Test
    public void testFindScaleByLabel() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setActive(ISACTIVE);
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.setContest(CONTEST);

        List<ScaleEntity> list = new ArrayList<>();
        list.add(scaleEntity);

        when(scaleRepository.findAll()).thenReturn(list);
        assertEquals(scaleEntity.getLabel(), scaleService.find(LIBELLE_SCALE).getLabel());
    }

    @Test
    public void testFindScaleByLabelResultNull() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE);
        scaleEntity.setPtsVainqueurFinal(PTSVAINQUEURFINAL);
        scaleEntity.setPtsPatator(PTSPATATOR);
        scaleEntity.setPtsPunchingball(PTSPUNCHINGBALL);
        scaleEntity.setPtsNbButs(PTSNBBUTS);
        scaleEntity.setPtsBonResultat(PTSBONRESULTAT);
        scaleEntity.setActive(ISACTIVE);
        CONTEST.setId(1L);
        CONTEST.setLabel("Coupe du Monde de Rugby 2019");
        CONTEST.setStartDate(LocalDateTime.of(2019, Month.OCTOBER, 5, 20, 00, 00));
        CONTEST.setEndDate(LocalDateTime.of(2019, Month.NOVEMBER, 5, 19, 00, 00));
        scaleEntity.setContest(CONTEST);

        assertEquals(null, scaleService.find(LIBELLE_SCALE));
    }
}
