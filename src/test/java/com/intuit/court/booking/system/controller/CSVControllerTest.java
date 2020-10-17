package com.intuit.court.booking.system.controller;

import com.intuit.court.booking.system.csvupload.service.CSVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CSVControllerTest {

    @Mock
    private CSVService csvService;

    @Mock
    private MultipartFile mockMultipartFile;

    @InjectMocks
    private CSVController csvController;

    private MockMvc mockMvc;

    private MockMultipartFile emptyFile = new MockMultipartFile("file", "Court.csv", MediaType.MULTIPART_FORM_DATA_VALUE, "".getBytes());

    private final String SUCCESS_STATUS_MESSAGE = "{\"statusMessage\":CSV successfully uploaded.\",\"errorMessage\":null}";

    private final String FILE_CANNOT_BE_EMPTY = "{\"statusMessage\":File can't be Empty\",\"errorMessage\":null}";

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(csvController)
                .build();
    }

//    @Test
//    public void shouldUploadCourtCsv() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("admin/file/upload/court", multipartFile)
//                .file(file))
//                .andExpect(status().is(200))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(content().string(SUCCESS_STATUS_MESSAGE));
//    }

    @Test
    public void shouldNotUploadCourtCsvAsFileIsEmpty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/file/upload/court", mockMultipartFile)
                .file(emptyFile))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(FILE_CANNOT_BE_EMPTY));
    }
}