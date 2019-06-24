package com.apside.prono.service;


import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.evenement.BadRequestCreateEvenementException;
import com.apside.prono.model.EvenementEntity;
import com.apside.prono.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    public EvenementEntity getEvenement(Long id) {
        //TODO : Le message d'erreur doit être mit dans une constant ou ENUM
        Optional<EvenementEntity> evenementEntity = evenementRepository.findById(id);

        if (!evenementEntity.isPresent()) {
            throw new EntityNotFoundException("L'évenement d'id " + id + " n'existe pas");
        }

        return evenementEntity.get();
    }

    public EvenementEntity createEvent(EvenementEntity evenement) {
        if(evenement.getId() != null) {
            throw new BadRequestCreateEvenementException("Un nouvel événement ne peut pas avoir d'id");
        }
        return evenementRepository.save(evenement);
    }

    public List<EvenementEntity> getAll() {
        return evenementRepository.findAll();
    }
}
