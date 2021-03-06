package com.apside.prono.repository;

import com.apside.prono.model.TypeEventLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEventLinkRepository extends JpaRepository<TypeEventLinkEntity, Long> {
}
