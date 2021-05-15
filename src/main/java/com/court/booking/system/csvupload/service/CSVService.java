package com.court.booking.system.csvupload.service;


import com.court.booking.system.csvupload.CourtCSV;
import com.court.booking.system.csvupload.SportCSV;
import com.court.booking.system.exception.FailureCodes;
import com.court.booking.system.exception.SportManagementCSVException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.court.booking.system.helpers.DateUtil.convertToTimestamp;

@Component
@Slf4j
public class CSVService {

    private final CSVOperationService csvOperationService;

    public CSVService(CSVOperationService csvOperationService) {
        this.csvOperationService = csvOperationService;
    }

    public void validateAndSaveCourt(MultipartFile multipartFile) {

        List<CSVMapper> csvMapperList = readCSV(multipartFile, CourtCSV.class, new CourtCSV());

        List<CourtCSV> courtCSVList = csvMapperList.stream()
                .map(csvMapper -> (CourtCSV) csvMapper)
                .map(CourtCSV::trim)
                .filter(this::validateTimings)
                .collect(Collectors.toList());

        csvOperationService.saveCourt(courtCSVList);
    }

    public void validateAndSaveSport(MultipartFile multipartFile) {

        List<CSVMapper> csvMapperList = readCSV(multipartFile, SportCSV.class, new SportCSV());

        List<SportCSV> sportCSVList = csvMapperList.stream()
                .map(csvMapper -> (SportCSV) csvMapper)
                .map(SportCSV::trim)
                .collect(Collectors.toList());

        csvOperationService.saveSport(sportCSVList);
    }

    private boolean validateTimings(CourtCSV courtCSV) {

        Timestamp openingTimestamp = convertToTimestamp(courtCSV.getOpeningTime());
        Timestamp closingTimestamp = convertToTimestamp(courtCSV.getClosingTime());

        if (!openingTimestamp.toLocalDateTime().isBefore(closingTimestamp.toLocalDateTime())) {
            log.error("Opening Time of court should be before closing time");
            throw new SportManagementCSVException("Invalid courtTimings", FailureCodes.INVALID_TIMING);
        }

        return true;
    }

    private List<CSVMapper> readCSV(MultipartFile file, Class<? extends CSVMapper> csvMapperClass,
                                    CSVMapper csvObject) throws SportManagementCSVException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {

            beanReader.getHeader(true);

            List<CSVMapper> csvMappedObjects = new ArrayList<>();
            CSVMapper csvMapperImpl = beanReader.read(csvMapperClass, csvObject.getNameMapping(), csvObject.getProcessors());

            while (csvMapperImpl != null) {
                csvMappedObjects.add(csvMapperImpl);
                csvMapperImpl = beanReader.read(csvMapperClass, csvObject.getNameMapping(), csvObject.getProcessors());
            }

            return csvMappedObjects;
        } catch (SuperCsvConstraintViolationException e) {

            String invalidValueSuppliedFromCSVForHeader = csvObject.getNameMapping()[e.getCsvContext().getColumnNumber() - 1];
            List<Object> objectList = e.getCsvContext().getRowSource();

            String court = null;

            if (objectList.get(0) != null) {
                court = String.valueOf(objectList.get(0));
            }

            throw new SportManagementCSVException("Mandatory field : " + invalidValueSuppliedFromCSVForHeader + ", is invalid/null for court : " + court + " at row number : " + e.getCsvContext().getRowNumber(), FailureCodes.CSV_MANDATORY_FIELD_IS_NULL);
        } catch (Exception e) {

            throw new SportManagementCSVException("Reading of csv file=" + file + " failed", e, FailureCodes.CSV_READING_FAILED);
        }
    }
}