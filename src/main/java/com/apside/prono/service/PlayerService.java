package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.player.BadRequestCreatePlayerException;
import com.apside.prono.model.PlayerEntity;
import com.apside.prono.repository.PlayerRepository;
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
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public PlayerEntity createPlayer(PlayerEntity playerEntity) {
        if(playerEntity != null) {
            if (playerEntity.getId() != null) {
                throw new BadRequestCreatePlayerException(bundle.getString("new_player_create"));
            }
            return playerRepository.save(playerEntity);
        }
        throw new BadRequestCreatePlayerException(bundle.getString("new_player_empty"));
    }

    public PlayerEntity getPlayer(long id) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if (!playerEntity.isPresent()) {
            String pattern = bundle.getString("player_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return playerEntity.get();
    }

    @Transactional
    public List<PlayerEntity> getAll() {
        return playerRepository.findAll();
    }

    @Transactional
    public PlayerEntity update(PlayerEntity playerEntity) {
        if(playerEntity !=null) {
            Optional<PlayerEntity> player = playerRepository.findById(playerEntity.getId());
            if (!player.isPresent()) {
                String pattern = bundle.getString("player_wrong_id");
                String message = MessageFormat.format(pattern, playerEntity.getId());
                throw new EntityNotFoundException(message);
            }
            playerRepository.findById(playerEntity.getId()).get().setSubscribeDate(playerEntity.getSubscribeDate());
            playerRepository.findById(playerEntity.getId()).get().setMail(playerEntity.getMail());
            playerRepository.findById(playerEntity.getId()).get().setLastName(playerEntity.getLastName());
            playerRepository.findById(playerEntity.getId()).get().setFirstName(playerEntity.getFirstName());
            PlayerEntity playerEntity1 = playerRepository.findById(playerEntity.getId()).get();
            playerRepository.flush();
            return playerEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_player_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!playerRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("player_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        playerRepository.deleteById(id);
    }

    public PlayerEntity find(String mail) {
        for (int i = 0; i < playerRepository.findAll().size(); i++) {
            if (playerRepository.findAll().get(i).getMail().equals(mail))
                return playerRepository.findAll().get(i);
        }
        return null;
    }
}
