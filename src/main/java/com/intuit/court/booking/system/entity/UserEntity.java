package com.intuit.court.booking.system.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_ID_SEQ_GEN")
	@SequenceGenerator(name = "USERS_ID_SEQ_GEN", sequenceName = "USERS_ID_SEQ", allocationSize = 1)
	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLES_ID"))
	private Set<RolesEntity> roles;
}