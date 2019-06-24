package com.apside.prono.repository;

import com.apside.prono.model.HistoCalculsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoCalculsRepository extends JpaRepository<HistoCalculsEntity, Long> {
}
