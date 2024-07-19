package com.example.clientportal.initializer;

import com.example.clientportal.model.Product;
import com.example.clientportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ProductInitializer {

    @Bean
    CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                List<Product> products = Arrays.asList(
                        Product.builder().name("Smartphone").price(699.99).build(),
                        Product.builder().name("Laptop").price(999.99).build(),
                        Product.builder().name("Tablet").price(399.99).build(),
                        Product.builder().name("Smartwatch").price(199.99).build(),
                        Product.builder().name("Wireless Earbuds").price(149.99).build(),
                        Product.builder().name("Gaming Console").price(499.99).build(),
                        Product.builder().name("4K TV").price(799.99).build(),
                        Product.builder().name("Bluetooth Speaker").price(99.99).build(),
                        Product.builder().name("External Hard Drive").price(89.99).build(),
                        Product.builder().name("Gaming Mouse").price(59.99).build(),
                        Product.builder().name("Mechanical Keyboard").price(129.99).build(),
                        Product.builder().name("Drone").price(299.99).build(),
                        Product.builder().name("VR Headset").price(349.99).build(),
                        Product.builder().name("Smart Home Hub").price(179.99).build(),
                        Product.builder().name("Fitness Tracker").price(79.99).build()
                );
                productRepository.saveAll(products);
            }
        };
    }
}