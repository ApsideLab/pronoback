package com.apside.prono.repository;

import com.apside.prono.model.PronoPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PronoPlayerRepository extends JpaRepository<PronoPlayerEntity, Long> {
}
