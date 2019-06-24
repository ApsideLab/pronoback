package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.type.BadRequestCreateTypeException;
import com.apside.prono.model.TypeEntity;
import com.apside.prono.repository.TypeRepository;
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
public class TypesTest {

    private static final String LIBELLE_TYPE1 = "type 1";
    private static final String LIBELLE_TYPE2 = "type 2";
    @Mock
    private TypeRepository typeRepository;
    @InjectMocks
    private TypeService typeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTypes() {
        List<TypeEntity> typeEntityList = new ArrayList<>();
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setLabel(LIBELLE_TYPE1);
        TypeEntity typeEntity2 = new TypeEntity();
        typeEntity2.setId(2L);
        typeEntity2.setLabel(LIBELLE_TYPE2);
        typeEntityList.add(typeEntity);
        typeEntityList.add(typeEntity2);

        when(typeRepository.findAll()).thenReturn(typeEntityList);

        List<TypeEntity> result = typeService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneType() {
        Optional<TypeEntity> typeEntity = Optional.of(new TypeEntity());
        typeEntity.get().setId(1L);
        typeEntity.get().setLabel(LIBELLE_TYPE1);

        when(typeRepository.findById(1L)).thenReturn(typeEntity);
        TypeEntity result = typeService.getType(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_TYPE1, result.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneType() throws Exception {
        typeService.getType(1L);
    }

    @Test(expected = BadRequestCreateTypeException.class)
    public void testBadRequestCreateType() throws Exception {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeService.createType(typeEntity);
    }

    @Test
    public void saveType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(LIBELLE_TYPE1);
        TypeEntity typeSave = new TypeEntity();
        typeSave.setId(1L);
        typeSave.setLabel(LIBELLE_TYPE1);
        when(typeRepository.save(typeEntity)).thenReturn(typeSave);
        TypeEntity type = typeService.createType(typeEntity);
        assertEquals(Long.valueOf(1L), type.getId());
        assertEquals(LIBELLE_TYPE1, type.getLabel());
    }

    @Test
    public void updateType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(LIBELLE_TYPE1);
        TypeEntity typeUpdate = new TypeEntity();
        typeUpdate.setId(1L);
        typeUpdate.setLabel(LIBELLE_TYPE2);
        when(typeRepository.findById(1L)).thenReturn(Optional.of(typeUpdate));
        TypeEntity type = typeService.update(typeUpdate);
        assertEquals(Long.valueOf(1L), type.getId());
        assertEquals(LIBELLE_TYPE2, type.getLabel());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateType() throws Exception {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setLabel(LIBELLE_TYPE2);
        typeService.update(typeEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteType() throws Exception {
        typeService.delete(-1L);
    }
    @Test
    public void testDeleteType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setLabel(LIBELLE_TYPE1);
        when(typeRepository.findById(1L)).thenReturn(Optional.of(typeEntity));
        typeService.delete(1L);
    }

    @Test
    public void testFindTypeByLabel() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setLabel(LIBELLE_TYPE1);
        List<TypeEntity> list = new ArrayList<>();
        list.add(typeEntity);
        when(typeRepository.findAll()).thenReturn(list);
        assertEquals(typeEntity.getLabel(), typeService.find(LIBELLE_TYPE1).getLabel());
    }

    @Test
    public void testFindTypeByLabelResultNull() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(1L);
        typeEntity.setLabel(LIBELLE_TYPE1);

        assertEquals(null, typeService.find(LIBELLE_TYPE1));
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateTypeEmpty() throws Exception {
        typeService.update(null);
    }
    @Test(expected = BadRequestCreateTypeException.class)
    public void testEntityNotFoundCreateTypeEmpty() throws Exception {
        typeService.createType(null);
    }
}