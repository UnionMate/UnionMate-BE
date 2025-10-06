package com.unionmate.backend.domain.recruitment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
