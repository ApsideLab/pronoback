package com.apside.prono.repository;

import com.apside.prono.model.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<ContestEntity, Long> {
}
