package com.intuit.court.booking.system.csvupload;

import com.intuit.court.booking.system.csvupload.service.CSVMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;


@Data
public class CourtCSV implements CSVMapper {

    private String name;

    private String city;

    private String openingTime;

    private String closingTime;

    public static final String[] COURT_NAME_MAPPING = new String[] {
            "name",
            "city",
            "openingTime",
            "closingTime"
    };

    /*
    Name mapping which will map to the CSV column
     */
    public String[] getNameMapping() {
        return COURT_NAME_MAPPING;
    }

    @Override
    public CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull()
        };

        return processors;
    }

    private static String trimAttribute(String attribute) {
        return StringUtils.trim(attribute);
    }

    public static CourtCSV trim(CourtCSV courtCSV) {

        courtCSV.setName(trimAttribute(courtCSV.getName()));
        courtCSV.setCity(trimAttribute(courtCSV.getCity()));
        courtCSV.setOpeningTime(trimAttribute(courtCSV.getOpeningTime()));
        courtCSV.setClosingTime(trimAttribute(courtCSV.getClosingTime()));

        return courtCSV;
    }
}
