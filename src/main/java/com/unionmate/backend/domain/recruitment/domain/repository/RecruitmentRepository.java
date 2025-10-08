package com.unionmate.backend.domain.recruitment.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

	@Query("""
		select distinct r
		from Recruitment r
		  left join fetch r.items i
		  left join fetch treat(i as SelectItem).selectItemOptions o
		where r.id = :id
		""")
	Optional<Recruitment> findFormById(@Param("id") Long id);
}
