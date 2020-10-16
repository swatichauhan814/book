package com.intuit.court.booking.system.controller;


import com.intuit.court.booking.system.dto.BookingDto;
import com.intuit.court.booking.system.exception.FailureCodes;
import com.intuit.court.booking.system.exception.SportManagementInternalException;
import com.intuit.court.booking.system.service.BookingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    private final BookingService courtService;

    @PostMapping("book/time/slot/{sportId}/{startDateTime}/{endDateTime}")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Booked Successfully!",
                    response = BookingDto.class),
    })
    public BookingDto bookingSlot(@PathVariable("sportId") String sportId,
                                  @RequestParam("startDateTime") String startDateTime,
                                  @RequestParam("endDateTime") String endDateTime) {

        log.info("Received booking request for sportId={}, startDateTime={}, endDateTime={}", sportId, startDateTime, endDateTime);
        // a - b

        try {
            return courtService.bookCourt(sportId, startDateTime, endDateTime);
        } catch (Exception e) {

            log.error("Booking failed. Please try different slot.", e);
            throw new SportManagementInternalException("Booking failed", FailureCodes.BOOKING_FAILED);
        }

//        return BookingDto.builder().build();
    }
}
