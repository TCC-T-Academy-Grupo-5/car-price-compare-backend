package com.tcc5.car_price_compare.auth;

import com.tcc5.car_price_compare.repositories.UserRepository;
import com.tcc5.car_price_compare.services.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class AuuthorizationServiceTest {

    @Autowired
    private AuthorizationService authorizationService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUserNameSuccess() {
        String email = "user@example.com";
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        given(userRepository.findByEmail(email)).willReturn(userDetails);

        UserDetails result = authorizationService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    public void testLoadUserByEmailUserNotFound() {
        String email = "invalid@example.com";
        given(userRepository.findByEmail(email)).willReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername(email));
    }

}
