package com.intuit.court.booking.system.exception;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SportManagementCSVException extends RuntimeException {

    private FailureCodes failureCodes;

    public SportManagementCSVException(String message, FailureCodes failureCode) {
        super(message);
        this.failureCodes = failureCode;
    }

    public SportManagementCSVException(String message, Throwable cause, FailureCodes failureCode) {
        super(message, cause);
        this.failureCodes = failureCode;
    }

    @Override
    public String toString() {
        return "SportManagementCSVException{" +
                "failureCodes=" + failureCodes + '}';
    }
}
