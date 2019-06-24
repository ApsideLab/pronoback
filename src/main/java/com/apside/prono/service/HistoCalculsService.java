package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.histoCalculs.BadRequestCreateHistoCalculsException;
import com.apside.prono.model.HistoCalculsEntity;
import com.apside.prono.repository.HistoCalculsRepository;
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
public class HistoCalculsService {
    @Autowired
    private HistoCalculsRepository histoCalculsRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public HistoCalculsEntity createHistoCalculs(HistoCalculsEntity histoCalculsEntity) {
        if (histoCalculsEntity != null) {
            if (histoCalculsEntity.getId() != null) {
                throw new BadRequestCreateHistoCalculsException(bundle.getString("new_histo_calculs_create"));
            }
            return histoCalculsRepository.save(histoCalculsEntity);
        }
        throw new BadRequestCreateHistoCalculsException(bundle.getString("new_histo_calculs_empty"));
    }

    public HistoCalculsEntity getHistoCalculs(long id) {
        Optional<HistoCalculsEntity> histoCalculsEntity = histoCalculsRepository.findById(id);
        if (!histoCalculsEntity.isPresent()) {
            String pattern = bundle.getString("histo_calculs_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return histoCalculsEntity.get();
    }

    @Transactional
    public List<HistoCalculsEntity> getAll() {
        return histoCalculsRepository.findAll();
    }

    @Transactional
    public HistoCalculsEntity update(HistoCalculsEntity histoCalculsEntity) {
        if (histoCalculsEntity != null) {
            Optional<HistoCalculsEntity> histoCalculs = histoCalculsRepository.findById(histoCalculsEntity.getId());
            if (!histoCalculs.isPresent()) {
                String pattern = bundle.getString("histo_calculs_wrong_id");
                String message = MessageFormat.format(pattern, histoCalculsEntity.getId());
                throw new EntityNotFoundException(message);
            }
            histoCalculsRepository.findById(histoCalculsEntity.getId()).get().setRankHistoCalculs(histoCalculsEntity.getRankHistoCalculs());
            histoCalculsRepository.findById(histoCalculsEntity.getId()).get().getContest().setId(histoCalculsEntity.getContest().getId());
            histoCalculsRepository.findById(histoCalculsEntity.getId()).get().getContest().setLabel(histoCalculsEntity.getContest().getLabel());

            HistoCalculsEntity histoCalculsEntity1 = histoCalculsRepository.findById(histoCalculsEntity.getId()).get();
            histoCalculsRepository.flush();
            return histoCalculsEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_histo_calculs_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!histoCalculsRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("histo_calculs_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        histoCalculsRepository.deleteById(id);
    }

}
