package com.tcc5.car_price_compare.auth;

import com.tcc5.car_price_compare.repositories.UserRepository;
import com.tcc5.car_price_compare.services.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUserNameSuccess() {
        String email = "user@example.com";
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        given(this.userRepository.findByEmail(email)).willReturn(userDetails);

        UserDetails result = this.authorizationService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(userDetails, result);
        verify(this.userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByEmailUserNotFound() {
        String email = "invalid@example.com";
        given(userRepository.findByEmail(email)).willReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername(email));
    }

}
