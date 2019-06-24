package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.result.BadRequestCreateResultException;
import com.apside.prono.model.ResultEntity;
import com.apside.prono.repository.ResultRepository;
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
public class ResultTest {

    private static final String LIBELLE_RESULT1 = "result 1";
    private static final String LIBELLE_RESULT2 = "result 2";
    @Mock
    private ResultRepository resultRepository;
    @InjectMocks
    private ResultService resultService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllResult() {
        List<ResultEntity> resultEntityList = new ArrayList<>();
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(1L);
        resultEntity.setResult(LIBELLE_RESULT1);
        ResultEntity resultEntity2 = new ResultEntity();
        resultEntity2.setId(2L);
        resultEntity2.setResult(LIBELLE_RESULT2);
        resultEntityList.add(resultEntity);
        resultEntityList.add(resultEntity2);

        when(resultRepository.findAll()).thenReturn(resultEntityList);

        List<ResultEntity> result = resultService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOneEvent() {
        Optional<ResultEntity> resultEntity = Optional.of(new ResultEntity());
        resultEntity.get().setId(1L);
        resultEntity.get().setResult(LIBELLE_RESULT1);
        when(resultRepository.findById(1L)).thenReturn(resultEntity);
        ResultEntity result = resultService.getResult(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_RESULT1, result.getResult());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundGetOneEvent() throws Exception {
        resultService.getResult(1L);
    }

    @Test(expected = BadRequestCreateResultException.class)
    public void testBadRequestCreateResult() throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(1L);
        resultService.createResult(resultEntity);
    }

    @Test
    public void saveEvent() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(LIBELLE_RESULT1);
        ResultEntity resultSave = new ResultEntity();
        resultSave.setId(1L);
        resultSave.setResult(LIBELLE_RESULT1);
        when(resultRepository.save(resultEntity)).thenReturn(resultSave);
        ResultEntity result = resultService.createResult(resultEntity);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_RESULT1, result.getResult());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testBadRequestDeleteResult() throws Exception {
        resultService.delete(-1L);
    }
    @Test
    public void testDeleteResultEvent() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(LIBELLE_RESULT1);
        resultEntity.setId(1L);
        when(resultRepository.findById(1L)).thenReturn(Optional.of(resultEntity));
        resultService.delete(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResult() throws Exception {
        ResultEntity resultEntity  = new ResultEntity();
        resultEntity.setResult(LIBELLE_RESULT1);
        resultEntity.setId(1L);
        resultService.update(resultEntity);
    }
    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundUpdateResultNull() throws Exception {
        resultService.update(null);
    }
    @Test(expected = BadRequestCreateResultException.class)
    public void testEntityNotFoundCreateResultNull() throws Exception {
        resultService.createResult(null);
    }
    @Test
    public void updateResult() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResult(LIBELLE_RESULT1);
        ResultEntity resultUpdate = new ResultEntity();
        resultUpdate.setId(1L);
        resultUpdate.setResult(LIBELLE_RESULT2);
        when(resultRepository.findById(1L)).thenReturn(Optional.of(resultUpdate));
        ResultEntity result = resultService.update(resultUpdate);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(LIBELLE_RESULT2, result.getResult());
    }
}