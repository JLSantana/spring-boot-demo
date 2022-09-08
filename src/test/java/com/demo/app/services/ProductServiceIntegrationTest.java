package com.demo.app.services;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import com.demo.app.models.exceptions.CustomException;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import com.demo.app.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = { "spring.config.location=classpath:test.yaml"})
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService cut;

    @Autowired
    private ProductRepository productRepository;

    private Product example;
    private String requestId;

    @BeforeEach
    public void setup() {
        example = productRepository.save(Product.builder().name("testName").productStatus(Status.ENABLED).price(55D).build());
        requestId = example.getProductId();
    }

    @AfterEach
    public void delete() {
        productRepository.deleteAll();
    }

    //region CRUD methods

    @Test
    @DisplayName("Create Product Service Integration Test")
    public void createProductTest() {
        CreateProductResponse response = cut.createProduct(CreateProductRequest.builder()
                .name("testName")
                .price(30D)
                .build());

        assertNotNull(response);
        assertNotNull(response.getProductId());
    }

    @Test
    @DisplayName("Read Product Service Integration Test - Happy Path")
    public void readFirstProductTest() {
        ProductResponse response = cut.readProduct(requestId);

        assertNotNull(response);
        assertNotNull(response.getName());
        assertNotNull(response.getPrice());
    }

    @Test
    @DisplayName("Read Product Service Integration Test - Not found")
    public void readSecondProductTest() {

        Throwable exception = assertThrowsExactly(CustomException.class, () -> {
            cut.readProduct("testId");
        });

        assertTrue(exception.getMessage().contains("custom.error.not-found"));
    }

    @Test
    @DisplayName("Update Product Service Unit Test")
    public void updateFirstProductTest() {
        cut.updateProduct(requestId, UpdateProductRequest.builder()
                .name("newTestName")
                .price(40D)
                .build());

        assertTrue(() -> {
            Product retrievedProduct = productRepository.findById(requestId).get();
            return retrievedProduct.getName().equals("newTestName") && retrievedProduct.getPrice().equals(40D);
        });
    }

    @Test
    @DisplayName("Delete Product Service Unit Test")
    public void deleteFirstProductTest() {
        cut.deleteProduct(requestId);

        assertEquals(Status.DISABLED, productRepository.findById(requestId).get().getProductStatus());
    }

    //endregion

}