package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.tryEventLink.BadRequestCreateTypeEventLinkException;
import com.apside.prono.model.TypeEventLinkEntity;
import com.apside.prono.repository.TypeEventLinkRepository;
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
public class TypeEventLinkService {
    @Autowired
    private TypeEventLinkRepository typeEventLinkRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    @Transactional
    public TypeEventLinkEntity createTypeEventLink(TypeEventLinkEntity typeEventLinkEntity) {
        if(typeEventLinkEntity != null) {
            if (typeEventLinkEntity.getId() != null) {
                throw new BadRequestCreateTypeEventLinkException(bundle.getString("new_type_event_link_create"));
            }
            return typeEventLinkRepository.save(typeEventLinkEntity);
        }
        throw new BadRequestCreateTypeEventLinkException(bundle.getString("new_type_event_link_empty"));
    }

    public TypeEventLinkEntity getTypeEventLink(long id) {
        Optional<TypeEventLinkEntity> typeEventLinkEntity = typeEventLinkRepository.findById(id);
        if (!typeEventLinkEntity.isPresent()) {
            String pattern = bundle.getString("type_event_link_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return typeEventLinkEntity.get();
    }

    @Transactional
    public List<TypeEventLinkEntity> getAll() {
        return typeEventLinkRepository.findAll();
    }

    @Transactional
    public TypeEventLinkEntity update(TypeEventLinkEntity typeEventLinkEntity) {
        if(typeEventLinkEntity != null) {
            Optional<TypeEventLinkEntity> typeEventLink = typeEventLinkRepository.findById(typeEventLinkEntity.getId());
            if (!typeEventLink.isPresent()) {
                String pattern = bundle.getString("type_event_link_wrong_id");
                String message = MessageFormat.format(pattern, typeEventLinkEntity.getId());
                throw new EntityNotFoundException(message);
            }
            typeEventLinkRepository.findById(typeEventLinkEntity.getId()).get().setType(typeEventLinkEntity.getType());
            typeEventLinkRepository.findById(typeEventLinkEntity.getId()).get().setEvent(typeEventLinkEntity.getEvent());

            TypeEventLinkEntity typeEventLinkEntity1 = typeEventLinkRepository.findById(typeEventLinkEntity.getId()).get();
            typeEventLinkRepository.flush();
            return typeEventLinkEntity1;
        }
        throw new EntityNotFoundException(bundle.getString("update_type_event_link_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!typeEventLinkRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("type_event_link_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        typeEventLinkRepository.deleteById(id);
    }
}
