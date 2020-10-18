package com.intuit.court.booking.system.service;


import com.intuit.court.booking.system.dto.UserDto;
import com.intuit.court.booking.system.entity.RoleEntity;
import com.intuit.court.booking.system.entity.UserEntity;
import com.intuit.court.booking.system.exception.FailureCodes;
import com.intuit.court.booking.system.exception.SportManagementInternalException;
import com.intuit.court.booking.system.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new SportManagementInternalException("User with this email already exists", FailureCodes.DUPLICATE_EMAIL));

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), userEntity.getRoleEntities());
    }

    public static String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public Boolean login(String username, String password) {

        boolean state;
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(token);
        state = token.isAuthenticated();

        if (state) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        return state;
    }

    public void registerUser(UserDto userDto) {

        try {

//            UserEntity userEntity = userRepository.findByEmail(userDto.getEmail())
//                    .orElseThrow(() -> new SportManagementInternalException("User with this email already exists", FailureCodes.DUPLICATE_EMAIL));

//            loadUserByUsername(userDto.getEmail());

            UserEntity user = getUser(userDto);
            userRepository.save(user);
            log.info("User registered successfully with email={}", userDto.getEmail());

        } catch (Exception e) {

            log.error("Error occurred while registering a new user", e);
            throw new SportManagementInternalException("Failed registering a new user");
        }
    }

    private UserEntity getUser(UserDto userDto) {

        Set<RoleEntity> rolesEntities = new HashSet<>();

        String encode = bCryptPasswordEncoder.encode(userDto.getPassword());

        return UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(encode)
                .roleEntities(rolesEntities)
                .build();
    }

}