package com.tamercapital.tamercapital.service;

import com.tamercapital.tamercapital.business.abstracts.EmailServiceBusiness;
import com.tamercapital.tamercapital.business.concretes.UserManager;
import com.tamercapital.tamercapital.core.security.jwt.JwtUtils;
import com.tamercapital.tamercapital.core.security.services.UserDetailsServiceImpl;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import com.tamercapital.tamercapital.model.concretes.User;
import com.tamercapital.tamercapital.repository.RoleRepository;
import com.tamercapital.tamercapital.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UserManagerTest {

    private UserManager userManager;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;
    private EmailServiceBusiness emailService;

    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void setUp() throws Exception {

        userRepository = Mockito.mock(UserRepository.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        jwtUtils = Mockito.mock(JwtUtils.class);
        emailService = Mockito.mock(EmailServiceBusiness.class);
        userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);


        userManager = new UserManager(authenticationManager, userRepository, roleRepository
                , encoder, jwtUtils, emailService, userDetailsService);


    }

    @Test(expected = RuntimeException.class)
    public void resgisterUserTest() {
        final User user = new User("serhat", "biricik47@gmail.com", "12345678");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        roles.add("mod");
        final SignupRequest signupRequest = new SignupRequest("serhat", "biricik47@gmail.com", "12345678", roles);
        given(userRepository.findById(user.getId())).willReturn(Optional.empty());
        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userManager.register(signupRequest);
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test(expected = RuntimeException.class)
    public void loginUserTest() {
        final LoginRequest loginRequest = new LoginRequest("serhat", "biricik");
        MockHttpSession session = Mockito.mock(MockHttpSession.class);

        doNothing().when(userManager).login(loginRequest,session);
        verify(userManager, times(1)).login(loginRequest,session);


    }

}
