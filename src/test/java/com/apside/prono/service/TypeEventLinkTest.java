package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.tryEventLink.BadRequestCreateTypeEventLinkException;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.model.TypeEntity;
import com.apside.prono.model.TypeEventLinkEntity;
import com.apside.prono.repository.TypeEventLinkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TypeEventLinkTest {

    @Mock
    private TypeEventLinkRepository typeEventLinkRepository;
    @InjectMocks
    private TypeEventLinkService typeEventLinkService;
    private Long value = 5L;
    private String label = "test";
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTypeEventLinks() {
        List<TypeEventLinkEntity> typeEventLinkEntityList = new ArrayList<>();
        TypeEventLinkEntity typeEventLinkEntity = initialize();
        TypeEventLinkEntity typeEventLinkEntity2 = initialize();
        typeEventLinkEntityList.add(typeEventLinkEntity);
        typeEventLinkEntityList.add(typeEventLinkEntity2);

        when(typeEventLinkRepository.findAll()).thenReturn(typeEventLinkEntityList);

        List<TypeEventLinkEntity> result = typeEventLinkService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneTypeEventLink() {
        TypeEventLinkEntity typeEventLinkEntity = initialize();
        typeEventLinkEntity.setId(1L);

        when(typeEventLinkRepository.findById(1L)).thenReturn(Optional.ofNullable(typeEventLinkEntity));
        TypeEventLinkEntity result = typeEventLinkService.getTypeEventLink(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(label, typeEventLinkEntity.getType().getLabel());
        assertEquals(label, typeEventLinkEntity.getEvent().getLibelle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneTypeEventLink() throws Exception {
        typeEventLinkService.getTypeEventLink(1L);
    }

    @Test(expected = BadRequestCreateTypeEventLinkException.class)
    public void testBadRequestCreateTypeEventLink() throws Exception {
        TypeEventLinkEntity typeEventLinkEntity = new TypeEventLinkEntity();
        typeEventLinkEntity.setId(1L);
        typeEventLinkService.createTypeEventLink(typeEventLinkEntity);
    }

    @Test
    public void saveTypeEventLink() {
        TypeEventLinkEntity typeEventLinkEntity = initialize();
        TypeEventLinkEntity typeEventLinkSave = initialize();
        typeEventLinkSave.setId(1L);
        when(typeEventLinkRepository.save(typeEventLinkEntity)).thenReturn(typeEventLinkSave);
        TypeEventLinkEntity typeEventLink = typeEventLinkService.createTypeEventLink(typeEventLinkEntity);
        assertEquals(Long.valueOf(1L), typeEventLink.getId());
        assertEquals(label, typeEventLink.getType().getLabel());
        assertEquals(label, typeEventLink.getEvent().getLibelle());
    }


    @Test
    public void updateTypeEventLink() {
        TypeEventLinkEntity typeEventLinkUpdate = initialize();
        typeEventLinkUpdate.setType(initializeType());
        typeEventLinkUpdate.setEvent(initializeEvent());
        String newLabel = "trololo";
        typeEventLinkUpdate.getType().setLabel(newLabel);
        typeEventLinkUpdate.setId(1L);
        when(typeEventLinkRepository.findById(1L)).thenReturn(Optional.of(typeEventLinkUpdate));
        TypeEventLinkEntity typeEventLink = typeEventLinkService.update(typeEventLinkUpdate);
        assertEquals(Long.valueOf(1L), typeEventLink.getId());
        assertEquals(typeEventLink.getType().getLabel(), newLabel);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateTypeEventLink() throws Exception {
        TypeEventLinkEntity typeEventLinkEntity = initialize();
        typeEventLinkService.update(typeEventLinkEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteTypeEventLink() throws Exception {
        typeEventLinkService.delete(-1L);
    }

    public TypeEventLinkEntity initialize() {
        TypeEventLinkEntity typeEventLinkEntity = new TypeEventLinkEntity();
        typeEventLinkEntity.setType(initializeType());
        typeEventLinkEntity.setEvent(initializeEvent());
        return typeEventLinkEntity;
    }
    public TypeEntity initializeType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);
        typeEntity.setId(1L);
        return typeEntity;
    }
    public EvenementEntity initializeEvent() {
        EvenementEntity eventEntity = new EvenementEntity();
        eventEntity.setLibelle(label);
        eventEntity.setCoeff(value);
        eventEntity.setDateEvenement(new Date());
        eventEntity.setDateFermeture(new Date());
        eventEntity.setDateOuverture(new Date());
        eventEntity.setId(1L);
        return eventEntity;
    }

    @Test
    public void testDeleteActor() {
        TypeEventLinkEntity typeEventLinkEntity = initialize();
        typeEventLinkEntity.setId(1L);
        when(typeEventLinkRepository.findById(1L)).thenReturn(Optional.of(typeEventLinkEntity));
        typeEventLinkService.delete(1L);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdatePlayerEmpty() throws Exception {
        typeEventLinkService.update(null);
    }
    @Test(expected = BadRequestCreateTypeEventLinkException.class)
    public void testEntityNotFoundCreatePlayerEmpty() throws Exception {
        typeEventLinkService.createTypeEventLink(null);
    }
}
