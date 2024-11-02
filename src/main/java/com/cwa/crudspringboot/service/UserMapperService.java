package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.dto.UserDTO;
import com.cwa.crudspringboot.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserMapperService {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getProfile().getFirstName() + " " + user.getProfile().getLastName(),
                user.getProfile().getEmail(),
                Period.between(user.getProfile().getBirthDate(), LocalDate.now()).getYears(),
                user.getProfile().getBio()

        );
    }
}
