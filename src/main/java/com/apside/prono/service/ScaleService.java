package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.scale.BadRequestCreateScaleException;
import com.apside.prono.mapper.contest.ContestEntityMapper;
import com.apside.prono.mapper.scale.ScaleEntityMapper;
import com.apside.prono.mapper.scale.ScaleMapper;
import com.apside.prono.model.ContestEntity;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Contest;
import com.apside.prono.modelapi.Scale;
import com.apside.prono.repository.ContestRepository;
import com.apside.prono.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.text.MessageFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ScaleService {
    @Autowired
    private ScaleRepository scaleRepository;
    @Autowired
    private ContestRepository contestRepository;
    private ContestService contestService = new ContestService();

    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public Scale createScale(Scale scale) {
        ScaleEntity scaleEntity = ScaleEntityMapper.INSTANCE.mapScaleEntity(scale);

        if(scaleEntity != null) {
            if(scale.getId() != null) {
                throw new BadRequestCreateScaleException(bundle.getString("new_scale_create"));
            } else if(scale.getContestId() == null) {
                throw new BadRequestCreateScaleException(bundle.getString("new_scale_not_contestId"));
            }

            Optional<ContestEntity> contestEntity = contestRepository.findById(scale.getContestId());
            if(!contestEntity.isPresent()) {
                String pattern = bundle.getString("new_scale_not_contestId");
                String message = MessageFormat.format(pattern, scale.getContestId());
                throw new EntityNotFoundException(message);
            }

            scaleEntity.setContest(contestEntity.get());

            Set<ScaleEntity> scaleEntitiesByContest = contestService.findScales(contestEntity.get());

            if(scaleEntitiesByContest.size() > 0) {
                scaleEntity.setActive(false);
            } else {
                scaleEntity.setActive(true);
            }

            scaleEntity = scaleRepository.save(scaleEntity);
        } else {
            throw new BadRequestCreateScaleException(bundle.getString("new_scale_empty"));
        }
        return ScaleMapper.INSTANCE.mapScale(scaleEntity);
    }

    public Scale getScale(long id) {
        Optional<ScaleEntity> scaleEntity = scaleRepository.findById(id);
        if(!scaleEntity.isPresent()) {
            String pattern = bundle.getString("scale_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return ScaleMapper.INSTANCE.mapScale(scaleEntity.get());
    }

    @Transactional
    public List<ScaleEntity> getAll() {return scaleRepository.findAll(); }

    @Transactional
    public ScaleEntity update(Scale scale) {
        if(scale != null) {
            if(scale.getId() == null) {
                String pattern = bundle.getString("scale_wrong_id");
                String message = MessageFormat.format(pattern, scale.getId());
                throw new EntityNotFoundException(message);
            }
            Long scaleId = scale.getId();
            Optional<ScaleEntity> scaleEntity = scaleRepository.findById(scaleId);

            if(scaleEntity.isPresent()) {
                scaleEntity = Optional.of(ScaleEntityMapper.INSTANCE.mapScaleEntity(scale));
            } else {
                String pattern = bundle.getString("scale_wrong_id");
                String message = MessageFormat.format(pattern, scale.getId());
                throw new EntityNotFoundException(message);
            }
            return scaleRepository.save(scaleEntity.get());
        } else {
            throw new EntityNotFoundException(bundle.getString("update_scale_empty"));
        }
    }

    @Transactional
    public void delete(long id) {
        Optional<ScaleEntity> scaleEntity = scaleRepository.findById(id);
        if(!scaleEntity.isPresent()) {
            String pattern = bundle.getString("scale_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        scaleRepository.deleteById(scaleEntity.get().getId());
    }

    public ScaleEntity find(String label) {
        for(int i = 0; i < scaleRepository.findAll().size(); i++) {
            if(scaleRepository.findAll().get(i).getLabel().equals(label))
                return scaleRepository.findAll().get(i);
        }
        return null;
    }
}
