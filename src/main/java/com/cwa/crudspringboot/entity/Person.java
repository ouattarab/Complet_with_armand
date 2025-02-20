package com.cwa.crudspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons",
        uniqueConstraints = @UniqueConstraint(columnNames = {"phoneNumber", "sequence"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq") //for or
    //@SequenceGenerator(name = "person_seq", sequenceName = "PERSON_SEQ", allocationSize = 1) //for or
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String phoneNumber;
    private String email;
    private Long age;

  //  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator") // ✅ Nouvelle séquence pour "sequence"
// @SequenceGenerator(name = "sequence_generator", sequenceName = "SEQUENCE_PERSON", allocationSize = 1)

    private Long sequence;
}
