package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.resultEvent.BadRequestCreateResultEventException;
import com.apside.prono.model.ResultEventEntity;
import com.apside.prono.repository.ResultEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
@Transactional(readOnly = true)
public class ResultEventService {
    @Autowired
    private ResultEventRepository resultEventRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public ResultEventEntity createResultEvent(ResultEventEntity resultEventEntity) {
        if(resultEventEntity != null) {
            if (resultEventEntity.getId() != null) {
                throw new BadRequestCreateResultEventException(bundle.getString("new_result_event_create"));
            }
            return resultEventRepository.save(resultEventEntity);
        }
        throw new BadRequestCreateResultEventException(bundle.getString("new_result_event_empty"));
    }

    public ResultEventEntity getResultEvent(long id) {
        Optional<ResultEventEntity> resultEventEntity = resultEventRepository.findById(id);
        if (!resultEventEntity.isPresent()) {
            String pattern = bundle.getString("result_event_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return resultEventEntity.get();
    }

    @Transactional
    public List<ResultEventEntity> getAll() {
        return resultEventRepository.findAll();
    }

    @Transactional
    public ResultEventEntity update(ResultEventEntity resultEventEntity) {
        if(resultEventEntity != null) {
            Optional<ResultEventEntity> resultEvent = resultEventRepository.findById(resultEventEntity.getId());
            if (!resultEvent.isPresent()) {
                String pattern = bundle.getString("result_event_wrong_id");
                String message = MessageFormat.format(pattern, resultEventEntity.getId());
                throw new EntityNotFoundException(message);
            }
            resultEventRepository.findById(resultEventEntity.getId()).get().setDate(resultEventEntity.getDate());
            resultEventRepository.findById(resultEventEntity.getId()).get().setActor(resultEventEntity.getActor());
            resultEventRepository.findById(resultEventEntity.getId()).get().setEvenement(resultEventEntity.getEvenement());
            resultEventRepository.findById(resultEventEntity.getId()).get().setResultDetail(resultEventEntity.getResultDetail());
            resultEventRepository.findById(resultEventEntity.getId()).get().setType(resultEventEntity.getType());
            ResultEventEntity resultEventEntity1 = resultEventRepository.findById(resultEventEntity.getId()).get();
            resultEventRepository.flush();
            return resultEventEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_result_event_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!resultEventRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("result_event_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        resultEventRepository.deleteById(id);
    }
}
