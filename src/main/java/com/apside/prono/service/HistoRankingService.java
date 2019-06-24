package com.apside.prono.service;

import com.apside.prono.errors.HistoRanking.BadRequestCreateHistoRankingException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.HistoRankingEntity;
import com.apside.prono.repository.HistoRankingRepository;
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
public class HistoRankingService {
    @Autowired
    private HistoRankingRepository histoRankingRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public HistoRankingEntity createHistoRanking(HistoRankingEntity histoRankingEntity) {
        if(histoRankingEntity != null) {
            if (histoRankingEntity.getId() != null) {
                throw new BadRequestCreateHistoRankingException(bundle.getString("new_histo_ranking_create"));
            }
            return histoRankingRepository.save(histoRankingEntity);
        }
        throw new BadRequestCreateHistoRankingException(bundle.getString("new_histo_ranking_empty"));
    }

    public HistoRankingEntity getHistoRanking(long id) {
        Optional<HistoRankingEntity> histoRankingEntity = histoRankingRepository.findById(id);
        if (!histoRankingEntity.isPresent()) {
            String pattern = bundle.getString("histo_ranking_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return histoRankingEntity.get();
    }

    @Transactional
    public List<HistoRankingEntity> getAll() {
        return histoRankingRepository.findAll();
    }

    @Transactional
    public HistoRankingEntity update(HistoRankingEntity histoRankingEntity) {
        if( histoRankingEntity != null) {
            Optional<HistoRankingEntity> histoRanking = histoRankingRepository.findById(histoRankingEntity.getId());
            if (!histoRanking.isPresent()) {
                String pattern = bundle.getString("histo_ranking_wrong_id");
                String message = MessageFormat.format(pattern, histoRankingEntity.getId());
                throw new EntityNotFoundException(message);
            }
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setDate(histoRankingEntity.getDate());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setBonus(histoRankingEntity.getBonus());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setDifferencePointsRugby(histoRankingEntity.getDifferencePointsRugby());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setGoodProno(histoRankingEntity.getGoodProno());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setContest(histoRankingEntity.getContest());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setPlayer(histoRankingEntity.getPlayer());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setNbTryOkRugby(histoRankingEntity.getNbTryOkRugby());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setPoints(histoRankingEntity.getPoints());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setPosition(histoRankingEntity.getPosition());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setRank(histoRankingEntity.getRank());
            histoRankingRepository.findById(histoRankingEntity.getId()).get().setScoreOkRugby(histoRankingEntity.getScoreOkRugby());
            HistoRankingEntity histoRankingEntity1 = histoRankingRepository.findById(histoRankingEntity.getId()).get();
            histoRankingRepository.flush();
            return histoRankingEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_histo_ranking_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!histoRankingRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("histo_ranking_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        histoRankingRepository.deleteById(id);
    }
}
