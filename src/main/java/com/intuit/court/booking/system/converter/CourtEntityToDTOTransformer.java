package com.intuit.court.booking.system.converter;


import com.intuit.court.booking.system.dto.CourtDto;
import com.intuit.court.booking.system.dto.SportDto;
import com.intuit.court.booking.system.entity.CourtEntity;
import com.intuit.court.booking.system.entity.SportEntity;
import com.intuit.court.booking.system.exception.FailureCodes;
import com.intuit.court.booking.system.exception.SportManagementInternalException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class CourtEntityToDTOTransformer {

    public static CourtDto convert(CourtEntity courtEntity) {

        if (Objects.nonNull(courtEntity)) {

            return CourtDto.builder()
                    .city(courtEntity.getCity())
                    .name(courtEntity.getName())
                    .sportDtoList(buildSportDetails(courtEntity.getSportEntities()))
                    .build();

        } else {
            log.error("Court Entity is null");
            throw new SportManagementInternalException("Court Entity is null", FailureCodes.COURT_IS_NULL);
        }
    }

    private static List<SportDto> buildSportDetails(List<SportEntity> sportEntities) {

        return sportEntities.stream()
                .map(CourtEntityToDTOTransformer::buildSport)
                .collect(Collectors.toList());
    }

    private static SportDto buildSport(SportEntity sportEntity) {

        return SportDto.builder()
                .name(sportEntity.getName().name())
                .price(sportEntity.getPrice())
                .build();
    }
}
