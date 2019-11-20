package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.scale.BadRequestCreateScaleException;
import com.apside.prono.model.ScaleEntity;
import com.apside.prono.repository.ScaleRepository;
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
public class ScaleService_old {
    @Autowired
    private ScaleRepository scaleRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    
    @Transactional
    public ScaleEntity createScale(ScaleEntity scaleEntity) {
        if(scaleEntity != null) {
            if (scaleEntity.getId() != null) {
                throw new BadRequestCreateScaleException(bundle.getString("new_scale_create"));
            }
            return scaleRepository.save(scaleEntity);
        }
        throw new BadRequestCreateScaleException(bundle.getString("new_scale_empty"));
    }

    public ScaleEntity getScale(long id) {
        Optional<ScaleEntity> scaleEntity = scaleRepository.findById(id);
        if (!scaleEntity.isPresent()) {
            String pattern = bundle.getString("scale_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return scaleEntity.get();
    }

    @Transactional
    public List<ScaleEntity> getAll() {
        return scaleRepository.findAll();
    }

    @Transactional
    public ScaleEntity update(ScaleEntity scaleEntity) {
        if(scaleEntity != null) {
            if (!scaleRepository.findById(scaleEntity.getId()).isPresent()) {
                String pattern = bundle.getString("scale_wrong_id");
                String message = MessageFormat.format(pattern, scaleEntity.getId());
                throw new EntityNotFoundException(message);
            }

            /*scaleRepository.findById(scaleEntity.getId()).get().setDateDebutValidite(scaleEntity.getDateDebutValidite());
            scaleRepository.findById(scaleEntity.getId()).get().setDateFinValidite(scaleEntity.getDateFinValidite());*/
            scaleRepository.findById(scaleEntity.getId()).get().setLabel(scaleEntity.getLabel());
            scaleRepository.findById(scaleEntity.getId()).get().setPtsBonResultat(scaleEntity.getPtsBonResultat());
            /*scaleRepository.findById(scaleEntity.getId()).get().setPtsBonusDeuxScoresExacts(scaleEntity.getPtsBonusDeuxScoresExacts());
            scaleRepository.findById(scaleEntity.getId()).get().setPtsBonusEcartButs(scaleEntity.getPtsBonusEcartButs());
            scaleRepository.findById(scaleEntity.getId()).get().setPtsBonusUnScoreExactResultatKO(scaleEntity.getPtsBonusUnScoreExactResultatKO());
            scaleRepository.findById(scaleEntity.getId()).get().setPtsBonusUnScoreExactResultatOK(scaleEntity.getPtsBonusUnScoreExactResultatOK());*/
            scaleRepository.flush();

            ScaleEntity scaleUpdate = scaleRepository.findById(Long.valueOf(scaleEntity.getId())).get();
            return scaleUpdate;
        }
        throw new EntityNotFoundException(bundle.getString("update_scale_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!scaleRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("scale_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        scaleRepository.deleteById(id);
    }

    public ScaleEntity find(String mail) {
        for (int i = 0; i < scaleRepository.findAll().size(); i++) {
            if (scaleRepository.findAll().get(i).getLabel().equals(mail))
                return scaleRepository.findAll().get(i);
        }
        return null;
    }
}
