package com.intuit.court.booking.system.service;


import com.intuit.court.booking.system.dto.BookingDto;
import com.intuit.court.booking.system.entity.BookingEntity;
import com.intuit.court.booking.system.entity.CourtEntity;
import com.intuit.court.booking.system.entity.SportEntity;
import com.intuit.court.booking.system.exception.FailureCodes;
import com.intuit.court.booking.system.exception.SportManagementCSVException;
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

    private boolean validateBookingTimings(LocalDateTime startDateTime, LocalDateTime endDateTime, CourtEntity courtEntity) {

        if (!courtEntity.getOpeningTime().toLocalDateTime().isBefore(startDateTime) && !endDateTime.isBefore(courtEntity.getClosingTime().toLocalDateTime())
                && !startDateTime.isBefore(endDateTime)) {
            log.error("Opening Time of court should be before closing time");
            throw new SportManagementCSVException("Invalid courtTimings", FailureCodes.INVALID_TIMING);
        }

        return true;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
//    @Transactional(readOnly = true)
    public BookingDto bookCourt(String sportId, String startDateTime, String endDateTime) {

        Optional<SportEntity> sportEntity = sportRepository.findById(Long.parseLong(sportId));

        CourtEntity courtEntity = sportEntity.get().getCourtEntity();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime bookingStartTime = LocalDateTime.parse(startDateTime, formatter);

        LocalDateTime bookingEndDatetime = LocalDateTime.parse(endDateTime, formatter);

        if (validateBookingTimings(bookingStartTime, bookingEndDatetime, courtEntity) &&
                getAvailableSlotsForGivenCourt(String.valueOf(courtEntity.getId()), sportId, bookingStartTime, bookingEndDatetime)) {

            Long price = sportEntity.get().getPrice();

            BookingEntity bookingEntity = new BookingEntity();

            bookingEntity.setAmount(price * (bookingEndDatetime.getHour() - bookingStartTime.getHour()));
            bookingEntity.setStartTime(Timestamp.valueOf(bookingStartTime));
            bookingEntity.setEndTime(Timestamp.valueOf(bookingEndDatetime));
            bookingEntity.setSportEntity(sportEntity.get());

            bookingRepository.save(bookingEntity);

            return BookingDto.builder()
                    .sportName(sportEntity.get().getName().toString())
                    .amount(bookingEntity.getAmount())
                    .startTime(startDateTime)
                    .endTime(endDateTime)
                    .build();
        }

        return BookingDto.builder().build();
    }

    public boolean getAvailableSlotsForGivenCourt(String courtId, String sportId, LocalDateTime bookingStartDatetime, LocalDateTime bookingEndDateTime) {

        Optional<CourtEntity> courtEntity = courtRepository.findById(Long.parseLong(courtId));

        int openingTime = courtEntity.get().getOpeningTime().toLocalDateTime().toLocalTime().getHour();

        List<Integer> totalTimeSlots = courtService.getTotalTimeSlots(sportId, bookingEndDateTime, courtEntity.get().getSportEntities());

        int current = 0;

        // 12 - 5
        // 1  -1

        // 2 - 4
        for (int i = openingTime; i < bookingEndDateTime.getHour(); i++) {

            current += totalTimeSlots.get(i);

            if (current > 0 && totalTimeSlots.get(bookingStartDatetime.getHour()) > 0)
                return false;

        }

        return true;
    }

}