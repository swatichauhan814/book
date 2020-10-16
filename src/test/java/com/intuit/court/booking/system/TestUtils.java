package com.intuit.court.booking.system;

import com.intuit.court.booking.system.dto.CourtDto;
import com.intuit.court.booking.system.entity.CourtEntity;
import com.intuit.court.booking.system.entity.SportEntity;
import com.intuit.court.booking.system.enums.SportType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private static final String COURT_CITY = "Bangalore";

    private static final String COURT_NAME = "Play 365";

    public static CourtEntity getCourtEntity() {

        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setCity(COURT_CITY);
        courtEntity.setName(COURT_NAME);
        courtEntity.setOpeningTime(Timestamp.valueOf("2020-10-09 06:00:00.0"));
        courtEntity.setClosingTime(Timestamp.valueOf("2020-10-09 21:00:00.0"));
        courtEntity.setSportEntities(getSportEntities());

        return courtEntity;
    }

    public static List<SportEntity> getSportEntities() {

        List<SportEntity> sportEntities = new ArrayList<>();

        SportEntity sp1 = new SportEntity();
        sp1.setName(SportType.BADMINTON);
        sp1.setPrice(300L);
        sportEntities.add(sp1);

        SportEntity sp2 = new SportEntity();
        sp2.setName(SportType.TENNIS);
        sp2.setPrice(450L);
        sportEntities.add(sp2);

        return sportEntities;
    }

    public static List<CourtDto> getCourtDTOs() {

        List<CourtDto> courtDtos = new ArrayList<>();

        CourtDto courtDto = CourtDto.builder()
                .name(COURT_NAME)
                .city(COURT_CITY)
                .build();

        courtDtos.add(courtDto);

        return courtDtos;
    }
}
