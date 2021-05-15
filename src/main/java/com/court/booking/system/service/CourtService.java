package com.court.booking.system.service;


import com.court.booking.system.dto.CourtDto;
import com.court.booking.system.converter.CourtEntityToDTOTransformer;
import com.court.booking.system.entity.BookingEntity;
import com.court.booking.system.entity.CourtEntity;
import com.court.booking.system.entity.SportEntity;
import com.court.booking.system.repository.CourtRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourtService {

    private final CourtRepository courtRepository;


    @Autowired
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public List<CourtDto> getAllCourtEntities() {

        List<CourtEntity> courtEntities = courtRepository.findAll();

        return Optional.ofNullable(courtEntities)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(CourtEntityToDTOTransformer::convert)
                .collect(Collectors.toList());
    }

    public List<String> getAvailableSlotsForGivenCourt(String courtId, String sportId, LocalDateTime date) {

        Optional<CourtEntity> courtEntity = courtRepository.findById(Long.parseLong(courtId));

        int openingTime = courtEntity.get().getOpeningTime().toLocalDateTime().toLocalTime().getHour();
        int closingTime = courtEntity.get().getClosingTime().toLocalDateTime().toLocalTime().getHour();

        List<Integer> totalTimeSlots = getTotalTimeSlots(sportId, date, courtEntity.get().getSportEntities());

        List<String> availableTimeSlots = new ArrayList<>();

        int current = 0;

        for (int i = openingTime; i < closingTime; i++) {

            current += totalTimeSlots.get(i);

            if (current == 0) {

                int y = i + 1;

                String slot = String.valueOf(i);
                slot += " , ";
                slot += y;
                availableTimeSlots.add(slot);
            }
        }

        return availableTimeSlots;
    }


    public List<Integer> getTotalTimeSlots(String sportId, LocalDateTime date, List<SportEntity> sportEntities) {

        List<BookingEntity> bookingEntities = new ArrayList<>();

        // 24

        for (SportEntity sportEntity : sportEntities) {

            List<BookingEntity> bookEntities = sportEntity.getBookingEntities();

            List<BookingEntity> collect = bookEntities.stream()
                    .filter(bookingEntity -> bookingEntity.getStartTime().toLocalDateTime().toLocalDate().equals(date.toLocalDate()))
                    .filter(bookingEntity -> bookingEntity.getSportEntity().getId().equals(Long.parseLong(sportId)))
                    .collect(Collectors.toList());

            bookingEntities.addAll(collect);
        }

        List<Integer> totalTimeSlots = new ArrayList<>();

        for (int i = 0; i < 24; i++)
            totalTimeSlots.add(0);

        // 9 - 10
        // 1 -1

        // 24
        // 0 1 -1 -2

        for (BookingEntity bookingEntity : bookingEntities) {

            int startHour = bookingEntity.getStartTime().toLocalDateTime().getHour();
            int endHour = bookingEntity.getEndTime().toLocalDateTime().getHour();

            totalTimeSlots.set(startHour, totalTimeSlots.get(startHour) + 1);
            totalTimeSlots.set(endHour, totalTimeSlots.get(endHour) - 1);
        }

        return totalTimeSlots;
    }

}