package com.apside.prono.repository;

import com.apside.prono.model.EvenementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvenementRepository extends JpaRepository<EvenementEntity, Long> {
}
