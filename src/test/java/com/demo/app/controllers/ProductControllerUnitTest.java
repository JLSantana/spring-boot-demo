package com.demo.app.controllers;

import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import com.demo.app.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerUnitTest {

    @InjectMocks
    private ProductController cut;

    @Mock
    private ProductService productService;

    private String productIdRequest;

    @BeforeEach
    public void setup() {
        productIdRequest = "testId";
    }

    //region CRUD methods

    @Test
    @DisplayName("Create Product Controller Unit Test")
    public void createProductTest() {
        when(productService.createProduct(CreateProductRequest.builder().build())).thenReturn(CreateProductResponse.builder().build());

        CreateProductResponse response = cut.createProduct(CreateProductRequest.builder().build()).getBody();

        assertNotNull(response);
        verify(productService).createProduct(CreateProductRequest.builder().build());
    }

    @Test
    @DisplayName("Read Product Controller Unit Test")
    public void readProductTest() {
        when(productService.readProduct(productIdRequest)).thenReturn(ProductResponse.builder().build());

        ProductResponse response = cut.readProduct(productIdRequest).getBody();

        assertNotNull(response);
        verify(productService).readProduct(productIdRequest);
    }

    @Test
    @DisplayName("Update Product Controller Unit Test")
    public void updateProductTest() {
        doNothing().when(productService).updateProduct(productIdRequest, UpdateProductRequest.builder().build());

        cut.updateProduct(productIdRequest, UpdateProductRequest.builder().build()).getBody();

        verify(productService).updateProduct(productIdRequest, UpdateProductRequest.builder().build());
    }

    @Test
    @DisplayName("Delete Product Controller Unit Test")
    public void deleteProductTest() {
        doNothing().when(productService).deleteProduct(productIdRequest);

        cut.deleteProduct(productIdRequest).getBody();

        verify(productService).deleteProduct(productIdRequest);
    }

    //endregion

}