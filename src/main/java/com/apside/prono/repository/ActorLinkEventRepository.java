package com.apside.prono.repository;

import com.apside.prono.model.ActorLinkEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorLinkEventRepository extends JpaRepository<ActorLinkEventEntity, Long> {
}
