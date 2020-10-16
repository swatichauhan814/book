package com.intuit.court.booking.system.csvupload;

import com.intuit.court.booking.system.csvupload.service.CSVMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;


@Data
public class SportCSV implements CSVMapper {

    private String courtId;

    private String name;

    private String price;

    public static final String[] SPORT_NAME_MAPPING = new String[] {
            "courtId",
            "name",
            "price"
    };

    /*
    Name mapping which will map to the CSV column
     */
    public String[] getNameMapping() {
        return SPORT_NAME_MAPPING;
    }

    @Override
    public CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new NotNull(),
                new NotNull()
        };

        return processors;
    }

    private static String trimAttribute(String attribute) {
        return StringUtils.trim(attribute);
    }

    public static SportCSV trim(SportCSV courtCSV) {

        courtCSV.setName(trimAttribute(courtCSV.getName()));
        courtCSV.setPrice(trimAttribute(courtCSV.getPrice()));
        courtCSV.setCourtId(trimAttribute(courtCSV.getCourtId()));

        return courtCSV;
    }
}
