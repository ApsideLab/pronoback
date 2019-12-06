package com.apside.prono.repository;

import com.apside.prono.model.ScaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<ScaleEntity, Long> {
}
