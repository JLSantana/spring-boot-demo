package com.demo.app.controllers;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = { "spring.config.location=classpath:test.yaml"})
public class ProductControllerIntegrationTest {

    @Autowired
    public ProductController cut;

    @Autowired
    public ProductRepository productRepository;

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
    @DisplayName("Create Product Controller Integration Test")
    public void createProductTest() {
        ResponseEntity<CreateProductResponse> response = cut.createProduct(CreateProductRequest.builder()
                .name("testName")
                .price(30D)
                .build());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getProductId());
    }


    @Test
    @DisplayName("Read Product Controller Integration Test")
    public void readProductTest() {
        ProductResponse response = cut.readProduct(requestId).getBody();

        assertNotNull(response);
        assertNotNull(response.getName());
        assertNotNull(response.getPrice());
    }

    @Test
    @DisplayName("Update Product Controller Integration Test")
    public void updateProductTest() {
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
    @DisplayName("Delete Product Controller Integration Test")
    public void deleteProductTest() {
        cut.deleteProduct(requestId);

        assertEquals(Status.DISABLED, productRepository.findById(requestId).get().getProductStatus());
    }

    //endregion

}