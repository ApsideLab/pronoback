package com.apside.prono.repository;

import com.apside.prono.model.HistoRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoRankingRepository extends JpaRepository<HistoRankingEntity, Long> {
}
