package com.court.booking.system.repository;

import com.court.booking.system.entity.CourtEntity;
import com.court.booking.system.entity.SportEntity;
import com.court.booking.system.enums.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends JpaRepository<SportEntity, Long> {

    @Override
    List<SportEntity> findAll();

    @Override
    Optional<SportEntity> findById(Long aLong);

    Optional<SportEntity> findByNameAndCourtEntity(SportType name, CourtEntity courtEntity);
}