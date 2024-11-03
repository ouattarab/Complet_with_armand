package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.entity.Product;
import com.cwa.crudspringboot.repository.ProductRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    void shouldReturnAllProducts() {
       // Product prod1 = new Product(1L,"PC",0.1,100);
    }

}