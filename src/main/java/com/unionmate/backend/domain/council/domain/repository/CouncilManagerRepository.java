package com.unionmate.backend.domain.council.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;

@Repository
public interface CouncilManagerRepository extends JpaRepository<CouncilManager, Long> {
}