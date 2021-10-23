package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.EmailServiceBusiness;
import com.tamercapital.tamercapital.business.abstracts.UserService;
import com.tamercapital.tamercapital.core.security.jwt.JwtUtils;
import com.tamercapital.tamercapital.core.security.response.JwtResponse;
import com.tamercapital.tamercapital.core.security.services.UserDetailsImpl;
import com.tamercapital.tamercapital.core.utilities.*;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import com.tamercapital.tamercapital.model.concretes.ERole;
import com.tamercapital.tamercapital.model.concretes.Role;
import com.tamercapital.tamercapital.model.concretes.User;
import com.tamercapital.tamercapital.repository.RoleRepository;
import com.tamercapital.tamercapital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailServiceBusiness emailService;

    @Autowired
    public UserManager(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, EmailServiceBusiness emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @Override
    public Result register(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new ErrorResult("Bu Kullanıc Adı Zaten var");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ErrorResult("Bu Email Zaten var");
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
        userRepository.save(user);
        return new SuccessResult(emailService.sendEmail(user,signupRequest.getEmail()).getMessage());
    }

    @Override
    public DataResult<JwtResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new SuccessDataResult<JwtResponse>(  new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles),"Kullanıcı Giriş Yaptı");


    }

    @Override
    public DataResult<Optional<User>> findById(String id) {
        return null;
    }

    @Override
    public DataResult<User> findByEmailIgnoreCase(String email) {

        return  new SuccessDataResult<User>(this.userRepository.findByEmailIgnoreCase(email));
    }


}
