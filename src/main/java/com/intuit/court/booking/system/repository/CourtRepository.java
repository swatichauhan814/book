package com.intuit.court.booking.system.repository;

import com.intuit.court.booking.system.entity.CourtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourtRepository extends JpaRepository<CourtEntity, Long> {

    @Override
    List<CourtEntity> findAll();

    @Query(value = "select c.NAME, c.CITY, sp.PRICE, sp.NAME from sport sp\n" +
            "join court c on sp.COURT_ID = c.ID", nativeQuery = true)
    List<CourtEntity> getAllCourts();

    @Override
    Optional<CourtEntity> findById(Long aLong);

    Optional<CourtEntity> findByNameAndCity(String name, String city);
}