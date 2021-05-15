package com.court.booking.system.controller;


import com.court.booking.system.dto.CourtDto;
import com.court.booking.system.service.CourtService;
import com.court.booking.system.exception.FailureCodes;
import com.court.booking.system.exception.SportManagementInternalException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/court")
public class CourtsController {

    private final CourtService courtService;

    @GetMapping("/get/all/")
    public List<CourtDto> getAllCourts() {
        try {

            return courtService.getAllCourtEntities();
        } catch (Exception e) {

            log.error("Fetching court details from the db threw exception", e);
            throw new SportManagementInternalException("Unable to fetch courts!", FailureCodes.UNABLE_TO_FETCH_COURT, e);
        }
    }

    @GetMapping("get/time/slot/{courtId}/{sportId}/{date}")
    public List<String> getAvailableTimeSlots(@PathVariable("courtId") String courtId, @PathVariable("sportId") String sportId,
                                              @PathVariable("date") String date) {

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.now());
        return courtService.getAvailableSlotsForGivenCourt(courtId, sportId, localDateTime);
    }
}
