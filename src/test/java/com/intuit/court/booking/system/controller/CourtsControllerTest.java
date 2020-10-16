package com.intuit.court.booking.system.controller;

import com.intuit.court.booking.system.dto.CourtDto;
import com.intuit.court.booking.system.service.CourtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.intuit.court.booking.system.TestUtils.getCourtDTOs;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        this.mockMvc = MockMvcBuilders.standaloneSetup(courtsController)
                .build();

        courtDtos = getCourtDTOs();
    }

    @Test
    public void getCourtSuccessfully() throws Exception {

        when(courtService.getAllCourtEntities()).thenReturn(courtDtos);

        this.mockMvc.perform(get("/court/get/all/")
                .content(courtDtos.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}