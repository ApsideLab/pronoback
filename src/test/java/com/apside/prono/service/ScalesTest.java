package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.scale.BadRequestCreateScaleException;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.repository.ScaleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ScalesTest {

   /* private static final String LIBELLE_SCALE1 = "scale 1";
    private static final String LIBELLE_SCALE2 = "scale 2";
    @Mock
    private ScaleRepository scaleRepository;
    @InjectMocks
    private ScaleService scaleService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllScales() {
        List<ScaleEntity> scaleEntityList = new ArrayList<>();
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE1);
        ScaleEntity scaleEntity2 = new ScaleEntity();
        scaleEntity2.setId(2L);
        scaleEntity2.setLabel(LIBELLE_SCALE2);
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
        scaleEntity.get().setLabel(LIBELLE_SCALE1);

        when(scaleRepository.findById(1L)).thenReturn(scaleEntity);
        ScaleEntity result = scaleService.getScale(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_SCALE1, result.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneScale() throws Exception {
        scaleService.getScale(1L);
    }

    @Test(expected = BadRequestCreateScaleException.class)
    public void testBadRequestCreateScale() throws Exception {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleService.createScale(scaleEntity);
    }

    @Test
    public void saveScale() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setLabel(LIBELLE_SCALE1);
        ScaleEntity scaleSave = new ScaleEntity();
        scaleSave.setId(1L);
        scaleSave.setLabel(LIBELLE_SCALE1);
        *//*scaleEntity.setPtsBonusUnScoreExactResultatOK(1);
        scaleEntity.setPtsBonusUnScoreExactResultatKO(2);
        scaleEntity.setPtsBonusEcartButs(3);
        scaleEntity.setPtsBonusDeuxScoresExacts(4);*//*
        scaleEntity.setPtsBonResultat(5);
        *//*scaleEntity.setDateFinValidite(new Date());
        scaleEntity.setDateDebutValidite(new Date());*//*
        when(scaleRepository.save(scaleEntity)).thenReturn(scaleSave);
        ScaleEntity scale = scaleService.createScale(scaleEntity);
        assertEquals(Long.valueOf(1L), scale.getId());
        assertEquals(LIBELLE_SCALE1, scale.getLabel());
    }

    @Test
    public void updateScale() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setLabel(LIBELLE_SCALE1);
        ScaleEntity scaleUpdate = new ScaleEntity();
        scaleUpdate.setId(1L);
        scaleUpdate.setLabel(LIBELLE_SCALE2);
        when(scaleRepository.findById(1L)).thenReturn(Optional.of(scaleUpdate));
        ScaleEntity scale = scaleService.update(scaleUpdate);
        assertEquals(Long.valueOf(1L), scale.getId());
        assertEquals(LIBELLE_SCALE2, scale.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateScale() throws Exception {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE2);
        scaleService.update(scaleEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteScale() throws Exception {
        scaleService.delete(-1L);
    }
    @Test
    public void testDeleteScale() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE1);
        when(scaleRepository.findById(1L)).thenReturn(Optional.of(scaleEntity));
        scaleService.delete(1L);
    }

    @Test
    public void testFindScaleByLabel() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE1);
        List<ScaleEntity> list = new ArrayList<>();
        list.add(scaleEntity);
        when(scaleRepository.findAll()).thenReturn(list);
        assertEquals(scaleEntity.getLabel(), scaleService.find(LIBELLE_SCALE1).getLabel());
    }

    @Test
    public void testFindScaleByLabelResultNull() {
        ScaleEntity scaleEntity = new ScaleEntity();
        scaleEntity.setId(1L);
        scaleEntity.setLabel(LIBELLE_SCALE1);

        assertEquals(null, scaleService.find(LIBELLE_SCALE1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResultNull() throws Exception {
        scaleService.update(null);
    }
    @Test(expected = BadRequestCreateScaleException.class)
    public void testEntityNotFoundCreateResultNull() throws Exception {
        scaleService.createScale(null);
    }*/
}