package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.response.user.RegisterResponse;
import com.tcc5.car_price_compare.domain.user.dto.AuthenticationDTO;
import com.tcc5.car_price_compare.domain.response.user.LoginResponse;
import com.tcc5.car_price_compare.domain.user.dto.RegisterDTO;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.infra.security.TokenService;
import com.tcc5.car_price_compare.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User user = User.fromDto(data, encryptedPassword);

        this.repository.save(user);

        RegisterResponse res = new RegisterResponse(user.getFirstName(), user.getLastName(), user.getEmail(), "User created succesfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
