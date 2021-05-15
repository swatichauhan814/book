package com.court.booking.system.service;


import com.court.booking.system.entity.BookingEntity;
import com.court.booking.system.entity.CourtEntity;
import com.court.booking.system.entity.SportEntity;
import com.court.booking.system.entity.UserEntity;
import com.court.booking.system.exception.FailureCodes;
import com.court.booking.system.dto.BookingDto;
import com.court.booking.system.exception.SportManagementCSVException;
import com.court.booking.system.repository.BookingRepository;
import com.court.booking.system.repository.CourtRepository;
import com.court.booking.system.repository.SportRepository;
import com.court.booking.system.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.court.booking.system.helpers.DateUtil.convertToTimestamp;
import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
public class BookingService {

    private final CourtRepository courtRepository;

    private final SportRepository sportRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final CourtService courtService;

    @Autowired
    public BookingService(CourtRepository courtRepository, SportRepository sportRepository, CourtService courtService, BookingRepository bookingRepository, UserRepository userRepository) {
        this.courtRepository = courtRepository;
        this.sportRepository = sportRepository;
        this.courtService = courtService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    private boolean validateBookingTimings(LocalDateTime startDateTime, LocalDateTime endDateTime, CourtEntity courtEntity) {

        // opening time of court - before booking
        if (!courtEntity.getOpeningTime().toLocalDateTime().isBefore(startDateTime) && !endDateTime.isBefore(courtEntity.getClosingTime().toLocalDateTime())
                && !startDateTime.isBefore(endDateTime)) {
            log.error("Opening Time of court should be before closing time");
            throw new SportManagementCSVException("Invalid courtTimings", FailureCodes.INVALID_TIMING);
        }

        return true;
    }

    public void findMaxBooking() {

        List<BookingEntity> bookingEntities = bookingRepository.findAll();

        Map<Integer, List<BookingEntity>> collect = bookingEntities.stream().collect(groupingBy(bookingEntity -> bookingEntity.getStartTime().getDate()));

        int maxi = Integer.MIN_VALUE;
        LocalDateTime maxDate = null;

        for (Map.Entry<Integer, List<BookingEntity>> entry : collect.entrySet()) {

            int bookingSize = entry.getValue().size();

            if (bookingSize > maxi) {

                maxDate = entry.getValue().get(0).getStartTime().toLocalDateTime();
                maxi = bookingSize;
            }
        }

        Map<UserEntity, List<BookingEntity>> userEntityListMap = bookingEntities.stream().collect(groupingBy(BookingEntity::getUserEntity));

        System.out.println(maxDate);

    }

    public synchronized BookingDto bookCourt(String email, String sportId, String startDateTime, String endDateTime) {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

//        String name = authentication.getName();
//        Object principal = authentication.getPrincipal();

        Optional<SportEntity> sportEntity = sportRepository.findById(Long.parseLong(sportId));

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        CourtEntity courtEntity = sportEntity.get().getCourtEntity();

        Timestamp bookStartTimestamp = convertToTimestamp(startDateTime);
        Timestamp bookEndTimestamp = convertToTimestamp(endDateTime);

        if (validateBookingTimings(bookStartTimestamp.toLocalDateTime(), bookEndTimestamp.toLocalDateTime(), courtEntity) &&
                getAvailableSlotsForGivenCourt(String.valueOf(courtEntity.getId()), sportId, bookStartTimestamp.toLocalDateTime(), bookEndTimestamp.toLocalDateTime())) {

            Long price = sportEntity.get().getPrice();

            BookingEntity bookingEntity = new BookingEntity();


            bookingEntity.setAmount(price * (bookEndTimestamp.toLocalDateTime().getHour() - bookStartTimestamp.toLocalDateTime().getHour()));
            bookingEntity.setStartTime(bookStartTimestamp);
            bookingEntity.setEndTime(bookEndTimestamp);
            bookingEntity.setSportEntity(sportEntity.get());
            bookingEntity.setUserEntity(userEntity.get());

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