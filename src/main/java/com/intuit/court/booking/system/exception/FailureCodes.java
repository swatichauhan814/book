package com.intuit.court.booking.system.exception;

public enum FailureCodes {

    COURT_IS_NULL("Court from db is null"),
    CSV_MANDATORY_FIELD_IS_NULL("Mandatory field is not on CSV"),
    CSV_READING_FAILED("Reading of CSV file failed"),
    INVALID_TIMING("Invalid Court Timings"),
    BOOKING_FAILED("Booking failed. Please try a different slot"),
    CSV_INVALID_COURT_ID("Invalid Court Id"),
    DUPLICATE_EMAIL("User with this email already exists!"),
    UNABLE_TO_FETCH_COURT("Unable to fetch court from the database");

    private String message;

    FailureCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
