package com.court.booking.system.controller;

import com.court.booking.system.TestUtils;
import com.court.booking.system.dto.CourtDto;
import com.court.booking.system.service.CourtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(MockitoExtension.class)
class CourtsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourtService courtService;

    @InjectMocks
    private CourtsController courtsController;

    private List<CourtDto> courtDtos;

    @BeforeEach
    public void setUp() {
        courtsController = new CourtsController(courtService);

//        this.mockMvc = MockMvcBuilders.standaloneSetup(courtsController)
//                .build();

        courtDtos = TestUtils.getCourtDTOs();
    }

    @Test
    public void getCourtSuccessfully() throws Exception {

        courtsController.getAllCourts();
        courtsController.getAvailableTimeSlots("1", "1", "2020-10-19");

//        when(courtService.getAllCourtEntities()).thenReturn(courtDtos);

//        this.mockMvc.perform(get("/court/get/all/")
//                .content(courtDtos.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }
}