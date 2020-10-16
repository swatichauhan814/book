package com.intuit.court.booking.system.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BOOKING")
@Getter
@Setter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKING_ID_SEQ_GEN")
    @SequenceGenerator(name = "BOOKING_ID_SEQ_GEN", sequenceName = "BOOKING_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "AMOUNT")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "SPORT_ID")
    private SportEntity sportEntity;
}