package com.cwa.crudspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "orderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Relation ManyToOne : Plusieurs OrderItems peuvent être associés à un même produit
    @ManyToOne
    @JoinColumn(name = "product_id") // Définit "product_id" comme clé étrangère dans la table "order_items"
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;

    // Relation ManyToOne : Plusieurs OrderItems peuvent être associés à une même commande
    @ManyToOne
    @JoinColumn(name = "order_id") // Définit "order_id" comme clé étrangère dans la table "order_items"
    private Order order;

}
