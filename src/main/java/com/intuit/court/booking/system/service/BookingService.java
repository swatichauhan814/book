package com.intuit.court.booking.system.service;


import com.intuit.court.booking.system.dto.BookingDto;
import com.intuit.court.booking.system.entity.BookingEntity;
import com.intuit.court.booking.system.entity.CourtEntity;
import com.intuit.court.booking.system.entity.SportEntity;
import com.intuit.court.booking.system.repository.BookingRepository;
import com.intuit.court.booking.system.repository.CourtRepository;
import com.intuit.court.booking.system.repository.SportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingService {

    private final CourtRepository courtRepository;

    private final SportRepository sportRepository;


    private final BookingRepository bookingRepository;

    private final CourtService courtService;

    @Autowired
    public BookingService(CourtRepository courtRepository, SportRepository sportRepository, CourtService courtService, BookingRepository bookingRepository) {
        this.courtRepository = courtRepository;
        this.sportRepository = sportRepository;
        this.courtService = courtService;
        this.bookingRepository = bookingRepository;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookingDto bookCourt(String sportId, String startDateTime, String endDateTime) {

        Optional<SportEntity> sportEntity = sportRepository.findById(Long.parseLong(sportId));

        CourtEntity courtEntity = sportEntity.get().getCourtEntity();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateTime, dateTimeFormatter);

        if (getAvailableSlotsForGivenCourt(String.valueOf(courtEntity.getId()), sportId, endLocalDateTime)) {

            Long price = sportEntity.get().getPrice();

            BookingEntity bookingEntity = new BookingEntity();

            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDateTime, dateTimeFormatter);

            bookingEntity.setAmount(price * (endLocalDateTime.getHour() - startLocalDateTime.getHour()));
            bookingEntity.setStartTime(Timestamp.valueOf(endLocalDateTime));
            bookingEntity.setEndTime(Timestamp.valueOf(startLocalDateTime));
            bookingEntity.setSportEntity(sportEntity.get());

            bookingRepository.save(bookingEntity);

            return BookingDto.builder()
                    .amount(bookingEntity.getAmount())
                    .startTime(Timestamp.valueOf(startDateTime))
                    .endTime(Timestamp.valueOf(endDateTime))
                    .build();
        }

        return BookingDto.builder().build();
    }


    public boolean getAvailableSlotsForGivenCourt(String courtId, String sportId, LocalDateTime date) {

        Optional<CourtEntity> courtEntity = courtRepository.findById(Long.parseLong(courtId));

        int openingTime = courtEntity.get().getOpeningTime().toLocalDateTime().toLocalTime().getHour();
        int closingTime = courtEntity.get().getClosingTime().toLocalDateTime().toLocalTime().getHour();

        List<Integer> totalTimeSlots = courtService.getTotalTimeSlots(sportId, date, courtEntity.get().getSportEntities());

        int current = 0;

        for (int i = openingTime; i < date.getHour(); i++) {

            current += totalTimeSlots.get(i);

            if (current > 0)
                return false;

        }

        return true;
    }

}