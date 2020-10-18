package com.intuit.court.booking.system.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "ROLE")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_ID_SEQ_GEN")
    @SequenceGenerator(name = "ROLE_ID_SEQ_GEN", sequenceName = "ROLE_ID_SEQ", allocationSize = 1)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "roleEntities")
    private Set<UserEntity> userEntities;

    @Override
    public String getAuthority() {
        return name;
    }
}