package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.dto.UserDTO;
import com.cwa.crudspringboot.repository.UserRepository;
import com.cwa.crudspringboot.service.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapperService::toDTO)
                .toList();
    }
}