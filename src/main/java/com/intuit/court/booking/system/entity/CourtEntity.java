package com.intuit.court.booking.system.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "COURT")
@Getter
@Setter
@EqualsAndHashCode
public class CourtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURT_ID_SEQ_GEN")
    @SequenceGenerator(name = "COURT_ID_SEQ_GEN", sequenceName = "COURT_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "OPENING_TIME")
    private Timestamp openingTime;

    @Column(name = "CLOSING_TIME")
    private Timestamp closingTime;

    @OneToMany(mappedBy = "courtEntity", fetch = FetchType.EAGER)
    private List<SportEntity> sportEntities;
}