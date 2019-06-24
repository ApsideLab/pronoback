package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.pronoPlayer.BadRequestCreatePronoPlayerException;
import com.apside.prono.model.PronoPlayerEntity;
import com.apside.prono.repository.PronoPlayerRepository;
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
public class PronoPlayerService {
    @Autowired
    private PronoPlayerRepository pronoPlayerRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public PronoPlayerEntity createPronoPlayer(PronoPlayerEntity pronoPlayerEntity) {
        if(pronoPlayerEntity != null) {
            if (pronoPlayerEntity.getId() != null) {
                throw new BadRequestCreatePronoPlayerException(bundle.getString("new_prono_player_create"));
            }
            return pronoPlayerRepository.save(pronoPlayerEntity);
        }
        throw new BadRequestCreatePronoPlayerException(bundle.getString("new_prono_player_empty"));
    }

    public PronoPlayerEntity getPronoPlayer(long id) {
        Optional<PronoPlayerEntity> pronoPlayerEntity = pronoPlayerRepository.findById(id);
        if (!pronoPlayerEntity.isPresent()) {
            String pattern = bundle.getString("prono_player_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return pronoPlayerEntity.get();
    }

    @Transactional
    public List<PronoPlayerEntity> getAll() {
        return pronoPlayerRepository.findAll();
    }

    @Transactional
    public PronoPlayerEntity update(PronoPlayerEntity pronoPlayerEntity) {
        if(pronoPlayerEntity != null) {
            Optional<PronoPlayerEntity> pronoPlayer = pronoPlayerRepository.findById(pronoPlayerEntity.getId());
            if (!pronoPlayer.isPresent()) {
                String pattern = bundle.getString("prono_player_wrong_id");
                String message = MessageFormat.format(pattern, pronoPlayerEntity.getId());
                throw new EntityNotFoundException(message);
            }
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setDate(pronoPlayerEntity.getDate());
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setActor(pronoPlayerEntity.getActor());
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setEvent(pronoPlayerEntity.getEvent());
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setPlayer(pronoPlayerEntity.getPlayer());
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setPronoDetail(pronoPlayerEntity.getPronoDetail());
            pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get().setType(pronoPlayerEntity.getType());
            PronoPlayerEntity pronoPlayerEntity1 = pronoPlayerRepository.findById(pronoPlayerEntity.getId()).get();
            pronoPlayerRepository.flush();
            return pronoPlayerEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_prono_player_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!pronoPlayerRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("prono_player_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        pronoPlayerRepository.deleteById(id);
    }
}
