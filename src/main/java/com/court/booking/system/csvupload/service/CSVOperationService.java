package com.court.booking.system.csvupload.service;


import com.court.booking.system.entity.CourtEntity;
import com.court.booking.system.entity.SportEntity;
import com.court.booking.system.enums.SportType;
import com.court.booking.system.exception.FailureCodes;
import com.court.booking.system.repository.CourtRepository;
import com.court.booking.system.repository.SportRepository;
import com.court.booking.system.csvupload.CourtCSV;
import com.court.booking.system.csvupload.SportCSV;
import com.court.booking.system.exception.SportManagementCSVException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.court.booking.system.helpers.DateUtil.convertToTimestamp;

@Service
public class CSVOperationService {

    private final CourtRepository courtRepository;

    private final SportRepository sportRepository;

    public CSVOperationService(CourtRepository courtRepository, SportRepository sportRepository) {
        this.courtRepository = courtRepository;
        this.sportRepository = sportRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveCourt(List<CourtCSV> courtCSVList) {

        for (CourtCSV courtCSV : courtCSVList) {

            Optional<CourtEntity> existingCourt = courtRepository.findByNameAndCity(courtCSV.getName(), courtCSV.getCity());

            CourtEntity newCourt = existingCourt.orElseGet(CourtEntity::new);

            CourtEntity courtEntity = convertToCourtEntity(courtCSV, newCourt);

            courtRepository.save(courtEntity);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveSport(List<SportCSV> courtCSVList) {

        for (SportCSV courtCSV : courtCSVList) {

            Optional<CourtEntity> existingCourt = courtRepository.findById(Long.parseLong(courtCSV.getCourtId()));

            if (existingCourt.isPresent()) {

                Optional<SportEntity> byNameAndCourt = sportRepository.findByNameAndCourtEntity(SportType.valueOf(courtCSV.getName()), existingCourt.get());

                SportEntity newSport = byNameAndCourt.orElseGet(SportEntity::new);

                SportEntity sportEntity = convertToSportEntity(courtCSV, newSport, existingCourt.get());

                sportRepository.save(sportEntity);

            } else {
                throw new SportManagementCSVException("Invalid court id " + courtCSV.getCourtId(), FailureCodes.CSV_INVALID_COURT_ID);
            }

        }
    }

    private SportEntity convertToSportEntity(SportCSV sportCSV, SportEntity sportEntity, CourtEntity courtEntity) {

        sportEntity.setPrice(Long.parseLong(sportCSV.getPrice()));
        sportEntity.setName(SportType.valueOf(sportCSV.getName()));
        sportEntity.setCourtEntity(courtEntity);

        return sportEntity;
    }

    private CourtEntity convertToCourtEntity(CourtCSV courtCSV, CourtEntity courtEntity) {

        courtEntity.setName(courtCSV.getName());
        courtEntity.setCity(courtCSV.getCity());
        courtEntity.setOpeningTime(convertToTimestamp(courtCSV.getOpeningTime()));
        courtEntity.setClosingTime(convertToTimestamp(courtCSV.getClosingTime()));

        return courtEntity;
    }
}