package com.apside.prono.repository;

import com.apside.prono.model.PronoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PronoRepository extends JpaRepository<PronoEntity, Long> {
}
