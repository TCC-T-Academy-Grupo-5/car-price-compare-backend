package com.tcc5.car_price_compare.application.user;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;


    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDTO data) {
        UserDTO userDTO = new UserDTO(data.firstName(), data.lastName(), data.email(), data.cellphone());

        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        this.service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
