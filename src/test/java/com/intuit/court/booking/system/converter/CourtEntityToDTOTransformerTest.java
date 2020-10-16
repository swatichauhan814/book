package com.intuit.court.booking.system.converter;

import com.intuit.court.booking.system.dto.CourtDto;
import org.junit.jupiter.api.Test;

import static com.intuit.court.booking.system.TestUtils.getCourtEntity;
import static org.junit.jupiter.api.Assertions.*;

class CourtEntityToDTOTransformerTest {

    @Test
    public void shouldConvertCourtToDto() {

        CourtDto courtDto = CourtEntityToDTOTransformer.convert(getCourtEntity());
        assertEquals("Bangalore", courtDto.getCity());
        assertEquals("Play 365", courtDto.getName());
    }
}