package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.prono.BadRequestCreatePronoException;
import com.apside.prono.model.PronoEntity;
import com.apside.prono.repository.PronoRepository;
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
public class PronoService {
    @Autowired
    private PronoRepository pronoRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public PronoEntity createProno(PronoEntity pronoEntity) {
        if(pronoEntity != null) {
            if (pronoEntity.getId() != null) {
                throw new BadRequestCreatePronoException(bundle.getString("new_prono_create"));
            }
            return pronoRepository.save(pronoEntity);
        }
        throw new BadRequestCreatePronoException(bundle.getString("new_prono_empty"));
    }

    public PronoEntity getProno(long id) {
        Optional<PronoEntity> pronoEntity = pronoRepository.findById(id);
        if (!pronoEntity.isPresent()) {
            String pattern = bundle.getString("prono_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return pronoEntity.get();
    }

    @Transactional
    public List<PronoEntity> getAll() {
        return pronoRepository.findAll();
    }

    @Transactional
    public PronoEntity update(PronoEntity pronoEntity) {
        if(pronoEntity != null) {
            if (!pronoRepository.findById(pronoEntity.getId()).isPresent()) {
                String pattern = bundle.getString("prono_wrong_id");
                String message = MessageFormat.format(pattern, pronoEntity.getId());
                throw new EntityNotFoundException(message);
            }
            pronoRepository.findById(pronoEntity.getId()).get().setScore(pronoEntity.getScore());
            pronoRepository.flush();
            PronoEntity pronoUpdate = pronoRepository.findById(Long.valueOf(pronoEntity.getId())).get();
            return pronoUpdate;
        }
        throw new EntityNotFoundException(bundle.getString("update_prono_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!pronoRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("prono_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        pronoRepository.deleteById(id);
    }
}
