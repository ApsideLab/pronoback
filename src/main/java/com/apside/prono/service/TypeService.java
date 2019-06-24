package com.apside.prono.service;

import com.apside.prono.errors.common.EntityNotFoundException;
import com.apside.prono.errors.type.BadRequestCreateTypeException;
import com.apside.prono.model.TypeEntity;
import com.apside.prono.repository.TypeRepository;
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
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private Environment env;
    private ResourceBundle bundle = ResourceBundle.getBundle("messagesServicesError");
    
    @Transactional
    public TypeEntity createType(TypeEntity typeEntity) {
        if (typeEntity != null) {
            if (typeEntity.getId() != null) {
                throw new BadRequestCreateTypeException(bundle.getString("new_type_create"));
            }
            return typeRepository.save(typeEntity);
        }

        throw new BadRequestCreateTypeException(bundle.getString("new_type_empty"));
    }

    public TypeEntity getType(long id) {
        Optional<TypeEntity> typeEntity = typeRepository.findById(id);
        if (!typeEntity.isPresent()) {
            String pattern = bundle.getString("type_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        return typeEntity.get();
    }

    @Transactional
    public List<TypeEntity> getAll() {
        return typeRepository.findAll();
    }

    @Transactional
    public TypeEntity update(TypeEntity type) {
        if (type != null) {
            if (!typeRepository.findById(type.getId()).isPresent()) {
                String pattern = bundle.getString("type_wrong_id");
                String message = MessageFormat.format(pattern, type.getId());
                throw new EntityNotFoundException(message);
            }

            typeRepository.findById(type.getId()).get().setLabel(type.getLabel());
            TypeEntity typeEntity = typeRepository.findById(type.getId()).get();

            typeRepository.flush();
            return typeEntity;
        }
        throw new EntityNotFoundException(bundle.getString("update_type_empty"));
    }

    @Transactional
    public void delete(long id) {
        if (!typeRepository.findById(id).isPresent()) {
            String pattern = bundle.getString("type_wrong_id");
            String message = MessageFormat.format(pattern, id);
            throw new EntityNotFoundException(message);
        }
        typeRepository.deleteById(id);
    }

    public TypeEntity find(String label) {
        for (int i = 0; i < typeRepository.findAll().size(); i++) {
            if (typeRepository.findAll().get(i).getLabel().equals(label))
                return typeRepository.findAll().get(i);
        }
        return null;
    }
}
