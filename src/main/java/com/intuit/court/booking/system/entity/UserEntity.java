package com.intuit.court.booking.system.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "USER")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ_GEN")
    @SequenceGenerator(name = "USER_ID_SEQ_GEN", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @ManyToMany
    @JoinTable(name = "USER_ROLE",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
    private Set<RoleEntity> roleEntities;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<BookingEntity> bookingEntities;
}