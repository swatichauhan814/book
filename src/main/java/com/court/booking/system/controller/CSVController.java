package com.court.booking.system.controller;


import com.court.booking.system.csvupload.UploadResultMessage;
import com.court.booking.system.csvupload.service.CSVService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class CSVController {

    private static final String SUCCESS_STATUS_MESSAGE = "CSV uploaded successfully!";

    private static final String FILE_CANNOT_BE_EMPTY = "File can't be empty";

    private static final String ERROR_OCCURRED_WILED_UPLOADING_CSV = "Error occurred while uploading CSV";

    private final CSVService csvService;


    /**
     *
     * @param multipartFile for uploading court csv file
     * @return Return Json object with Message with No of successful Transaction
     */
    @PostMapping("/file/upload/court")
    public UploadResultMessage uploadCourtCsv(@RequestParam("file") MultipartFile multipartFile) {

        // Setting default statusMessage
        UploadResultMessage uploadResultMessage = new UploadResultMessage(FILE_CANNOT_BE_EMPTY);

        if (!multipartFile.isEmpty()) {

            try {

                csvService.validateAndSaveCourt(multipartFile);
                uploadResultMessage.setStatusMessage(SUCCESS_STATUS_MESSAGE);
            } catch (Exception e) {
                log.error("Error occurred while uploading Court Csv");
                uploadResultMessage.setStatusMessage(ERROR_OCCURRED_WILED_UPLOADING_CSV);
                uploadResultMessage.setErrorMessage(e.getMessage());
            }
        }

        return uploadResultMessage;
    }

    /**
     *
     * @param multipartFile for uploading sport csv file
     * @return Return Json object with Message with No of successful Transaction
     */
    @PostMapping("/file/upload/sport")
    public UploadResultMessage uploadSportCsv(@RequestParam("file") MultipartFile multipartFile) {

        // Setting default statusMessage
        UploadResultMessage uploadResultMessage = new UploadResultMessage(FILE_CANNOT_BE_EMPTY);

        if (!multipartFile.isEmpty()) {

            try {

                csvService.validateAndSaveSport(multipartFile);
                uploadResultMessage.setStatusMessage(SUCCESS_STATUS_MESSAGE);
            } catch (Exception e) {
                log.error("Error occurred while uploading Sport Csv");
                uploadResultMessage.setStatusMessage(ERROR_OCCURRED_WILED_UPLOADING_CSV);
                uploadResultMessage.setErrorMessage(e.getMessage());
            }
        }

        return uploadResultMessage;
    }
}
