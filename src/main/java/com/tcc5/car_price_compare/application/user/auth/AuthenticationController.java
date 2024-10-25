package com.tcc5.car_price_compare.application.user.auth;

import com.tcc5.car_price_compare.application.user.favorite.FavoriteService;
import com.tcc5.car_price_compare.application.vehicle.VehicleService;
import com.tcc5.car_price_compare.application.vehicle.rating.RatingService;
import com.tcc5.car_price_compare.domain.response.user.RegisterResponse;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.user.dto.AuthenticationDTO;
import com.tcc5.car_price_compare.domain.response.user.LoginResponse;
import com.tcc5.car_price_compare.domain.user.dto.RegisterDTO;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.UserDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import com.tcc5.car_price_compare.infra.security.TokenService;
import com.tcc5.car_price_compare.infra.persistence.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and profile management")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final FavoriteService favoriteService;
    private final RatingService ratingService;
    private final VehicleService vehicleService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository repository, TokenService tokenService, FavoriteService favoriteService, RatingService ratingService, VehicleService vehicleService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.favoriteService = favoriteService;
        this.ratingService = ratingService;
        this.vehicleService = vehicleService;
    }


    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate token", description = "This endpoint authenticates the user using email and password, and returns a JWT token upon successful authentication.")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "This endpoint registers a new user with the provided details and returns a success message upon successful registration.")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User user = User.fromDto(data, encryptedPassword);

        this.repository.save(user);

        RegisterResponse res = new RegisterResponse(user.getFirstName(), user.getLastName(), user.getEmail(), "User created succesfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/token")
    @Operation(summary = "Validate JWT token", description = "This endpoint validates the provided JWT token and returns whether it is valid or not.")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        String subject = tokenService.validateToken(token);

        boolean isValid = !subject.isEmpty();
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile from token", description = "This endpoint retrieves the user profile based on the provided JWT token.")
    public ResponseEntity<UserDTO> getUserFromToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");

        String subject = tokenService.validateToken(token);

        if (subject == null || subject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) repository.findByEmail(subject);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Favorite> favorites = favoriteService.findAll(0, 5, 0).getContent();

        List<VehicleResponseDTO> favoriteVehicles = new ArrayList<>();

        favorites.forEach(f -> favoriteVehicles.add(vehicleService.getVehicleById(f.getVehicle().getId().toString())));

        List<Rating> ratings = ratingService.getRatingsByUser(user.getId());

        return ResponseEntity.ok(new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getCellphone(), favoriteVehicles, ratings));
    }
}
