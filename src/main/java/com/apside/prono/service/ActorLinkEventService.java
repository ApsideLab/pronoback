package com.apside.prono.service;

import com.apside.prono.errors.actorLinkEvent.BadRequestCreateActorLinkEventException;
import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.repository.ActorLinkEventRepository;
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
public class ActorLinkEventService {
    @Autowired
    private ActorLinkEventRepository actorLinkEventRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    @Transactional
    public ActorLinkEventEntity createActorLinkEvent(ActorLinkEventEntity actorLinkEventEntity) {
        if (actorLinkEventEntity != null) {
            if (actorLinkEventEntity.getId() != null) {
                throw new BadRequestCreateActorLinkEventException(bundle.getString("new_actor_link_event_create"));
            }
            return actorLinkEventRepository.save(actorLinkEventEntity);
        }
        throw new BadRequestCreateActorLinkEventException(bundle.getString("new_actor_link_event_empty"));
    }

    public ActorLinkEventEntity getActorLinkEvent(long id) {
        Optional<ActorLinkEventEntity> actorLinkEventEntity = actorLinkEventRepository.findById(id);
        if (!actorLinkEventEntity.isPresent()) {
            String pattern = bundle.getString("actor_link_event_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return actorLinkEventEntity.get();
    }

    @Transactional
    public List<ActorLinkEventEntity> getAll() {
        return actorLinkEventRepository.findAll();
    }

    @Transactional
    public ActorLinkEventEntity update(ActorLinkEventEntity actorLinkEventEntity) {
        if (actorLinkEventEntity != null) {
            Optional<ActorLinkEventEntity> actorLinkEvent = actorLinkEventRepository.findById(actorLinkEventEntity.getId());
            if (!actorLinkEvent.isPresent()) {
                String pattern = bundle.getString("actor_link_event_wrong_id");
                String message = MessageFormat.format(pattern, actorLinkEventEntity.getId());
                throw new EntityNotFoundException(message);
            }
            actorLinkEventRepository.findById(actorLinkEventEntity.getId()).get().setActor(actorLinkEventEntity.getActor());
            actorLinkEventRepository.findById(actorLinkEventEntity.getId()).get().setEvent(actorLinkEventEntity.getEvent());

            ActorLinkEventEntity actorLinkEventEntity1 = actorLinkEventRepository.findById(actorLinkEventEntity.getId()).get();
            actorLinkEventRepository.flush();
            return actorLinkEventEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_actor_link_event_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!actorLinkEventRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("actor_link_event_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        actorLinkEventRepository.deleteById(id);
    }
}
