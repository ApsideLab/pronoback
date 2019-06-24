package com.apside.prono.service;

import com.apside.prono.errors.actor.BadRequestCreateActorException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorEntity;
import com.apside.prono.repository.ActorRepository;
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
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");

    @Transactional
    public ActorEntity createActor(ActorEntity actor) {
        if (actor != null) {
            if (actor.getId() != null) {
                throw new BadRequestCreateActorException(bundle.getString("new_actor_create"));
            }
            return actorRepository.save(actor);
        }
        throw new BadRequestCreateActorException(bundle.getString("new_actor_empty"));
    }

    public ActorEntity getActor(long id) {
        Optional<ActorEntity> actor = actorRepository.findById(id);
        if (!actor.isPresent()) {
            String pattern = bundle.getString("actor_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return actor.get();
    }

    @Transactional
    public List<ActorEntity> getAll() {
        return actorRepository.findAll();
    }

    @Transactional
    public ActorEntity update(ActorEntity actorEntity) {
        if (actorEntity != null) {
            Optional<ActorEntity> actor = actorRepository.findById(actorEntity.getId());
            if (!actor.isPresent()) {
                String pattern = bundle.getString("actor_wrong_id");
                String message = MessageFormat.format(pattern, actorEntity.getId());
                throw new EntityNotFoundException(message);
            }
            actorRepository.findById(actorEntity.getId()).get().setLabel(actorEntity.getLabel());

            ActorEntity actorEntity1 = actorRepository.findById(actorEntity.getId()).get();
            actorRepository.flush();
            return actorEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_actor_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!actorRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("actor_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        actorRepository.deleteById(id);
    }

    public ActorEntity find(String label) {
        for (int i = 0; i < actorRepository.findAll().size(); i++) {
            if (actorRepository.findAll().get(i).getLabel().equals(label))
                return actorRepository.findAll().get(i);
        }
        return null;
    }
}
