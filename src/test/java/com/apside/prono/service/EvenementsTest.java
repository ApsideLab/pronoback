package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.evenement.BadRequestCreateEvenementException;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.modelapi.Evenement;
import com.apside.prono.repository.EvenementRepository;
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
public class EvenementsTest {

    @Mock
    private EvenementRepository evenementRepository;

    @InjectMocks
    private EvenementService evenementService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private static final String LIBELLE_EVENT1 = "evenement 1";
    private static final String LIBELLE_EVENT2 = "evenement 2";


    @Test
    public void testGetAllEvenements(){
        List<EvenementEntity> evenementEntityList = new ArrayList<>();
        EvenementEntity evenementEntity = new EvenementEntity();
        evenementEntity.setId(1L);
        evenementEntity.setLibelle(LIBELLE_EVENT1);
        EvenementEntity evenementEntity2 = new EvenementEntity();
        evenementEntity2.setId(2L);
        evenementEntity2.setLibelle(LIBELLE_EVENT2);
        evenementEntityList.add(evenementEntity);
        evenementEntityList.add(evenementEntity2);

        when(evenementRepository.findAll()).thenReturn(evenementEntityList);

        List<EvenementEntity> result = evenementService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneEvent(){
        Optional<EvenementEntity> evenementEntity = Optional.of(new EvenementEntity());
        evenementEntity.get().setId(1L);
        evenementEntity.get().setLibelle(LIBELLE_EVENT1);
        when(evenementRepository.findById(1L)).thenReturn(evenementEntity);
        EvenementEntity result = evenementService.getEvenement(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_EVENT1, result.getLibelle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneEvent() throws Exception {
        evenementService.getEvenement(1L);
    }

    @Test(expected = BadRequestCreateEvenementException.class)
    public void testBadRequestCreateEvenement() throws Exception {
        EvenementEntity evenementEntity = new EvenementEntity();
        evenementEntity.setId(1L);
        evenementService.createEvent(evenementEntity);
    }

    @Test
    public void saveEvent(){
        EvenementEntity evenementEntity = new EvenementEntity();
        evenementEntity.setCoeff(1);
        evenementEntity.setLibelle(LIBELLE_EVENT1);
        EvenementEntity evenementSave = new EvenementEntity();
        evenementSave.setId(1L);
        evenementSave.setCoeff(1);
        evenementSave.setLibelle(LIBELLE_EVENT1);
        when(evenementRepository.save(evenementEntity)).thenReturn(evenementSave);
        EvenementEntity evenement = evenementService.createEvent(evenementEntity);
        assertEquals(Long.valueOf(1L), evenement.getId());
        assertEquals(LIBELLE_EVENT1, evenement.getLibelle());
    }
}