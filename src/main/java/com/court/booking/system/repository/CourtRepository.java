package com.court.booking.system.repository;

import com.court.booking.system.entity.CourtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourtRepository extends JpaRepository<CourtEntity, Long> {

    @Override
    List<CourtEntity> findAll();

    @Override
    Optional<CourtEntity> findById(Long aLong);

    Optional<CourtEntity> findByNameAndCity(String name, String city);
}