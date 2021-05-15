package com.court.booking.system.converter;

import com.court.booking.system.TestUtils;
import com.court.booking.system.dto.CourtDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtEntityToDTOTransformerTest {

    @Test
    public void shouldConvertCourtToDto() {

        CourtDto courtDto = CourtEntityToDTOTransformer.convert(TestUtils.getCourtEntity());
        assertEquals("Bangalore", courtDto.getCity());
        assertEquals("Play 365", courtDto.getName());
    }
}