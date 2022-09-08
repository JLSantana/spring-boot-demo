package com.demo.app.services;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import com.demo.app.models.exceptions.CustomException;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import com.demo.app.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    private ProductService cut;

    @Mock
    private ProductRepository productRepository;

    private Product exampleProduct;
    private String requestId;

    @BeforeEach
    public void setup() {
        exampleProduct = Product.builder()
                .name("testName")
                .price(30D)
                .productStatus(Status.ENABLED)
                .productId("testId")
                .creationTime(LocalDateTime.now())
                .build();

        requestId = "testId";
    }

    //region CRUD methods

    @Test
    @DisplayName("Create Product Service Unit Test")
    public void createProductTest() {
        Product earlyProduct = Product.builder().name("testName").price(30D).productStatus(Status.ENABLED).build();

        when(productRepository.save(earlyProduct)).thenReturn(exampleProduct);

        CreateProductResponse response = cut.createProduct(CreateProductRequest.builder()
                .name("testName")
                .price(30D)
                .build());

        assertNotNull(response.getProductId());
        verify(productRepository).save(earlyProduct);
    }

    @Test
    @DisplayName("Read Product Service Unit Test - Happy Path")
    public void readFirstProductTest() {
        when(productRepository.findFirstByProductIdAndProductStatus(requestId, Status.ENABLED)).thenReturn(Optional.of(exampleProduct));

        ProductResponse response = cut.readProduct(requestId);

        assertNotNull(response.getName());
        assertNotNull(response.getPrice());
        verify(productRepository).findFirstByProductIdAndProductStatus(requestId, Status.ENABLED);
    }

    @Test
    @DisplayName("Read Product Service Unit Test - Not found")
    public void readSecondProductTest() {

        Throwable exception = assertThrowsExactly(CustomException.class, () -> {
            cut.readProduct(requestId);
        });

        verify(productRepository).findFirstByProductIdAndProductStatus(requestId, Status.ENABLED);
        assertTrue(exception.getMessage().contains("custom.error.not-found"));
    }

    @Test
    @DisplayName("Update Product Service Unit Test")
    public void updateFirstProductTest() {
        when(productRepository.findFirstByProductIdAndProductStatus(requestId, Status.ENABLED)).thenReturn(Optional.of(exampleProduct));

        cut.updateProduct(requestId, UpdateProductRequest.builder()
                .name("newTestName")
                .price(40D)
                .build());

        assertEquals(40D, exampleProduct.getPrice());
        assertEquals("newTestName", exampleProduct.getName());
        verify(productRepository).findFirstByProductIdAndProductStatus(requestId, Status.ENABLED);
    }

    @Test
    @DisplayName("Delete Product Service Unit Test")
    public void deleteFirstProductTest() {
        when(productRepository.findFirstByProductIdAndProductStatus(requestId, Status.ENABLED)).thenReturn(Optional.of(exampleProduct));

        cut.deleteProduct(requestId);

        assertEquals(Status.DISABLED, exampleProduct.getProductStatus());
        verify(productRepository).findFirstByProductIdAndProductStatus(requestId, Status.ENABLED);
    }

    //endregion

}