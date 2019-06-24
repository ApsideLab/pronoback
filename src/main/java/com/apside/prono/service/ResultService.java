package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.result.BadRequestCreateResultException;
import com.apside.prono.model.ResultEntity;
import com.apside.prono.repository.ResultRepository;
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
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public ResultEntity createResult(ResultEntity resultEntity) {
        if(resultEntity != null) {
            if (resultEntity.getId() != null) {
                throw new BadRequestCreateResultException(bundle.getString("new_result_create"));
            }
            return resultRepository.save(resultEntity);
        }
        throw new BadRequestCreateResultException(bundle.getString("new_result_empty"));
    }

    public ResultEntity getResult(long id) {
        Optional<ResultEntity> resultEntity = resultRepository.findById(id);
        if (!resultEntity.isPresent()) {
            String pattern = bundle.getString("result_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return resultEntity.get();
    }

    @Transactional
    public List<ResultEntity> getAll() {
        return resultRepository.findAll();
    }

    @Transactional
    public ResultEntity update(ResultEntity resultEntity) {
        if(resultEntity != null) {
            if (!resultRepository.findById(resultEntity.getId()).isPresent()) {
                String pattern = bundle.getString("result_wrong_id");
                String message = MessageFormat.format(pattern, resultEntity.getId());
                throw new EntityNotFoundException(message);
            }
            resultRepository.findById(resultEntity.getId()).get().setResult(resultEntity.getResult());
            resultRepository.flush();
            ResultEntity resultUpdate = resultRepository.findById(Long.valueOf(resultEntity.getId())).get();
            return resultUpdate;
        }
        throw new EntityNotFoundException(bundle.getString("update_result_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!resultRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("result_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        resultRepository.deleteById(id);
    }
}
