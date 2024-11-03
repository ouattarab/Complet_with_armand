package com.cwa.crudspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;

    // Relation ManyToOne : Plusieurs commandes peuvent appartenir à un même client
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
