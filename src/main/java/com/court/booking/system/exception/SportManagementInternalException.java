package com.court.booking.system.exception;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SportManagementInternalException extends RuntimeException {


    private List<FailureCodes> failureCodes = new ArrayList<>();

    public SportManagementInternalException(String message, FailureCodes failureCode, Exception e) {
        super(message, e);
        this.failureCodes.add(failureCode);
    }

    public SportManagementInternalException(String message, FailureCodes failureCode) {
        super(message);
        this.failureCodes.add(failureCode);
    }

    public SportManagementInternalException(String message, Exception e) {
        super(message, e);
    }

    public SportManagementInternalException(String message) {
        super(message);
    }
}
