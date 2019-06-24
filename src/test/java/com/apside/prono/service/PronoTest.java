package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.prono.BadRequestCreatePronoException;
import com.apside.prono.model.PronoEntity;
import com.apside.prono.repository.PronoRepository;
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
public class PronoTest {

    private static final String LIBELLE_PRONO1 = "prono 1";
    private static final String LIBELLE_PRONO2 = "prono 2";
    @Mock
    private PronoRepository pronoRepository;
    @InjectMocks
    private PronoService pronoService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPronos() {
        List<PronoEntity> pronoEntityList = new ArrayList<>();
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setId(1L);
        pronoEntity.setScore(LIBELLE_PRONO1);
        PronoEntity pronoEntity2 = new PronoEntity();
        pronoEntity2.setId(2L);
        pronoEntity2.setScore(LIBELLE_PRONO2);
        pronoEntityList.add(pronoEntity);
        pronoEntityList.add(pronoEntity2);

        when(pronoRepository.findAll()).thenReturn(pronoEntityList);

        List<PronoEntity> result = pronoService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneEvent() {
        Optional<PronoEntity> pronoEntity = Optional.of(new PronoEntity());
        pronoEntity.get().setId(1L);
        pronoEntity.get().setScore(LIBELLE_PRONO1);
        when(pronoRepository.findById(1L)).thenReturn(pronoEntity);
        PronoEntity result = pronoService.getProno(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_PRONO1, result.getScore());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneEvent() throws Exception {
        pronoService.getProno(1L);
    }

    @Test(expected = BadRequestCreatePronoException.class)
    public void testBadRequestCreateProno() throws Exception {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setId(1L);
        pronoService.createProno(pronoEntity);
    }

    @Test
    public void saveEvent() {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setScore(LIBELLE_PRONO1);
        PronoEntity pronoSave = new PronoEntity();
        pronoSave.setId(1L);
        pronoSave.setScore(LIBELLE_PRONO1);
        when(pronoRepository.save(pronoEntity)).thenReturn(pronoSave);
        PronoEntity prono = pronoService.createProno(pronoEntity);
        assertEquals(Long.valueOf(1L), prono.getId());
        assertEquals(LIBELLE_PRONO1, prono.getScore());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteProno() throws Exception {
        pronoService.delete(-1L);
    }
    @Test(expected = BadRequestCreatePronoException.class)
    public void testBadRequestCreatePronoNull() throws Exception {
        pronoService.createProno(null);
    }
    @Test
    public void testDeleteProno() {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setId(1L);
        pronoEntity.setScore("score");
        when(pronoRepository.findById(1L)).thenReturn(Optional.of(pronoEntity));
        pronoService.delete(1L);
    }
    @Test
    public void updateProno() {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setScore("score");
        PronoEntity pronoUpdate = new PronoEntity();
        pronoUpdate.setId(1L);
        pronoUpdate.setScore(LIBELLE_PRONO2);
        when(pronoRepository.findById(1L)).thenReturn(Optional.of(pronoUpdate));
        PronoEntity prono = pronoService.update(pronoUpdate);
        assertEquals(Long.valueOf(1L), prono.getId());
        assertEquals(LIBELLE_PRONO2, prono.getScore());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateProno() throws Exception {
        PronoEntity pronoEntity = new PronoEntity();
        pronoEntity.setId(1L);
        pronoEntity.setScore(LIBELLE_PRONO2);
        pronoService.update(pronoEntity);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdatePronoNull() throws Exception {
        pronoService.update(null);
    }
}