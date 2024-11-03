package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
