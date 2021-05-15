package com.court.booking.system.controller;


import com.court.booking.system.exception.FailureCodes;
import com.court.booking.system.exception.SportManagementInternalException;
import com.court.booking.system.service.BookingService;
import com.court.booking.system.dto.BookingDto;
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

    private final BookingService bookingService;

    @PostMapping("/book/time/slot/{sportId}/{email}")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Booked Successfully!",
                    response = BookingDto.class),
    })
    public BookingDto bookingSlot(@PathVariable("sportId") String sportId,
                                  @PathVariable("email") String email,
                                  @RequestParam("startDateTime") String startDateTime,
                                  @RequestParam("endDateTime") String endDateTime) {

        log.info("Received booking request for sportId={}, startDateTime={}, endDateTime={}", sportId, startDateTime, endDateTime);
        // a - b

        try {
            return bookingService.bookCourt(email, sportId, startDateTime, endDateTime);
        } catch (Exception e) {

            log.error("Booking failed. Please try different slot.", e);
            throw new SportManagementInternalException("Booking failed", FailureCodes.BOOKING_FAILED);
        }

//        return BookingDto.builder().build();
    }


    @GetMapping("get/max/booking")
    public void getAvailableTimeSlots() {

        bookingService.findMaxBooking();
    }
}
