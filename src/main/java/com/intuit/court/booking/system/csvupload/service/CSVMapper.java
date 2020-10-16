package com.intuit.court.booking.system.csvupload.service;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface CSVMapper {

    String[] getNameMapping();

    CellProcessor[] getProcessors();
}