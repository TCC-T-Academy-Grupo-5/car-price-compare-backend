package com.tcc5.car_price_compare.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc5.car_price_compare.controllers.AuthenticationController;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.AuthenticationDTO;
import com.tcc5.car_price_compare.domain.user.dto.RegisterDTO;
import com.tcc5.car_price_compare.infra.security.TokenService;
import com.tcc5.car_price_compare.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @Test
    public void testLoginSuccess() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("user@example.com", "password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(authenticationDTO.email());
        user.setPassword(authenticationDTO.password());
        given(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        given(this.tokenService.generateToken(any(User.class))).willReturn("test-jwt-token");
        String json = this.objectMapper.writeValueAsString(authenticationToken);

        this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-jwt-token"));
    }

    @Test
    void testLoginWithInvalidCredentials() throws Exception {
        given(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new BadCredentialsException("Invalid credentials"));
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("invalid@example.com", "invalidpassword");
        String json = this.objectMapper.writeValueAsString(authenticationDTO);

        this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "johndoe@example.com", "password", "11999998888");
        given(this.userRepository.findByEmail(registerDTO.email())).willReturn(null);
        String json = this.objectMapper.writeValueAsString(registerDTO);

        this.mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }

    @Test
    void testRegisterEmailAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "johndoe@example.com", "password", "11999998888");
        given(this.userRepository.findByEmail(registerDTO.email())).willReturn(new User());
        String json = this.objectMapper.writeValueAsString(registerDTO);

        this.mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }
}
