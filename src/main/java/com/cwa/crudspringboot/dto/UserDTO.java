package com.cwa.crudspringboot.dto;

public record UserDTO(
        Long id,
        String username,
        String role,
        String name,
        String email,
        int age,
        String bio

) {
}
