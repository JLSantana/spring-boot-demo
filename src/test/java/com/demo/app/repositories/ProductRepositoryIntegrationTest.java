package com.demo.app.repositories;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = { "spring.config.location=classpath:test.yaml"})
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository cut;

    @Test
    @DisplayName("Find First by Product and Status")
    public void findFirstByProductIdAndProductStatus() {
        Product firstProduct = Product.builder()
                .name("testName")
                .price(30D)
                .productStatus(Status.ENABLED)
                .build();

        Product secondProduct = Product.builder()
                .name("testSecondName")
                .price(40D)
                .productStatus(Status.DISABLED)
                .build();

        final Product finalProduct = cut.save(firstProduct);
        cut.save(secondProduct);

        assertEquals(2, cut.count());
        assertTrue(() -> {
            Optional<Product> retrievedProduct = cut.findFirstByProductIdAndProductStatus(finalProduct.getProductId(), Status.ENABLED);
            return retrievedProduct.isPresent() && retrievedProduct.get().getProductId().equals(finalProduct.getProductId());
        });

        cut.deleteAll();
        assertEquals(0, cut.count());
    }

}