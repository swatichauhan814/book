package com.intuit.court.booking.system.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_ID_SEQ_GEN")
    @SequenceGenerator(name = "ROLES_ID_SEQ_GEN", sequenceName = "ROLES_ID_SEQ", allocationSize = 1)
    @Column(name = "ROLES_ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

}