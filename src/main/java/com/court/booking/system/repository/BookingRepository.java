package com.court.booking.system.repository;


import com.court.booking.system.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Override
    List<BookingEntity> findAll();

}