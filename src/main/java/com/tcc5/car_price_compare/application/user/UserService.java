package com.tcc5.car_price_compare.application.user;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.UserDTO;
import com.tcc5.car_price_compare.infra.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    public User updateUser(String id, UserDTO data) {
        var user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (data.firstName() != null) user.setFirstName(data.firstName());
        if (data.lastName() != null) user.setLastName(data.lastName());
        if (data.email() != null) user.setEmail(data.email());
        if (data.cellphone() != null) user.setCellphone(data.cellphone());

        return userRepository.save(user);
    }
}
