package com.demo.app.controllers;

import com.demo.app.apis.ProductApi;
import com.demo.app.models.requests.CreateProductRequest;
import com.demo.app.models.requests.UpdateProductRequest;
import com.demo.app.models.responses.CreateProductResponse;
import com.demo.app.models.responses.ProductResponse;
import com.demo.app.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<CreateProductResponse> createProduct(CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(productService.createProduct(createProductRequest));
    }

    @Override
    public ResponseEntity<ProductResponse> readProduct(String productId) {
        return ResponseEntity.ok(productService.readProduct(productId));
    }

    @Override
    public ResponseEntity<Void> updateProduct(String productId, UpdateProductRequest updateProductRequest) {
        productService.updateProduct(productId, updateProductRequest);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(String productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }

}
