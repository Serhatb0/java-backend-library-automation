package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.EmailServiceBusiness;
import com.tamercapital.tamercapital.business.abstracts.UserService;
import com.tamercapital.tamercapital.core.security.jwt.JwtUtils;
import com.tamercapital.tamercapital.core.security.services.UserDetailsServiceImpl;
import com.tamercapital.tamercapital.exception.EntityNotFoundException;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import com.tamercapital.tamercapital.model.concretes.ERole;
import com.tamercapital.tamercapital.model.concretes.Role;
import com.tamercapital.tamercapital.model.concretes.User;
import com.tamercapital.tamercapital.repository.RoleRepository;
import com.tamercapital.tamercapital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserManager implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailServiceBusiness emailService;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserManager(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, EmailServiceBusiness emailService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public User register(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new EntityNotFoundException("Bu Kullanıcı Adı Zaten Var");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EntityNotFoundException("Bu Email Zaten Mevcut");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Rol Bulunamadı."));
            roles.add(userRole);

        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Rol Bulunamadı."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Rol Bulunamadı"));
                        roles.add(modRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);

                }
            });
        }

        user.setRoles(roles);
        emailService.sendEmail(user,user.getEmail());
        return userRepository.save(user);

    }

    @Override
    public String login(LoginRequest loginRequest ,HttpSession session) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException ex) {

        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        session.setAttribute("username",userDetails.getUsername());
        session.setAttribute("password",userDetails.getPassword());

        return jwt;


    }

    @Override
    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {

        return this.userRepository.findByEmailIgnoreCase(email);
    }


}
