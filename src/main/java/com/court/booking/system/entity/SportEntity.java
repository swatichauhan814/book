package com.court.booking.system.entity;


import com.court.booking.system.enums.SportType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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