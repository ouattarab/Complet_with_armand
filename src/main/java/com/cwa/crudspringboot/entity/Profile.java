package com.cwa.crudspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity

@Data

public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

    private String bio;
}
