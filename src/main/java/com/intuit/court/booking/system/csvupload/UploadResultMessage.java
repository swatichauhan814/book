package com.intuit.court.booking.system.csvupload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadResultMessage {

    private String statusMessage;

    private String errorMessage;

    public UploadResultMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}