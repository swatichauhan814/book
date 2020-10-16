package com.intuit.court.booking.system.entity;


import com.intuit.court.booking.system.enums.SportType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SPORT")
@Getter
@Setter
public class SportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPORT_ID_SEQ_GEN")
    @SequenceGenerator(name = "SPORT_ID_SEQ_GEN", sequenceName = "SPORT_ID_SEQ", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private SportType name;

    @Column(name = "PRICE")
    private Long price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COURT_ID")
    private CourtEntity courtEntity;

    @OneToMany(mappedBy = "sportEntity", fetch = FetchType.EAGER)
    private List<BookingEntity> bookingEntities;
}